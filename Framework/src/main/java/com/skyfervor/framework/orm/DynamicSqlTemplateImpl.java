package com.skyfervor.framework.orm;


import com.skyfervor.framework.constant.Constant;
import com.skyfervor.framework.context.UserContext;
import com.skyfervor.framework.utility.ReflectionUtils;
import com.skyfervor.framework.utility.StringUtils;
import com.skyfervor.framework.vobase.PageVo;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.xml.BeansDtdResolver;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.Assert;
import org.xml.sax.XMLReader;

import javax.persistence.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicSqlTemplateImpl implements DynamicSqlTemplate, InitializingBean, ResourceLoaderAware {

	private final static Log logger = LogFactory.getLog(DynamicSqlTemplateImpl.class);

	private ResourceLoader resourceLoader;
	private List<String> fileNameList;
	private final Map<String, String> nameSqlMap = new HashMap<>();
	private boolean isRefresh = false;
	private final Map<String, Long> fileNameTimeMap = new HashMap<>();
	private HibernateTemplate hibernateTemplate;
	private int pageSize;

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	private DefaultConfigurationBuilder getBuilder() throws Exception {

		final SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		saxParserFactory.setValidating(false);

		final SAXParser saxParser = saxParserFactory.newSAXParser();
		XMLReader parser = saxParser.getXMLReader();
		parser.setEntityResolver(new BeansDtdResolver());

		return new DefaultConfigurationBuilder(parser);
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		buildNameSqlMap();
		if (isRefresh) {
			buildFileNameTimeMap();
		}
	}

	private void buildNameSqlMap() throws Exception {

		DefaultConfigurationBuilder builder = getBuilder();

		for (String s : fileNameList) {
			String fileName = s.trim();
			if (resourceLoader instanceof ResourcePatternResolver) {
				try {
					Resource[] resources = ((ResourcePatternResolver) resourceLoader).getResources(fileName);
					buildNameSqlMap(builder, resources);
				} catch (IOException ex) {
					throw new ConfigurationException("Could not resolve sql definition resource pattern [" + fileName + "]", ex);
				}
			} else {
				Resource resource = resourceLoader.getResource(fileName);
				buildNameSqlMap(builder, new Resource[]{resource});
			}
		}
	}

	private void buildNameSqlMap(DefaultConfigurationBuilder builder, Resource[] resources) throws Exception {

		for (Resource resource : resources) {
			this.buildNameSqlMap(builder, resource);
		}
	}

	public void setRefresh(boolean isRefresh) {
		this.isRefresh = isRefresh;
	}

	private void buildFileNameTimeMap() throws Exception {

		for (String s : fileNameList) {
			String fileName = s.trim();
			if (resourceLoader instanceof ResourcePatternResolver) {
				try {
					Resource[] resources = ((ResourcePatternResolver) resourceLoader).getResources(fileName);
					buildFileNameTimeMap(resources);
				} catch (IOException ex) {
					throw new ConfigurationException("Could not resolve sql definition resource pattern [" + fileName + "]", ex);
				}
			} else {
				Resource resource = resourceLoader.getResource(fileName);
				buildFileNameTimeMap(new Resource[]{resource});
			}
		}
	}

	private void buildFileNameTimeMap(Resource[] resources) throws Exception {

		for (Resource resource : resources) {
			String fileName = resource.getFilename();
			long fileTime = resource.lastModified();
			fileNameTimeMap.put(fileName, fileTime);
		}

	}

	private String getSqlByName(String key) throws Exception {

		if (isRefresh) {
			refreshNameSqlMap();
		}
		return nameSqlMap.get(key);
	}

	private void refreshNameSqlMap() throws Exception {

		DefaultConfigurationBuilder builder = getBuilder();

		for (String s : fileNameList) {
			String fileName = s.trim();
			if (resourceLoader instanceof ResourcePatternResolver) {
				try {
					Resource[] resources = ((ResourcePatternResolver) resourceLoader).getResources(fileName);
					refreshNameSqlMap(builder, resources);
				} catch (IOException ex) {
					throw new ConfigurationException("Could not resolve sql definition resource pattern [" + fileName + "]", ex);
				}
			} else {
				Resource resource = resourceLoader.getResource(fileName);
				refreshNameSqlMap(builder, new Resource[]{resource});
			}
		}
	}

	private void refreshNameSqlMap(DefaultConfigurationBuilder builder, Resource[] resources) throws Exception {

		for (Resource resource : resources) {
			String filename = resource.getFilename();
			long fileTimeNow = resource.lastModified();
			synchronized (fileNameTimeMap) {
				long fileTime = fileNameTimeMap.get(filename) != null ? fileNameTimeMap.get(filename) : 0;
				if (fileTime != fileTimeNow) {
					buildNameSqlMap(builder, resource);
					fileNameTimeMap.put(filename, fileTimeNow);
				}
			}
		}
	}

	private void buildNameSqlMap(DefaultConfigurationBuilder builder, Resource resource) throws Exception {

		Configuration config = builder.build(resource.getInputStream());
		Configuration[] querys = config.getChildren("query");

		for (Configuration query : querys) {
			String queryName = query.getAttribute("name");
			if (queryName.equals("")) {
				throw new ConfigurationException("Sql name is essential attribute in a <query>.");
			}
			nameSqlMap.put(queryName, query.getValue());
		}
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void setFileNameList(List<String> fileNameList) {
		this.fileNameList = fileNameList;
	}

	public List<String> getFileNameList() {
		return fileNameList;
	}

	public void setIsRefresh(boolean isRefresh) {
		this.isRefresh = isRefresh;
	}

	public boolean getIsRefresh() {
		return isRefresh;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	private static String removeOrders(String sql) {

		Assert.hasText(sql);

		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(sql);

		StringBuffer sb = new StringBuffer();

		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);

		return sb.toString();
	}

	private static String removeSelect(String sql) {

		Assert.hasText(sql);

		int beginPos = sql.toLowerCase().indexOf("from ");

		Assert.isTrue(beginPos != -1, " hql : " + sql + " must has a keyword 'from'");

		return sql.substring(beginPos);
	}

	private Context generateVelocityContext(Map<String, Object> params) {

		VelocityContext context = new VelocityContext();
		if (null == params) {

			return context;
		}

		for (String key : params.keySet()) {

			Object value = params.get(key);

			context.put(key, value);
		}

		return context;
	}

	private void setProperties(Query query, Context context, String[] namedParams) throws HibernateException {

		for (String namedParam : namedParams) {

			final Object object = context.get(namedParam);
			if (object == null) {
				continue;
			}
			if (object instanceof Collection) {
				query.setParameterList(namedParam, (Collection<?>) object);
			} else {
				query.setParameter(namedParam, object);
			}
		}
	}

	@Override
	@Deprecated
	public <T> PageVo<T> queryPage(String sqlName, Map<String, Object> params, int pageIndex, int pageSize, Class<T> clazz) throws Exception {
		return queryPageBySql(sqlName, params, pageIndex, pageSize, clazz);
	}

	@Override
	@Deprecated
	public <T> List<T> queryList(String sqlName, Map<String, Object> params, Class<T> clazz) throws Exception {
		return queryPageBySql(sqlName, params, 0, 0, clazz).getRows();
	}

	@Override
	@Deprecated
	public <T> T queryOne(String sqlName, Map<String, Object> params, Class<T> clazz) throws Exception {
		List<T> list = queryList(sqlName, params, clazz);
		return list.size() > 0 ? list.get(0) : null;
	}

	public <T> PageVo<T> queryPageBySql(String sqlName, Map<String, Object> params, final int pageIndex, int pageSize, final Class<T> clazz) throws Exception {

		String sqlOriginal = getSqlByName(sqlName);
		if (sqlOriginal == null) {
			throw new Exception("QueryPageBySql -> The sql is not find, Sql: " + sqlName);
		}
		sqlOriginal = this.setDefaultWhereCondition(sqlOriginal, params, clazz);

		final Context context = generateVelocityContext(params);
		StringWriter writer = new StringWriter();

		Velocity.evaluate(context, writer, "Hibernate", sqlOriginal);
		final String sql = writer.toString();

		Long total = 0L;
		if (pageIndex > 0 && pageSize > 0) {
			final String sqlCount = "select sum(num) from (select count(*) as num " + removeSelect(removeOrders(sql)) + ") as total";
			total = hibernateTemplate.execute(session -> {
				Query query = session.createSQLQuery(sqlCount);
				setProperties(query, context, query.getNamedParameters());
				Number count = (Number) query.uniqueResult();
				return count != null ? count.longValue() : 0;
			});
		}

		if (pageSize <= 0) {
			if (this.pageSize > 0) {
				pageSize = this.pageSize;
			} else {
				pageSize = 10;
			}
		}

		final int pageSizeFinal = pageSize;

		List<T> list = hibernateTemplate.execute((HibernateCallback<List<T>>) session -> {
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(new MyResultTransformer(clazz));
			setProperties(query, context, query.getNamedParameters());
			if (pageIndex > 0) {
				query.setFirstResult((pageIndex - 1) * pageSizeFinal);
				query.setMaxResults(pageSizeFinal);
			}
			return query.list();
		});

		long start = pageIndex > 0 ? (pageIndex - 1) * pageSize : 0;
		return new PageVo<>(start, total, pageSize, list);
	}

	public <T> PageVo<T> queryPageByStrSql(final String strSql, final Map<String, Object> params, final int pageIndex, int pageSize, final Class<T> clazz) {

		final Context context = generateVelocityContext(params);
		StringWriter writer = new StringWriter();

		Velocity.evaluate(context, writer, "Hibernate", strSql);
		final String sql = writer.toString();

		Long total = 0L;
		if (pageIndex > 0 && pageSize > 0) {
			final String sqlCount = "select sum(num) from (select count(*) as num " + removeSelect(removeOrders(sql)) + ") as total";
			total = hibernateTemplate.execute(session -> {
				Query query = session.createSQLQuery(sqlCount);
				setProperties(query, context, query.getNamedParameters());
				Number count = (Number) query.uniqueResult();
				return count != null ? count.longValue() : 0;
			});
		}

		if (pageSize <= 0) {
			if (this.pageSize > 0) {
				pageSize = this.pageSize;
			} else {
				pageSize = 10;
			}
		}

		final int pageSizeFinal = pageSize;
		List<T> list = hibernateTemplate.execute((HibernateCallback<List<T>>) session -> {
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(new MyResultTransformer(clazz));
			setProperties(query, context, query.getNamedParameters());
			if (pageIndex > 0) {
				query.setFirstResult((pageIndex - 1) * pageSizeFinal);
				query.setMaxResults(pageSizeFinal);
			}
			return query.list();
		});

		long start = pageIndex > 0 ? (pageIndex - 1) * pageSizeFinal : 0;
		return new PageVo<>(start, total, pageSizeFinal, list);
	}

	@Override
	public <T> T insert(Object vo, Class<T> clazz) throws Exception {

		StringUtils.CorrectString(vo);

		T md = clazz.newInstance();
		PropertyUtils.copyProperties(md, vo);
		setForeignMd(vo, md, clazz);
		setBaseValue(md);
		hibernateTemplate.save(md);
		return md;
	}

	@Override
	public <T> void update(Object vo, Class<T> clazz) throws Exception {

		StringUtils.CorrectString(vo);

		Object md = clazz.newInstance();
		PropertyUtils.copyProperties(md, vo);
		setForeignMd(vo, md, clazz);
		updateBaseValue(md);
		hibernateTemplate.update(md);
	}

	@Override
	public <T> int updateExcept(Object vo, Class<T> clazz, String[] fieldNameArray) throws Exception {
		return updateByCreateSql(vo, clazz, false, fieldNameArray, null, null);
	}

	@Override
	public <T> int updateInclude(Object vo, Class<T> clazz, String[] fieldNameArray) throws Exception {
		return updateByCreateSql(vo, clazz, false, null, null, fieldNameArray);
	}

	@Override
	public <T> int updateNonull(Object vo, Class<T> clazz) throws Exception {
		return updateByCreateSql(vo, clazz, true, null, null, null);
	}

	@Override
	public <T> int updateNonullNoById(Object vo, Class<T> clazz, String[] fieldNameArray) throws Exception {
		return updateByCreateSql(vo, clazz, true, null, fieldNameArray, null);
	}

	public <T> int updateByCreateSql(Object vo, Class<T> clazz, boolean isNonull, String[] exceptFieldNameArray,
									 String[] selectFieldNameArray, String[] includeFieldNameArray) throws Exception {

		StringUtils.CorrectString(vo);

		HashSet<String> exceptFieldNameSet = new HashSet<>();
		if (exceptFieldNameArray != null) {
			Collections.addAll(exceptFieldNameSet, exceptFieldNameArray);
		}

		HashSet<String> includeFieldNameSet = new HashSet<>();
		if (includeFieldNameArray != null) {
			Collections.addAll(includeFieldNameSet, includeFieldNameArray);
		}

		Table table = clazz.getAnnotation(Table.class);
		String tableName = table.name();

		StringBuilder sqlBuilder = new StringBuilder("UPDATE " + tableName);
		StringBuilder sqlSetBuilder = new StringBuilder(" SET ");
		StringBuilder sqlWhereBuilder = new StringBuilder(" WHERE 1=1");

		Map<String, Object> paramMap = new HashMap<>();
		Set<String> fieldSet = new HashSet<>();
		Set<String> fieldPrimarySet = new HashSet<>();

		Method[] methods = clazz.getMethods();
		for (Method method : methods) {

			Set<String> tempSet;
			Id id = method.getAnnotation(Id.class);
			if (id != null) {
				tempSet = fieldPrimarySet;
			} else {
				tempSet = fieldSet;
			}

			ManyToOne manyToOne = method.getAnnotation(ManyToOne.class);
			if (manyToOne != null) {
				JoinColumns joinColumns = method.getAnnotation(JoinColumns.class);
				if (joinColumns != null) {
					JoinColumn[] joinColumnArray = joinColumns.value();
					for (JoinColumn joinColumn : joinColumnArray) {
						tempSet.add(joinColumn.name().toUpperCase());
					}
				} else {
					JoinColumn joinColumn = method.getAnnotation(JoinColumn.class);
					tempSet.add(joinColumn.name().toUpperCase());
				}
			} else {
				Column column = method.getAnnotation(Column.class);
				if (column != null) {
					tempSet.add(column.name().toUpperCase());
				}
			}
		}

		Class<?> clazzVo = vo.getClass();
		Field[] fields = clazzVo.getDeclaredFields();
//		Field[] fields = ReflectionUtils.getAllDeclaredFields(clazzVo);

		AccessibleObject.setAccessible(fields, true);

		if (selectFieldNameArray != null) {
			for (String fieldPrimaryName : selectFieldNameArray) {
				boolean bool = false;
				for (Field field : fields) {
					if (field.getName().equals(fieldPrimaryName)) {
						bool = true;
						Object fieldValue = field.get(vo);
						if (fieldValue != null) {
							sqlWhereBuilder.append(" AND ");
							sqlWhereBuilder.append(field.getName());
							sqlWhereBuilder.append(" = :");
							sqlWhereBuilder.append(field.getName());
							paramMap.put(field.getName(), fieldValue);
						} else {
							throw new Exception("UpdateByCreateSql -> Select field is null in vo, Class: " + clazzVo.getName() + ", Field: " + fieldPrimaryName);
						}
						break;
					}
				}
				if (!bool) {
					throw new Exception("UpdateByCreateSql -> Select field is not exist in vo, Class: " + clazzVo.getName() + ", Field: " + fieldPrimaryName);
				}
			}
		} else {
			for (String fieldPrimaryName : fieldPrimarySet) {
				boolean bool = false;
				for (Field field : fields) {
					if (field.getName().toUpperCase().equals(fieldPrimaryName)) {
						bool = true;
						Object fieldValue = field.get(vo);
						if (fieldValue != null) {
							sqlWhereBuilder.append(" AND ");
							sqlWhereBuilder.append(field.getName());
							sqlWhereBuilder.append(" = :");
							sqlWhereBuilder.append(field.getName());
							paramMap.put(field.getName(), fieldValue);
						} else {
							throw new Exception("UpdateByCreateSql -> Id field is null in vo, Class: " + clazzVo.getName() + ", Field: " + fieldPrimaryName);
						}
						break;
					}
				}
				if (!bool) {
					throw new Exception("UpdateByCreateSql -> Id field is not exist in vo, Class: " + clazzVo.getName() + ", Field: " + fieldPrimaryName);
				}
			}
		}

		for (Field field : fields) {
			String fieldName = field.getName();
			if (fieldSet.contains(fieldName.toUpperCase())) {
				if (exceptFieldNameArray == null || !exceptFieldNameSet.contains(fieldName)) {
					if (includeFieldNameArray == null || includeFieldNameSet.contains(fieldName)) {
						Object fieldValue = field.get(vo);
						if (fieldValue != null) {
							sqlSetBuilder.append(fieldName);
							sqlSetBuilder.append(" = :");
							sqlSetBuilder.append(fieldName);
							sqlSetBuilder.append(", ");
							paramMap.put(fieldName, fieldValue);
						} else {
							if (!isNonull) {
								sqlSetBuilder.append(fieldName);
								sqlSetBuilder.append(" = NULL");
								sqlSetBuilder.append(", ");
							}
						}
					}
				}
			}
		}
		String baseValue = this.updateBaseValueBySql(vo, clazz, paramMap);
		if (baseValue.length() > 0) {
			sqlSetBuilder.append(baseValue);
		}

		if (sqlSetBuilder.length() <= 5) {
			return 0;
		}

		sqlBuilder.append(sqlSetBuilder.substring(0, sqlSetBuilder.length() - 2));
		sqlBuilder.append(sqlWhereBuilder.toString());

		final Context context = generateVelocityContext(paramMap);
		final String sql = sqlBuilder.toString();
		return hibernateTemplate.execute(session -> {
			Query query = session.createSQLQuery(sql);
			String[] namedParams = query.getNamedParameters();
			setProperties(query, context, namedParams);
			return query.executeUpdate();
		});
	}

	@Override
	@Deprecated
	public int updateSql(String sqlName, Map<String, Object> params) throws Exception {

		final Context context = generateVelocityContext(params);
		StringWriter writer = new StringWriter();

		String sqlOriginal = getSqlByName(sqlName);
		if (sqlOriginal == null) {
			throw new Exception("UpdateSql -> The sql is not find: " + sqlName);
		}

		Velocity.evaluate(context, writer, "Hibernate", sqlOriginal);
		final String sql = writer.toString();

		return hibernateTemplate.execute(session -> {
			Query query = session.createSQLQuery(sql);
			String[] namedParams = query.getNamedParameters();
			setProperties(query, context, namedParams);
			return query.executeUpdate();
		});
	}

	@Override
	public <T> void delete(Object vo, Class<T> clazz) throws Exception {
		Object md = clazz.newInstance();
		PropertyUtils.copyProperties(md, vo);
		setForeignMd(vo, md, clazz);
		hibernateTemplate.delete(md);

	}

	@Override
	public <T> void deleteNoById(Object vo, Class<T> clazz, String[] selectFieldNameArray) throws Exception {

		Table table = clazz.getAnnotation(Table.class);
		String tableName = table.name();

		StringBuilder sqlBuilder = new StringBuilder("DELETE FROM " + tableName);
		StringBuilder sqlWhereBuilder = new StringBuilder(" WHERE 1=1");

		Map<String, Object> paramMap = new HashMap<>();

		Class<?> clazzVo = vo.getClass();
		// 此处通过工具类获取class所有的成员变量（解决继承BaseVo后获取不到父类成员变量问题）
//		Field[] fields = clazzVo.getDeclaredFields();
		Field[] fields = ReflectionUtils.getAllDeclaredFields(clazzVo);
		AccessibleObject.setAccessible(fields, true);
		if (selectFieldNameArray != null) {
			for (String fieldPrimaryName : selectFieldNameArray) {
				boolean bool = false;
				for (Field field : fields) {
					if (field.getName().equals(fieldPrimaryName)) {
						bool = true;
						Object fieldValue = field.get(vo);
						if (fieldValue != null) {
							sqlWhereBuilder.append(" AND ");
							sqlWhereBuilder.append(fieldPrimaryName);
							sqlWhereBuilder.append(" = :");
							sqlWhereBuilder.append(fieldPrimaryName);
							paramMap.put(fieldPrimaryName, fieldValue);
						} else {
							throw new Exception("DeleteNoById -> Select field is null in vo, Class: " + clazzVo.getName() + ", Field: " + fieldPrimaryName);
						}
						break;
					}
				}
				if (!bool) {
					throw new Exception("DeleteNoById -> Select field is not exist in vo, Class: " + clazzVo.getName() + ", Field: " + fieldPrimaryName);
				}
			}
		}

		sqlBuilder.append(sqlWhereBuilder.toString());

		final Context context = generateVelocityContext(paramMap);
		final String sql = sqlBuilder.toString();

		hibernateTemplate.execute(session -> {
			Query query = session.createSQLQuery(sql);
			String[] namedParams = query.getNamedParameters();
			setProperties(query, context, namedParams);
			return query.executeUpdate();
		});
	}

	@Override
	public <T> T load(Object vo, final Class<T> clazz) throws Exception {
		return loadBySql(vo, clazz);
	}

	public <T> T loadBySql(Object vo, Class<T> clazz) throws Exception {

		Map<String, Object> primaryFieldMap = new HashMap<>();

		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			Id id = method.getAnnotation(Id.class);
			if (id != null) {
				ManyToOne manyToOne = method.getAnnotation(ManyToOne.class);
				if (manyToOne != null) {
					JoinColumns joinColumns = method.getAnnotation(JoinColumns.class);
					if (joinColumns != null) {
						JoinColumn[] joinColumnArray = joinColumns.value();
						for (JoinColumn joinColumn : joinColumnArray) {
							primaryFieldMap.put(joinColumn.name().toUpperCase(), joinColumn.name().toUpperCase());
						}
					} else {
						JoinColumn joinColumn = method.getAnnotation(JoinColumn.class);
						primaryFieldMap.put(joinColumn.name().toUpperCase(), joinColumn.name().toUpperCase());
					}
				} else {
					Column column = method.getAnnotation(Column.class);
					if (column != null) {
						primaryFieldMap.put(column.name().toUpperCase(), column.name().toUpperCase());
					}
				}
			}
		}

		Class<?> clazzVo = vo.getClass();
		// 此处通过工具类获取class所有的成员变量（解决继承BaseVo后获取不到父类成员变量问题）
//		Field[] fields = clazzVo.getDeclaredFields();
		Field[] fields = ReflectionUtils.getAllDeclaredFields(clazzVo);
		AccessibleObject.setAccessible(fields, true);
		Set<String> primaryFieldSet = primaryFieldMap.keySet();
		for (String primaryFieldName : primaryFieldSet) {
			boolean bool = false;
			for (Field field : fields) {
				if (field.getName().toUpperCase().equals(primaryFieldName)) {
					bool = true;
					Object fieldValue = field.get(vo);
					if (fieldValue != null) {
						primaryFieldMap.put(primaryFieldName, fieldValue);
					} else {
						throw new Exception("LoadBySql -> Id field is null in vo, Class: " + clazzVo.getName() + ", Field: " + primaryFieldName);
					}
					break;
				}
			}
			if (!bool) {
				throw new Exception("LoadBySql -> Id field is not exist in vo, Class: " + clazzVo.getName() + ", Field: " + primaryFieldName);
			}
		}

		return loadBySql(primaryFieldMap, clazz);
	}

	public <T> T loadBySql(Map<String, Object> map, final Class<T> clazz) {

		Table table = clazz.getAnnotation(Table.class);
		String tableName = table.name();

		StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM " + tableName + " WHERE 1=1 ");

		Set<String> fieldNameSet = map.keySet();
		for (String fieldName : fieldNameSet) {
			sqlBuilder.append("AND ");
			sqlBuilder.append(fieldName.toUpperCase());
			sqlBuilder.append(" = :");
			sqlBuilder.append(fieldName.toUpperCase());
			sqlBuilder.append(" ");
		}

		final Context context = generateVelocityContext(map);
		final String sql = sqlBuilder.toString();

		return hibernateTemplate.execute(session -> {
			Query query = session.createSQLQuery(sql).addEntity(clazz);
			String[] namedParams = query.getNamedParameters();
			setProperties(query, context, namedParams);
			@SuppressWarnings("unchecked")
			List<T> list = query.list();
			if (list.size() != 0) {
				return list.get(0);
			} else {
				return null;
			}
		});
	}

	private <T> void setForeignMd(Object vo, Object md, Class<T> clazz) throws Exception {

		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			ManyToOne manyToOne = method.getAnnotation(ManyToOne.class);
			if (manyToOne != null) {

				Map<String, Object> foreignNameMap = new HashMap<>();
				JoinColumns joinColumns = method.getAnnotation(JoinColumns.class);
				if (joinColumns != null) {
					JoinColumn[] joinColumnArray = joinColumns.value();
					for (JoinColumn joinColumn : joinColumnArray) {
						joinColumn.referencedColumnName();
						if (StringUtils.isNotBlank(joinColumn.referencedColumnName())) {
							foreignNameMap.put(joinColumn.referencedColumnName().toUpperCase(), joinColumn.name().toUpperCase());
						} else {
							foreignNameMap.put(joinColumn.name().toUpperCase(), joinColumn.name().toUpperCase());
						}
					}
				} else {
					JoinColumn joinColumn = method.getAnnotation(JoinColumn.class);
					joinColumn.referencedColumnName();
					if (StringUtils.isNotBlank(joinColumn.referencedColumnName())) {
						foreignNameMap.put(joinColumn.referencedColumnName().toUpperCase(), joinColumn.name().toUpperCase());
					} else {
						foreignNameMap.put(joinColumn.name().toUpperCase(), joinColumn.name().toUpperCase());
					}
				}

				Set<String> foreignNameSet = foreignNameMap.keySet();
				for (String referencedFieldName : foreignNameSet) {
					String fieldName = (String) foreignNameMap.get(referencedFieldName);
					Class<?> clazzVo = vo.getClass();
					// 此处通过工具类获取class所有的成员变量（解决继承BaseVo后获取不到父类成员变量问题）
//					Field[] fields = clazzVo.getDeclaredFields();
					Field[] fields = ReflectionUtils.getAllDeclaredFields(clazzVo);
					AccessibleObject.setAccessible(fields, true);
					for (Field field : fields) {
						if (field.getName().toUpperCase().equals(fieldName)) {
							Object fieldValue = field.get(vo);
							if (fieldValue != null && !fieldValue.equals("")) {
								foreignNameMap.put(referencedFieldName, fieldValue);
							} else {
								foreignNameMap.remove(referencedFieldName);
							}
							break;
						}
					}
				}

				if (foreignNameMap.keySet().size() > 0) {
					String methodName = method.getName();
					Class<?> returnType = method.getReturnType();
					Object mdForeign = loadBySql(foreignNameMap, returnType);
					if (mdForeign == null) {
						throw new Exception("SetForeignMd -> The foreign record not exit");
					}
					Method methodSet = clazz.getMethod("set" + methodName.substring(3), returnType);
					methodSet.invoke(md, mdForeign);
				}
			}
		}
	}


	@Override
	public void flush() {
		hibernateTemplate.flush();
	}

	@Override
	public <T> T insertOrUpdate(Object vo, Class<T> clazz) throws Exception {
		T md = clazz.newInstance();
		PropertyUtils.copyProperties(md, vo);
		setForeignMd(vo, md, clazz);
		setBaseValue(md);
		hibernateTemplate.saveOrUpdate(md);
		return md;
	}

	static class MyResultTransformer implements ResultTransformer {

		private static final long serialVersionUID = 4216528307922649659L;

		Class<?> clazz;

		MyResultTransformer(Class<?> clazz) {
			this.clazz = clazz;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public List transformList(List arg0) {
			return arg0;
		}

		@Override
		public Object transformTuple(Object[] arg0, String[] arg1) {

			Object object = null;

			try {
				object = clazz.newInstance();
				for (int i = 0; i < arg1.length; i++) {
//					Field field = clazz.getDeclaredField(arg1[i]);
//					field.setAccessible(true);
//					Object backObject = getByType(field, arg0[i]);
//					field.set(object, backObject);
					Field field = ReflectionUtils.findField(clazz, arg1[i]);
					ReflectionUtils.makeAccessible(field);
					ReflectionUtils.setField(field, object, getByType(field, arg0[i]));
				}
				StringUtils.CorrectString(object);
			} catch (Exception e) {
				if (logger.isErrorEnabled()) {
					logger.error("MyResultTransformer -> transformTuple, Class: " + clazz.getName() + " -> " + e);
				}
			}

			return object;
		}

		private Object getByType(Field field, Object paramObject) {

			if (paramObject instanceof BigInteger) {

				BigInteger bigInteger = (BigInteger) paramObject;

				if (field.getType().isAssignableFrom(int.class)
						|| field.getType().isAssignableFrom(Integer.class)) {

					paramObject = bigInteger.intValue();

				} else if (field.getType().isAssignableFrom(long.class)
						|| field.getType().isAssignableFrom(Long.class)) {

					paramObject = bigInteger.longValue();

				} else if (field.getType().isAssignableFrom(Date.class)) {

					paramObject = new Date(bigInteger.longValue());
				}

			} else if (paramObject instanceof BigDecimal) {

				BigDecimal bigDecimal = (BigDecimal) paramObject;

				if (field.getType().isAssignableFrom(int.class)
						|| field.getType().isAssignableFrom(Integer.class)) {

					paramObject = bigDecimal.intValue();

				} else if (field.getType().isAssignableFrom(long.class)
						|| field.getType().isAssignableFrom(Long.class)) {

					paramObject = bigDecimal.longValue();

				} else if (field.getType().isAssignableFrom(Date.class)) {

					paramObject = new Date(bigDecimal.longValue());

				} else if (field.getType().isAssignableFrom(float.class)
						|| field.getType().isAssignableFrom(Float.class)) {

					paramObject = bigDecimal.floatValue();

				} else if (field.getType().isAssignableFrom(double.class)
						|| field.getType().isAssignableFrom(Double.class)) {

					paramObject = bigDecimal.doubleValue();
				}
			}

			return paramObject;
		}

	}

	/**
	 * 在insert时为BaseModel成员变量赋值
	 */
	private void setBaseValue(Object md) {
		if (md instanceof BaseModel) {
			Map<String, Object> userMap = new HashMap<>();
			userMap.put("currentUserId", null);
			userMap.put("currentUserName", null);
//			if (UserContext.getLoginInfo() != null && UserContext.getLoginName() != null) {
//				logger.info("DynamicSqlTemplateImpl.setBaseValue userId=" + UserContext.getUserID() + ",userName=" + UserContext.getLoginName());
			userMap.replace("currentUserId", UserContext.getUserID());
			userMap.replace("currentUserName", UserContext.getLoginName());
//			} else {
//				logger.info("DynamicSqlTemplateImpl.setBaseValue UserContext未取到");
//				this.getUserFromVo(vo, userMap);
//			}
			if (((BaseModel) md).getEnumDataEntityStatus() == null) {
				((BaseModel) md).setEnumDataEntityStatus(Constant.DataBase.Normal);
			}
			((BaseModel) md).setCreateOperator(userMap.get("currentUserName") == null ? null : userMap.get("currentUserName").toString());
			((BaseModel) md).setCreateOperatorId(userMap.get("currentUserId") == null ? null : new Long(userMap.get("currentUserId").toString()));
			((BaseModel) md).setCreateTime(new Date());
			((BaseModel) md).setLastUpdateOperator(userMap.get("currentUserName") == null ? null : userMap.get("currentUserName").toString());
			((BaseModel) md).setLastUpdateOperatorId(userMap.get("currentUserId") == null ? null : new Long(userMap.get("currentUserId").toString()));
			((BaseModel) md).setLastUpdateTime(new Date());
		}
	}

	/**
	 * 在update时为BaseModel成员变量赋值
	 */
	private void updateBaseValue(Object md) {
		if (md instanceof BaseModel) {
			Map<String, Object> userMap = new HashMap<>();
			userMap.put("currentUserId", null);
			userMap.put("currentUserName", null);
//			if (UserContext.getLoginInfo() != null && UserContext.getLoginName() != null) {
//				logger.info("DynamicSqlTemplateImpl.updateBaseValue userId=" + UserContext.getUserID() + ",userName=" + UserContext.getLoginName());
			userMap.replace("currentUserId", UserContext.getUserID());
			userMap.replace("currentUserName", UserContext.getLoginName());
//			} else {
//				logger.info("DynamicSqlTemplateImpl.updateBaseValue UserContext未取到");
//				this.getUserFromVo(vo, userMap);
//			}
			((BaseModel) md).setLastUpdateOperator(userMap.get("currentUserName") == null ? null : userMap.get("currentUserName").toString());
			((BaseModel) md).setLastUpdateOperatorId(userMap.get("currentUserId") == null ? null : new Long(userMap.get("currentUserId").toString()));
			((BaseModel) md).setLastUpdateTime(new Date());
		}
	}

	/**
	 * 以sql更新时为BaseModel成员变量赋值
	 */
	private <T> String updateBaseValueBySql(Object vo, Class<T> clazz, Map<String, Object> paramMap) throws Exception {
		StringBuilder sb = new StringBuilder();
		T md = clazz.newInstance();
		if (md instanceof BaseModel) {
			Map<String, Object> map = new HashMap<>();
			Field[] fields = vo.getClass().getSuperclass().getDeclaredFields();
			AccessibleObject.setAccessible(fields, true);
			for (Field field : fields) {
				String fieldName = field.getName();
				if ("enumDataEntityStatus".equals(fieldName)) {
					Object fieldValue = field.get(vo);
					if (fieldValue != null && fieldValue.equals(Constant.DataBase.Deleted)) {
						map.put("enumDataEntityStatus", Constant.DataBase.Deleted);
					}
					break;
				}
			}
			if (!map.containsKey("enumDataEntityStatus")) {
				map.put("enumDataEntityStatus", Constant.DataBase.Normal);
			}
//			if (UserContext.getLoginInfo() != null && UserContext.getLoginName() != null) {
//				logger.info("DynamicSqlTemplateImpl.updateBaseValueBySql userId=" + UserContext.getUserID() + ",userName=" + UserContext.getLoginName());
			map.put("currentUserId", UserContext.getUserID());
			map.put("currentUserName", UserContext.getLoginName());
//			} else {
//				logger.info("DynamicSqlTemplateImpl.updateBaseValueBySql UserContext未取到");
//				this.getUserFromVo(vo, map);
//			}
			map.put("lastUpdateTime", new Date());
			for (String str : map.keySet()) {
				String fieldName = str;
				if ("currentUserId".equals(str)) {
					fieldName = "lastUpdateOperatorId";
				} else if ("currentUserName".equals(str)) {
					fieldName = "lastUpdateOperator";
				}
				sb.append(fieldName);
				sb.append(" = :");
				sb.append(fieldName);
				sb.append(", ");
				paramMap.put(fieldName, map.get(str));
			}
		}
		return sb.toString();
	}

	/**
	 * 以Vo对象为入参按sql查询
	 *
	 * @param sqlName   sql-*.xml中的query name
	 * @param vo        传入查询的Vo对象
	 * @param pageIndex
	 * @param pageSize
	 * @param clazzVo   Vo.class
	 * @param vid
	 * @return
	 * @throws Exception
	 */
	public <T> PageVo<T> queryPageBySql(String sqlName, T vo, final int pageIndex, int pageSize, final Class<T> clazzVo, long vid) throws Exception {
		Field[] fields = ReflectionUtils.getAllDeclaredFields(clazzVo);
		AccessibleObject.setAccessible(fields, true);
		Map<String, Object> paramsMap = new HashMap<>();
		for (Field field : fields) {
			String fieldName = field.getName();
			Object fieldValue = field.get(vo);
			if (fieldValue != null) {
				paramsMap.put(fieldName, fieldValue);
			}
		}
		if (vid > 0)
			paramsMap.put("id", vid);
		return this.queryPageBySql(sqlName, paramsMap, pageIndex, pageSize, clazzVo);
	}

	@Override
	public <T> PageVo<T> queryPage(String sqlName, T vo, int pageIndex,
								   int pageSize, Class<T> clazz) throws Exception {
		return queryPageBySql(sqlName, vo, pageIndex, pageSize, clazz, 0);
	}

	public <T> PageVo<T> queryPage(T vo, Class<?> clazz, int pageIndex, int pageSize, long vid) throws Exception {

		String sqlMapKey = clazz.getName() + "autoList";
		String sqlOriginal = getSqlByName(sqlMapKey);
		if (sqlOriginal == null) {
			String crlf = System.getProperty("line.separator");

			Set<String> fieldSet = new HashSet<>();
			String primaryKey = "";
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				Id id = method.getAnnotation(Id.class);
				Column column = method.getAnnotation(Column.class);

				if (id != null) {
					primaryKey = column.name();
				}
				if (column != null) {
					fieldSet.add(column.name().toUpperCase());
				}
			}
			StringBuilder sqlSelectBuilder = new StringBuilder(" SELECT ");
			StringBuilder sqlWhereBuilder = new StringBuilder(" WHERE 1=1 ");

			sqlWhereBuilder.append(crlf);
			sqlWhereBuilder.append("#if($id)").append(crlf);
			sqlWhereBuilder.append(" AND ").append(primaryKey).append(" = :id").append(crlf);
			sqlWhereBuilder.append("#end ").append(crlf);

			Class<?> clazzVo = vo.getClass();
			Field[] clazzFields = clazzVo.getDeclaredFields();
			Field[] fields = clazzFields;
			Class<?> superClazz = clazzVo.getSuperclass();
			if (Object.class != superClazz) {
				Field[] superFields = superClazz.getDeclaredFields();
				fields = new Field[clazzFields.length + superFields.length];
				System.arraycopy(clazzFields, 0, fields, 0, clazzFields.length);
				System.arraycopy(superFields, 0, fields, clazzFields.length, superFields.length);
			}
			AccessibleObject.setAccessible(fields, true);
			List<String> selectCols = new ArrayList<>();
			for (Field field : fields) {
				String fieldName = field.getName();
				if (fieldSet.contains(fieldName.toUpperCase())) {
					selectCols.add(fieldName);
					//Object fieldValue = field.get(vo);
					//if (fieldValue != null) {
					sqlWhereBuilder.append(" #if($").append(field.getName()).append(")").append(crlf);
					sqlWhereBuilder.append(" AND ").append(field.getName()).append(" = :").append(field.getName()).append(crlf);
					sqlWhereBuilder.append(" #end").append(crlf);
					//}
				}
			}
			if (selectCols.size() > 0)
				sqlSelectBuilder.append(StringUtils.join(selectCols, ", "));

			Table table = clazz.getAnnotation(Table.class);
			String tableName = table.name();
			sqlSelectBuilder.append(" From ").append(tableName);
			String msql = sqlSelectBuilder.append(sqlWhereBuilder).toString();

			synchronized (nameSqlMap) {
				nameSqlMap.put(sqlMapKey, msql);
			}
		}

		@SuppressWarnings("unchecked")
		Class<T> voclass = (Class<T>) vo.getClass();
		return queryPageBySql(sqlMapKey, vo, pageIndex > 0 ? pageIndex : 1, pageSize > 0 ? pageSize : 2000, voclass, vid);
	}

	@Override
	public <T> List<T> queryList(String sqlName, T vo, Class<T> clazz) throws Exception {
		return queryPageBySql(sqlName, vo, 0, 0, clazz, 0).getRows();
	}

	@Override
	public <T> T queryOne(String sqlName, T vo, Class<T> clazz)
			throws Exception {
		List<T> list = queryList(sqlName, vo, clazz);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 为简单sql增加逻辑删除标识符的where条件，默认仅查询有效数据
	 *
	 * @param sql
	 * @param paramsMap
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	private <T> String setDefaultWhereCondition(String sql, Map<String, Object> paramsMap, Class<T> clazzVo) throws Exception {
		Assert.hasText(sql);
		int wherePos = sql.toLowerCase().indexOf("where ");
		if (sql.toLowerCase().contains("join ") ||
				wherePos == -1 || wherePos != sql.toLowerCase().lastIndexOf("where ")) {// 若为复杂sql则直接返回原sql
			return sql;
		}
		StringBuilder newSql = new StringBuilder(sql.substring(0, wherePos + 6));
		T vo = clazzVo.newInstance();
		if (vo instanceof IDataEntity) {
			if (!paramsMap.containsKey("enumDataEntityStatus")) {
				paramsMap.put("enumDataEntityStatus", Constant.DataBase.Normal);
				newSql.append("enumDataEntityStatus = :enumDataEntityStatus AND ");
			}
		}
		newSql.append(sql.substring(wherePos + 6));
		return newSql.toString();
	}

}

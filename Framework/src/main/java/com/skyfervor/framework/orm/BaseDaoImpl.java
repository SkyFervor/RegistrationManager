package com.skyfervor.framework.orm;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import com.skyfervor.framework.vobase.PageVo;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.skyfervor.framework.constant.Constant;

public abstract class BaseDaoImpl<VO, Model> implements BaseDao<VO> {

	private Class<VO> VOClass;
	private Class<Model> ModelClass;

	@Autowired
	protected DynamicSqlTemplate dynamicSqlTemplate;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() throws Exception {

		Class<?> tempC = getClass();
		if (!BaseDaoImpl.class.isAssignableFrom(tempC))
			throw new Exception("Dao继承关系不符合规范。");

		while (!tempC.getSuperclass().equals(BaseDaoImpl.class)) {
			tempC = tempC.getSuperclass();
		}

		VOClass = (Class<VO>) ((ParameterizedType) tempC.getGenericSuperclass())
				.getActualTypeArguments()[0];
		ModelClass = (Class<Model>) ((ParameterizedType) tempC.getGenericSuperclass())
				.getActualTypeArguments()[1];
	}

	public VO insertVo(VO vo) throws Exception {
		Mapper mapper = new DozerBeanMapper();
		Model model = dynamicSqlTemplate.insert(vo, ModelClass);
		return mapper.map(model, VOClass);
	}

	public ArrayList<VO> insertVo(VO[] vos) throws Exception {
		ArrayList<VO> list = new ArrayList<VO>();
		Mapper mapper = new DozerBeanMapper();
		for (VO vo : vos) {
			Model model = dynamicSqlTemplate.insert(vo, ModelClass);
			list.add(mapper.map(model, VOClass));
		}
		return list;
	}

	public int updateVo(VO vo) throws Exception {
		return dynamicSqlTemplate.updateNonull(vo, ModelClass);
	}

	public int updateVo(VO[] vos) throws Exception {
		int updateCount = 0;
		for (VO vo : vos) {
			updateCount += dynamicSqlTemplate.updateNonull(vo, ModelClass);
		}
		return updateCount;
	}

	public PageVo<VO> queryPage(String sqlName, VO vo, int page, int rows) throws Exception {
		return dynamicSqlTemplate.queryPage(sqlName, vo, page, rows, VOClass);
	}

	public PageVo<VO> queryPage(VO vo, int page, int rows) throws Exception {
		return dynamicSqlTemplate.queryPage(vo, ModelClass, page, rows, 0);
	}

	public List<VO> queryList(String sqlName, VO vo) throws Exception {
		return dynamicSqlTemplate.queryList(sqlName, vo, VOClass);
	}

	public List<VO> queryList(VO vo) throws Exception {
		return dynamicSqlTemplate.queryPage(vo, ModelClass, 0, 0, 0).getRows();
	}

	public long queryCount(VO vo) throws Exception {
		return dynamicSqlTemplate.queryPage(vo, ModelClass, 0, 0, 0).getRecords();
	}

	public long queryCount(String sqlName, VO vo) throws Exception {
		return dynamicSqlTemplate.queryPage(sqlName, vo, 1, 10, VOClass).getRecords();
	}

	public VO queryOne(String sqlName, VO vo) throws Exception {
		return dynamicSqlTemplate.queryOne(sqlName, vo, VOClass);
	}
	
	public VO queryById(long id) throws Exception {
		PageVo<VO> vos = dynamicSqlTemplate.queryPage(VOClass.newInstance(), ModelClass, 0, 0, id);
		if (vos == null || vos.getRows() == null || vos.getRows().size() < 1)
			return null;
		return vos.getRows().get(0);
	}

	public void deleteVo(VO vo) throws Exception {
		dynamicSqlTemplate.delete(vo, ModelClass);
	}

	public void deleteVoNoById(VO vo, String[] selectFieldNameArray) throws Exception {
		dynamicSqlTemplate.deleteNoById(vo, ModelClass, selectFieldNameArray);
	}

	public void deleteVo(VO[] vos) throws Exception {
		for (VO vo : vos) {
			dynamicSqlTemplate.delete(vo, ModelClass);
		}
	}

	public void deleteVoForEnumDataEntityStatus(VO vo) throws Exception {
		if (vo instanceof IDataEntity) {
			((IDataEntity) vo).setEnumDataEntityStatus(Constant.DataBase.Deleted);
			dynamicSqlTemplate.updateNonull(vo, ModelClass);
		} else {
			throw new Exception(
					"deleteVoForEnumDataEntityStatus -> vo is not extends IDataEntity ");
		}
	}

}

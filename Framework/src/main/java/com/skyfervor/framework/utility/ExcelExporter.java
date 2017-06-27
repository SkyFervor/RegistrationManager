package com.skyfervor.framework.utility;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelExporter {
	private static Logger logger = LoggerFactory.getLogger(ExcelExporter.class);

	public static <T> void exportExcel(Collection<T> dataset, OutputStream out) throws Exception {
		List<String> headers = new ArrayList<String>();
		List<String> attrNames = new ArrayList<String>();
		Iterator<T> itor = dataset.iterator();
		while (itor.hasNext()) {
			T t = (T) itor.next();
			Field[] fields = t.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				}
				headers.add(fieldName);
				attrNames.add(fieldName);
			}
			break;
		}
		exportExcel(headers, attrNames, dataset, out);
	}

	public static <T> void exportExcel(List<String> headers, List<String> attrNames, Collection<T> dataset,
			OutputStream out) throws Exception {
		exportExcel("数据导出" + StringUtils.getUUIDString(), headers, attrNames, dataset, out, "yyyy-MM-dd HH:mm:ss");
	}

	public static <T> void exportExcel(String title, List<String> headers, List<String> attrNames,
			Collection<T> dataset, OutputStream out) throws Exception {
		exportExcel(title, headers, attrNames, dataset, out, "yyyy-MM-dd HH:mm:ss");
	}

	public static <T> void exportExcel(String title, List<String> headers, List<String> attrNames,
			Collection<T> dataset, OutputStream out, String pattern) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		try {
			HSSFSheet sheet = workbook.createSheet(title);
			sheet.setDefaultColumnWidth(30);
			HSSFRow row = sheet.createRow(0);
			for (int i = 0; i < headers.size(); i++) {
				HSSFCell cell = row.createCell(i);
				HSSFRichTextString text = new HSSFRichTextString(headers.get(i));
				cell.setCellValue(text);
			}

			Iterator<T> itor = dataset.iterator();
			int index = 0;
			while (itor.hasNext()) {
				index++;
				row = sheet.createRow(index);
				T t = (T) itor.next();
				for (int j = 0; j < attrNames.size(); j++) {
					String attrName = attrNames.get(j);
					String methodName = "get" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
					HSSFCell cell = row.createCell(j);
					Object value = null;
					try {
						Method method = t.getClass().getMethod(methodName);
						if (method != null) {
							value = method.invoke(t);
						}
					} catch (Exception e) {
						logger.info("invoke method failed",e);
						value = null;
					}

					if (value == null) {
						cell.setCellValue(new HSSFRichTextString(""));
						continue;
					}
					String textValue = null;
					if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else {
						textValue = value.toString();
					}
					if (textValue != null) {
						Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							cell.setCellValue(new HSSFRichTextString(textValue));
						}
					}
				}
			}

			workbook.write(out);
		} catch (IOException e) {
			logger.error("写入excel文档失败.",e);
			throw e;
		} finally {
			workbook.close();
		}

	}
}

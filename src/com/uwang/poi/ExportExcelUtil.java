package com.uwang.poi;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * ����Excel
 * @author liuyazhuang
 * @param <T>
 */
public class ExportExcelUtil<T>{
	
	// 2007 �汾���� ���֧��1048576�� xlsx
	public  final static String  EXCEl_FILE_2007 = "2007";
	// 2003 �汾 ���֧��65536 ��
	public  final static String  EXCEL_FILE_2003 = "2003";
	
	/**
	 * <p>
	 * ������ͷ��������Excel <br>
	 * ʱ���ʽĬ�ϣ�yyyy-MM-dd hh:mm:ss <br>
	 * </p>
	 * 
	 * @param title ������
	 * @param dataset ���ݼ���
	 * @param out �����
	 * @param version 2003 ���� 2007������ʱĬ������2003�汾
	 */
	public void exportExcel(String title, Collection<T> dataset, OutputStream out, String version) {
		if(StringUtils.isEmpty(version) || EXCEL_FILE_2003.equals(version.trim())){
			exportExcel2003(title, null, dataset, out, "yyyy-MM-dd HH:mm:ss");
		}else{
			exportExcel2007(title, null, dataset, out, "yyyy-MM-dd HH:mm:ss");
		}
	}
 
	/**
	 * <p>
	 * ��������ͷ�������е�Excel <br>
	 * ʱ���ʽĬ�ϣ�yyyy-MM-dd hh:mm:ss <br>
	 * </p>
	 * 
	 * @param title ������
	 * @param headers ͷ�����⼯��
	 * @param dataset ���ݼ���
	 * @param out �����
	 * @param version 2003 ���� 2007������ʱĬ������2003�汾
	 */
	public void exportExcel(String title,String[] headers, Collection<T> dataset, OutputStream out,String version) {
		if(StringUtils.isBlank(version) || EXCEL_FILE_2003.equals(version.trim())){
			exportExcel2003(title, headers, dataset, out, "yyyy-MM-dd HH:mm:ss");
		}else{
			exportExcel2007(title, headers, dataset, out, "yyyy-MM-dd HH:mm:ss");
		}
	}
 
	/**
	 * <p>
	 * ͨ��Excel��������,���÷�����Ʊ�������������ֶΣ�������д��Excel�ļ��� <br>
	 * �˰汾����2007���ϰ汾���ļ� (�ļ���׺��xlsx)
	 * </p>
	 * 
	 * @param title
	 *            ��������
	 * @param headers
	 *            ���ͷ�����⼯��
	 * @param dataset
	 *            ��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���JavaBean������Ķ��󡣴˷���֧�ֵ�
	 *            JavaBean���Ե����������л����������ͼ�String,Date
	 * @param out
	 *            ������豸�����������󣬿��Խ�EXCEL�ĵ������������ļ�����������
	 * @param pattern
	 *            �����ʱ�����ݣ��趨�����ʽ��Ĭ��Ϊ"yyyy-MM-dd hh:mm:ss"
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "resource" })
	public void exportExcel2007(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern) {
		// ����һ��������
		XSSFWorkbook workbook = new XSSFWorkbook();
		// ����һ�����
		XSSFSheet sheet = workbook.createSheet(title);
		// ���ñ��Ĭ���п��Ϊ15���ֽ�
		sheet.setDefaultColumnWidth(20);
		// ����һ����ʽ
		XSSFCellStyle style = workbook.createCellStyle();
		// ������Щ��ʽ
		style.setFillForegroundColor(new XSSFColor(java.awt.Color.gray));
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		// ����һ������
		XSSFFont font = workbook.createFont();
//		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("����"); 
		font.setColor(new XSSFColor(java.awt.Color.BLACK));
		font.setFontHeightInPoints((short) 11);
		// ������Ӧ�õ���ǰ����ʽ
		style.setFont(font);
		// ���ɲ�������һ����ʽ
		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(new XSSFColor(java.awt.Color.WHITE));
		style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		style2.setBorderTop(BorderStyle.THIN);
		style2.setAlignment(HorizontalAlignment.CENTER);
		style2.setVerticalAlignment(VerticalAlignment.CENTER);
//		 ������һ������
		XSSFFont font2 = workbook.createFont();
//		font2.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
		// ������Ӧ�õ���ǰ����ʽ
		style2.setFont(font2);
 
		// ������������
		XSSFRow row = sheet.createRow(0);
		XSSFCell cellHeader;
		for (int i = 0; i < headers.length; i++) {
			cellHeader = row.createCell(i);
			cellHeader.setCellStyle(style);
			cellHeader.setCellValue(new XSSFRichTextString(headers[i]));
		}
 
		// �����������ݣ�����������
		Iterator<T> it = dataset.iterator();
		int index = 0;
		T t;
		Field[] fields;
		Field field;
		XSSFRichTextString richString;
		Pattern p = Pattern.compile("^//d+(//.//d+)?$");
		Matcher matcher;
		String fieldName;
		String getMethodName;
		XSSFCell cell;
		Class tCls;
		Method getMethod;
		Object value;
		String textValue;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			t = (T) it.next();
			// ���÷��䣬����JavaBean���Ե��Ⱥ�˳�򣬶�̬����getXxx()�����õ�����ֵ
			fields = t.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(style2);
				field = fields[i];
				fieldName = field.getName();
				getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					tCls = t.getClass();
					getMethod = tCls.getMethod(getMethodName, new Class[] {});
					value = getMethod.invoke(t, new Object[] {});
					// �ж�ֵ�����ͺ����ǿ������ת��
					textValue = null;
					if (value instanceof Integer) {
						cell.setCellValue((Integer) value);
					} else if (value instanceof Float) {
						textValue = String.valueOf((Float) value);
						cell.setCellValue(textValue);
					} else if (value instanceof Double) {
						textValue = String.valueOf((Double) value);
						cell.setCellValue(textValue);
					} else if (value instanceof Long) {
						cell.setCellValue((Long) value);
					}
					if (value instanceof Boolean) {
						textValue = "��";
						if (!(Boolean) value) {
							textValue = "��";
						}
					} else if (value instanceof Date) {
						textValue = sdf.format((Date) value);
					} else {
						// �����������Ͷ������ַ����򵥴���
						if (value != null) {
							textValue = value.toString();
						}
					}
					if (textValue != null) {
						matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// �����ֵ���double����
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							richString = new XSSFRichTextString(textValue);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// ������Դ
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * <p>
	 * ͨ��Excel��������,���÷�����Ʊ�������������ֶΣ�������д��Excel�ļ��� <br>
	 * �˷�������2003�汾��excel,�ļ�����׺��xls <br>
	 * </p>
	 * 
	 * @param title
	 *            ��������
	 * @param headers
	 *            ���ͷ�����⼯��
	 * @param dataset
	 *            ��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���JavaBean������Ķ��󡣴˷���֧�ֵ�
	 *            JavaBean���Ե����������л����������ͼ�String,Date
	 * @param out
	 *            ������豸�����������󣬿��Խ�EXCEL�ĵ������������ļ�����������
	 * @param pattern
	 *            �����ʱ�����ݣ��趨�����ʽ��Ĭ��Ϊ"yyyy-MM-dd hh:mm:ss"
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "resource", "deprecation" })
	public void exportExcel2003(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern) {
		// ����һ��������
		HSSFWorkbook workbook = new HSSFWorkbook();
		// ����һ�����
		HSSFSheet sheet = workbook.createSheet(title);
		// ���ñ��Ĭ���п��Ϊ15���ֽ�
		sheet.setDefaultColumnWidth(20);
		// ����һ����ʽ
		HSSFCellStyle style = workbook.createCellStyle();
		// ������Щ��ʽ
		style.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		// ����һ������
		HSSFFont font = workbook.createFont();
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("����"); 
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 11);
		// ������Ӧ�õ���ǰ����ʽ
		style.setFont(font);
		// ���ɲ�������һ����ʽ
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		style2.setBorderTop(BorderStyle.THIN);
		style2.setAlignment(HorizontalAlignment.CENTER);
		style2.setVerticalAlignment(VerticalAlignment.CENTER);
		// ������һ������
		HSSFFont font2 = workbook.createFont();
//		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// ������Ӧ�õ���ǰ����ʽ
		style2.setFont(font2);
 
		// ������������
		HSSFRow row = sheet.createRow(0);
		HSSFCell cellHeader;
		for (int i = 0; i < headers.length; i++) {
			cellHeader = row.createCell(i);
			cellHeader.setCellStyle(style);
			cellHeader.setCellValue(new HSSFRichTextString(headers[i]));
		}
 
		// �����������ݣ�����������
		Iterator<T> it = dataset.iterator();
		int index = 0;
		T t;
		Field[] fields;
		Field field;
		HSSFRichTextString richString;
		Pattern p = Pattern.compile("^//d+(//.//d+)?$");
		Matcher matcher;
		String fieldName;
		String getMethodName;
		HSSFCell cell;
		Class tCls;
		Method getMethod;
		Object value;
		String textValue;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			t = (T) it.next();
			// ���÷��䣬����JavaBean���Ե��Ⱥ�˳�򣬶�̬����getXxx()�����õ�����ֵ
			fields = t.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(style2);
				field = fields[i];
				fieldName = field.getName();
				getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					tCls = t.getClass();
					getMethod = tCls.getMethod(getMethodName, new Class[] {});
					value = getMethod.invoke(t, new Object[] {});
					// �ж�ֵ�����ͺ����ǿ������ת��
					textValue = null;
					if (value instanceof Integer) {
						cell.setCellValue((Integer) value);
					} else if (value instanceof Float) {
						textValue = String.valueOf((Float) value);
						cell.setCellValue(textValue);
					} else if (value instanceof Double) {
						textValue = String.valueOf((Double) value);
						cell.setCellValue(textValue);
					} else if (value instanceof Long) {
						cell.setCellValue((Long) value);
					}
					if (value instanceof Boolean) {
						textValue = "��";
						if (!(Boolean) value) {
							textValue = "��";
						}
					} else if (value instanceof Date) {
						textValue = sdf.format((Date) value);
					} else {
						// �����������Ͷ������ַ����򵥴���
						if (value != null) {
							textValue = value.toString();
						}
					}
					if (textValue != null) {
						matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// �����ֵ���double����
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							richString = new HSSFRichTextString(textValue);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// ������Դ
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
package com.uwang.poi;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.uwang.util.DruidUtil;

/**
 * excel�ļ���ȡ������,֧��xls,xlsx���ָ�ʽ
 * @author Andrew
 *
 */
public class ExcelUtil {

	public static void main(String[] args)  {
		
//		List<ArrayList<String>> list = excelReader("D:\\opt\\eclipse\\jee-oxygen-workspace\\a-md5-demo\\src\\test.xls",0,1,2);
//		for (int i = 0; i < list.size(); i++) {
//			for (int j = 0; j < list.get(i).size(); j++) {
//				System.out.println(list.get(i).get(j));
//			}
//		}
//		Connection connection = DruidUtil.getConnection();
//		List<ArrayList<String>> list = excelReader("D:\\test.xls");
//		
//		PreparedStatement preparedStatement=null;
//		for (int i = 1; i < list.size(); i++) {
//			String sql = "INSERT INTO eif_user(eif_name,age,sex,img,work_experience) VALUES(?,?,?,?,?)";
//			preparedStatement = connection.prepareStatement(sql);
//			for (int j = 0; j < list.get(i).size(); j++) {
//				System.out.println(list.get(i).get(j));
//				//ִ��jdbc�Ĳ���
//				if(j>0){
//					try {
//						preparedStatement.setString(j, list.get(i).get(j));
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//				}	
//			}
//			
//			int row = preparedStatement.executeUpdate();
//			System.out.println(row);
//		}
		
	}
	
	/**
	 * ��װ��һ��xls�ĵ��뷽��
	 */
	public static void xlstest() throws IOException, SQLException{
		Connection connection = DruidUtil.getConnection();
		List<ArrayList<String>> list = excelReader("D:\\test.xls");
		
		PreparedStatement preparedStatement=null;
		for (int i = 1; i < list.size(); i++) {
			String sql = "INSERT INTO eif_user(eif_name,age,sex,img,work_experience) VALUES(?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(sql);
			for (int j = 0; j < list.get(i).size(); j++) {
				System.out.println(list.get(i).get(j));
				//ִ��jdbc�Ĳ���
				if(j>0){
					try {
						preparedStatement.setString(j, list.get(i).get(j));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}	
			}
			
			int row = preparedStatement.executeUpdate();
			System.out.println(row);
		}
	}
	
	
    /**
     * excel�ļ���ȡָ���е�����
     * @author Andrew
     * @param excelPath        �ļ���
     * @param args             ��Ҫ��ѯ���к�
     * @return    ArrayList<ArrayList<String>>    ��ά�ַ�������
     * @throws IOException
     */
    @SuppressWarnings({ "unused" })
    public static ArrayList<ArrayList<String>> excelReader(String excelPath,int ... args) throws IOException {
        // ����excel����������
        Workbook workbook = null;
        FormulaEvaluator formulaEvaluator = null;
        // ��ȡĿ���ļ�
        File excelFile = new File(excelPath);
        InputStream is = new FileInputStream(excelFile);
        // �ж��ļ���xlsx����xls
        if (excelFile.getName().endsWith("xlsx")) {
            workbook = new XSSFWorkbook(is);
            formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
        }else {
            workbook = new HSSFWorkbook(is);
            formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
        }
        
        //�ж�excel�ļ����Ƿ���ȷ
        if(workbook == null){
            System.err.println("δ��ȡ������,����·����");
            return null;
        }
        //������ά����,����excel��������
        ArrayList<ArrayList<String>> als = new ArrayList<ArrayList<String>>();
        //�����������е�sheet
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            Sheet sheet = workbook.getSheetAt(numSheet);
            //��ǰsheetҳ��Ϊ��,��������
            if (sheet == null) {
                continue;
            }
            // ����ÿ��sheet����ȡ���е�ÿһ��
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue; 
                }
                ArrayList<String> al = new ArrayList<String>();
                // ����ÿһ�е�ÿһ��
                for(int columnNum = 0 ; columnNum < args.length ; columnNum++){
                    Cell cell = row.getCell(args[columnNum]);
                    al.add(getValue(cell, formulaEvaluator));
                }
                als.add(al);
            }
        }
        is.close();
        return als;
    }
    
    /**
     * excel�ļ���ȡȫ����Ϣ
     * @author Andrew
     * @param excelPath        �ļ���
     * @return    ArrayList<ArrayList<String>>    ��ά�ַ�������
     * @throws IOException
     */
    @SuppressWarnings({ "unused" })
    public static ArrayList<ArrayList<String>> excelReader(String excelPath) throws IOException {
        // ����excel����������
        Workbook workbook = null;
        FormulaEvaluator formulaEvaluator = null;
        // ��ȡĿ���ļ�
        File excelFile = new File(excelPath);
        InputStream is = new FileInputStream(excelFile);
        // �ж��ļ���xlsx����xls
        if (excelFile.getName().endsWith("xlsx")) {
            workbook = new XSSFWorkbook(is);
            formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
        }else {
            workbook = new HSSFWorkbook(is);
            formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
        }
        
        //�ж�excel�ļ����Ƿ���ȷ
        if(workbook == null){
            System.err.println("δ��ȡ������,����·����");
            return null;
        }
        //������ά����,����excel��������
        ArrayList<ArrayList<String>> als = new ArrayList<ArrayList<String>>();
        //�����������е�sheet
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            Sheet sheet = workbook.getSheetAt(numSheet);
            //��ǰsheetҳ��Ϊ��,��������
            if (sheet == null) {
                continue;
            }
            // ����ÿ��sheet����ȡ���е�ÿһ��
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue; 
                }
                // ����ÿһ�е�ÿһ��
                ArrayList<String> al = new ArrayList<String>();
                for(int columnNum = 0 ; columnNum < row.getLastCellNum(); columnNum++){
                    Cell cell = row.getCell(columnNum);
                    al.add(getValue(cell, formulaEvaluator));
                }
                als.add(al);
            }
        }
        is.close();
        return als;
    }
    
    /**
     * excel�ļ������ݶ�ȡ,������׺Ϊxls,xlsx
     * @param xssfRow
     * @return
     */
    @SuppressWarnings("deprecation")
    private static String getValue(Cell cell, FormulaEvaluator formulaEvaluator) {
        if(cell==null){
            return null;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case Cell.CELL_TYPE_NUMERIC:
                // �ж�������ʱ�����ͻ�����ֵ����
                if (DateUtil.isCellDateFormatted(cell)) {
                    short format = cell.getCellStyle().getDataFormat();
                    SimpleDateFormat sdf = null;
                    /* �������ڸ�ʽ������ͨ��getDataFormat()ֵ���ж�
                     *     yyyy-MM-dd----- 14
                     *    yyyy��m��d��----- 31
                     *    yyyy��m��--------57
                     *    m��d��  --------- 58
                     *    HH:mm---------- 20
                     *    hʱmm��  --------- 32
                     */
                    if(format == 14 || format == 31 || format == 57 || format == 58){ 
                        //���� 
                        sdf = new SimpleDateFormat("yyyy-MM-dd"); 
                      }else if (format == 20 || format == 32) { 
                        //ʱ�� 
                        sdf = new SimpleDateFormat("HH:mm"); 
                      }
                    return sdf.format(cell.getDateCellValue());
                } else {
                    // �����������жϴ���
                    double cur = cell.getNumericCellValue();  
                    long longVal = Math.round(cur);  
                    Object inputValue = null;
                    if(Double.parseDouble(longVal + ".0") == cur) {   
                        inputValue = longVal;
                    }
                    else {   
                        inputValue = cur; 
                    }
                    return String.valueOf(inputValue);
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_FORMULA:
                //�Թ�ʽ���д���,���ع�ʽ������ֵ,ʹ��cell.getCellFormula()ֻ�᷵�ع�ʽ
                return String.valueOf(formulaEvaluator.evaluate(cell).getNumberValue());
            //Cell.CELL_TYPE_BLANK || Cell.CELL_TYPE_ERROR
            default:
                return null;
        }
    }   
}
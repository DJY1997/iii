package com.uwang.poi;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class Test {

	public static void main(String[] args) throws Exception{
		ExportExcelUtil<Student> util = new ExportExcelUtil<Student>();
		 // ׼������
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
        	 list.add(new Student(i,"��"+i,1+i));
        }
        String[] columnNames = { "ID", "����", "�Ա�" };
        util.exportExcel("�û�����", columnNames, list, new FileOutputStream("D:\\��ҵ���ű�.xls"), ExportExcelUtil.EXCEL_FILE_2003);
	}
}

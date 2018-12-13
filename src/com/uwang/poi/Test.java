package com.uwang.poi;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class Test {

	public static void main(String[] args) throws Exception{
		ExportExcelUtil<Student> util = new ExportExcelUtil<Student>();
		 // 准备数据
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
        	 list.add(new Student(i,"张"+i,1+i));
        }
        String[] columnNames = { "ID", "姓名", "性别" };
        util.exportExcel("用户导出", columnNames, list, new FileOutputStream("D:\\行业新闻表.xls"), ExportExcelUtil.EXCEL_FILE_2003);
	}
}

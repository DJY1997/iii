package com.uwang.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args) {
		
		
		
		// 得到连接
		Connection connection = DruidUtil.getConnection();
		
		String sql = "INSERT INTO users VALUES(DEFAULT,?,?);";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, "张三");
			preparedStatement.setString(2, "13456");
			int i = preparedStatement.executeUpdate();
			System.out.println(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}

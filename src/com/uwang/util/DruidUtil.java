package com.uwang.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * 创建数据源
 *
 */
public class DruidUtil {
	// 声明一个DruidDataSource数据源对象
	private static DruidDataSource dataSource;
	
	static {
		Properties properties = new Properties();
		try {
			InputStream inputStream = DruidUtil.class.getClassLoader().getResourceAsStream("druid.properties");
			properties.load(inputStream);
			// 获取到数据源的对象
			dataSource = (DruidDataSource)DruidDataSourceFactory.createDataSource(properties);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	// 通过druid得到连接
	public static Connection getConnection() {
		try {
			return dataSource.getConnection();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 关闭连接
	 */
	public static void close() {
		dataSource.close();
	}
	
	
	
}
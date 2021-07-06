/**************************************************************
 * Purpose JDBC Demo to perform various operations in database. 
 * @author Hrishikesh Ugavekar
 * @Version 1.0
 * @since 30-06-2021
 *
 ************************************************************/
package com.hrishikesh.jdbcDemo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JdbcDemo {
	private Connection connection;

	/**
	 * This method connects program to database.
	 * @return true if connection is successful otherwise false
	 */
	public boolean connectToDb(String jdbcURL , String userName , String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");
			System.out.println("Connecting to database " + jdbcURL);
			connection = DriverManager.getConnection(jdbcURL,userName,password);
			System.out.println("Connection successful");
			return true;
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
			return false;
		} catch (SQLException e) {
			System.out.println("Database Not Found");
			return false;
			
		}

	}
}

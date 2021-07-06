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
import java.util.ArrayList;

import model.EmployeeSalaryModel;


public class JdbcDemo {
	ArrayList<EmployeeSalaryModel> salaryList = new ArrayList<>();
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
	
	public int getSalaryFromDb(String query) {
		connectToDb("jdbc:mysql://localhost:3306/payrolldb?useSSL=false",
				"root",
				"Hrishi123!@#");
		try(Statement statement = connection.createStatement()){
			ResultSet resultset = statement.executeQuery(query);
			while(resultset.next()) {
				EmployeeSalaryModel employee = new EmployeeSalaryModel(resultset.getInt(1),
						resultset.getString(2),
						resultset.getInt(3),resultset.getInt(4));
				salaryList.add(employee);
			}
			return salaryList.size();
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
		
	}
}

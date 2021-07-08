/**************************************************************
 * Purpose JDBC Demo to perform various operations in database. 
 * @author Hrishikesh Ugavekar
 * @Version 1.0
 * @since 06-07-2021
 *
 ************************************************************/
package com.hrishikesh.jdbcDemo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	
	/**
	 * Gets the salary of employees from database
	 * Stores the salary of employees as a list
	 * @param query from test method
	 * @return number of employees
	 */
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
	
	/**
	 * Update the salary without using prepared statement
	 * @param name
	 */
	public void updateSalary() {
		connectToDb("jdbc:mysql://localhost:3306/payrolldb?useSSL=false",
				"root",
				"Hrishi123!@#");
		String query = "UPDATE salary set BasicPay=3000000 "
				+ "where idSalary = (select Salary_idSalary from employee where Employee_name = 'Terisa');";
		try(Statement statement = connection.createStatement()){
			statement.executeUpdate(query);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Update salary using prepared statement
	 * @param name
	 * @param salary
	 */
	public void updateSalaryUsingPreparedStatement(String name, int salary) {
		connectToDb("jdbc:mysql://localhost:3306/payrolldb?useSSL=false",
				"root",
				"Hrishi123!@#");
		String query = "UPDATE salary set BasicPay=? "
				+ "where idSalary = (select Salary_idSalary from employee where Employee_name = ?);";
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setInt(1, salary);
			preparedstatement.setString(2, name);
			preparedstatement.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param query from test method
	 * @return Basic pay of particular employee
	 */
	public int getUpdatedSalary(String query) {
		connectToDb("jdbc:mysql://localhost:3306/payrolldb?useSSL=false",
				"root",
				"Hrishi123!@#");
		try(Statement statement = connection.createStatement()){
			ResultSet resultset = statement.executeQuery(query);
			resultset.next();
			return resultset.getInt(1);
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * @param gender from test method
	 * @return number of employees of a particular gender.
	 */
	public int getNumberOfMalesOrFemales(String gender) {
		connectToDb("jdbc:mysql://localhost:3306/payrolldb?useSSL=false",
				"root",
				"Hrishi123!@#");
		String query = "select count(Gender) from employee where Gender = ?" ;
		try(PreparedStatement preparedstatement = connection.prepareStatement(query)) {
			preparedstatement.setString(1, String.valueOf(gender));
			ResultSet resultset = preparedstatement.executeQuery();
			resultset.next();
			System.out.println(resultset.getInt(1));
			return resultset.getInt(1);
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * When new person is added his salary should reflect in List.
	 * If any exception occurs query is rollbacked.
	 * @return size of salaryList
	 * @throws SQLException
	 */
	public int addNewEmployee() throws SQLException {
		connectToDb("jdbc:mysql://localhost:3306/payrolldb?useSSL=false",
				"root",
				"Hrishi123!@#");
		try {
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String query = "insert into  employee (Employee_name,Gender,Adress_idAdress,Salary_idSalary)"
					+ "values('Harshit','M',4,4);";
			statement.executeUpdate(query);
			
			String query2 = "SELECT idEmployee,Employee_name,Salary_idSalary,BasicPay FROM employee"
					+ " INNER JOIN salary ON employee.Salary_idSalary = salary.idSalary;";
			ResultSet resultset = statement.executeQuery(query2);
			resultset.last();
			
			EmployeeSalaryModel employee = new EmployeeSalaryModel(resultset.getInt(1),
					resultset.getString(2),
					resultset.getInt(3),resultset.getInt(4));
			salaryList.add(employee);
			
		}catch(SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}
		return salaryList.size();
		
	}
	
	public void print() {
		System.out.println(salaryList);
	}
	public static void main(String[] args) {
		JdbcDemo j = new JdbcDemo();
		j.print();
	}
}

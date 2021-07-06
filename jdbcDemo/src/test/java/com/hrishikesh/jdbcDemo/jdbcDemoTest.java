package com.hrishikesh.jdbcDemo;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class jdbcDemoTest {

	JdbcDemo jdbc = null;
	
	@Before
	public void setUp() {
		jdbc = new JdbcDemo();
	}
	
	@Test
	public void given_connectionToDatabase_whenSuccessful_ShouldReturnTrue() {
		boolean result = jdbc.connectToDb("jdbc:mysql://localhost:3306/payrolldb?useSSL=false","root",
				"Hrishi123!@#");
		Assert.assertEquals(true, result);
	}
	
	@Test
	public void givenPasswordOfDatabase_whenIncorrect_ShoudReturnFalse() {
		boolean result = jdbc.connectToDb("jdbc:mysql://localhost:3306/payrolldb?useSSL=false","root",
				"#######");
		Assert.assertEquals(false, result);
	}
	
	@Test
	public void givenURLOfDatabase_whenIncorrect_ShoudReturnFalse() {
		boolean result = jdbc.connectToDb("jdbc:mysql://localhost:1234/payrolldb?useSSL=false","root",
				"Hrishi123!@#");
		Assert.assertEquals(false, result);
	}
	
	@Test
	public void given_ListOfSalaryDetailsOfEmployess_ShouldReturnNumberOfEmployees() throws SQLException {
		int result = jdbc.getSalaryFromDb("SELECT idEmployee,Employee_name,Salary_idSalary,BasicPay FROM employee"
				+ " INNER JOIN salary ON employee.Salary_idSalary = salary.idSalary;");
		Assert.assertEquals(4, result);
	}
	
	@Test
	public void given_ListOfSalaryDetailsOfEmployess_WhenGivenWrongQuery_ShouldReturnZero() throws SQLException {
		int result = jdbc.getSalaryFromDb("SELECT * FROM employee");//Wrong Query, throws exception
		Assert.assertEquals(0, result);
	}
	
	@Test
	public void when_SalaryUpsated_WithoutUsingPreparedStatement_ShouldReflectSalaryInDatabase() {
		jdbc.updateSalary();
		int result = jdbc.getUpdatedSalary("select BasicPay from salary where idSalary = (select Salary_idSalary from employee where Employee_name = 'Terisa');");
		Assert.assertEquals(3000000, result);
	}
	
	@Test
	public void when_SalaryUpsated_WithUsingPreparedStatement_ShouldReflectSalaryInDatabase() {
		jdbc.updateSalaryUsingPreparedStatement("Hrishi", 99999);;
		int result = jdbc.getUpdatedSalary("select BasicPay from salary where idSalary = (select Salary_idSalary from employee where Employee_name = 'Hrishi');");
		Assert.assertEquals(99999, result);
	}
	
	@Test
	public void when_askedToGiveNumberOfMales_ShouldReturn2() {
		int result = jdbc.getNumberOfMalesOrFemales("M");
		Assert.assertEquals(2, result);
	}
}

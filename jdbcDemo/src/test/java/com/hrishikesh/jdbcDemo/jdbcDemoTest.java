package com.hrishikesh.jdbcDemo;
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
}

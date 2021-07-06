package model;

public class EmployeeSalaryModel {
	int idEmployee;
	String employeeName;
	int salaryId;
	int basicPay;
	
	
	
	@Override
	public String toString() {
		return "EmployeeSalaryModel [idEmployee=" + idEmployee + ", employeeName=" + employeeName + ", salaryId="
				+ salaryId + ", basicPay=" + basicPay + "]";
	}


	public EmployeeSalaryModel(int idEmployee, String employeeName, int salaryId, int basicPay) {
		super();
		this.idEmployee = idEmployee;
		this.employeeName = employeeName;
		this.salaryId = salaryId;
		this.basicPay = basicPay;
	}
	
	
	public int getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(int idEmployee) {
		this.idEmployee = idEmployee;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public int getSalaryId() {
		return salaryId;
	}
	public void setSalaryId(int salaryId) {
		this.salaryId = salaryId;
	}
	public int getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(int basicPay) {
		this.basicPay = basicPay;
	}
	

}

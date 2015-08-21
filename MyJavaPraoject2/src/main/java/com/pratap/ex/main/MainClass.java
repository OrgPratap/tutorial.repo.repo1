package com.pratap.ex.main;

import com.pratap.ex.emp.Emp;
import com.pratap.ex.empdao.EmpDAO;

public class MainClass 
{
	public static void main(String[] args) 
	{
		System.out.println("***WELCOME***");
		Emp emp = new Emp();
		emp.setEmpId("101");
		emp.setEmpName("karthik");
		emp.setEmpAge(27);
		emp.setEmpSalary(34988.00f);
		
		EmpDAO dao = new EmpDAO();
		if(dao.addEmp(emp)){
			System.out.println("emp added successfully...");
		}else{
			System.err.println("error!!!!!");
		}
	}
}

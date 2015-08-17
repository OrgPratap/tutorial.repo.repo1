package com.pratap.ex.empdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.pratap.ex.emp.Emp;

public class EmpDAO 
{
	private Connection connection;

	public EmpDAO() 
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("++++++++++DRIVER LOADED SUCCESSFULLY++++++++++++");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "root");
			System.out.println("++++++++++CONNECTED TO THE DATABSE{XE}");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean addEmp(Emp emp){
		
		String sql="inser into ";
		
		
		return false;
	}
	
}

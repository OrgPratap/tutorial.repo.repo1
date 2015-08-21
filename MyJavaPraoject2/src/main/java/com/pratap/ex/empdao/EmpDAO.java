package com.pratap.ex.empdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.pratap.ex.emp.Emp;

public class EmpDAO {
	private Connection connection;

	public EmpDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("++++++++++DRIVER LOADED SUCCESSFULLY++++++++++++");
			connection = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521:XE", "system", "root");
			System.out.println("++++++++++CONNECTED TO THE DATABSE{XE}");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean addEmp(Emp emp) {

		boolean queryExecutedProperly = false;
		String sql = "insert into emp values('" + emp.getEmpId() + "','" + emp.getEmpName() + "'," + emp.getEmpAge() + "," + emp.getEmpSalary() + ")";
		try {
			int result = -1;
			result = connection.createStatement().executeUpdate(sql);
			if(result != -1)
				queryExecutedProperly = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return queryExecutedProperly;
	}
}
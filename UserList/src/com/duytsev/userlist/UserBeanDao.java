package com.duytsev.userlist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBeanDao {
	
	private String errorLog;
	private Connection con;
	
	private boolean isUserExist(UserBean user) {
		return false;
		
	}
	
	public void connectDB(){
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			errorLog = "Failed to load the jdbc driver";
			e.printStackTrace();
		}
		
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system",
						"dbduika");
		} catch (SQLException e) {
			errorLog = "Failed to connect database";
			e.printStackTrace();
		}
		
		
	}
	
	public void addUser(UserBean user) {
		
		PreparedStatement preStatement = null;
		try {
			
			preStatement = con.prepareStatement("INSERT INTO AA_USERS(USER_ID, FNAME, "
					+ "LNAME, EMAIL) VALUES(?, ?, ?, ?)");
			
			preStatement.setString(1, "");
			preStatement.setString(2, user.getFirstName());
			preStatement.setString(3, user.getLastName());
			preStatement.setString(4, user.getEmail());
			
			preStatement.executeUpdate();
			
		} catch (SQLException e) {
			errorLog = "Failed to add user";
			e.printStackTrace();
		}
		
	}
	
	public List<UserBean> getAllUsers() {
		
		List<UserBean> users = new ArrayList<UserBean>();
		
		PreparedStatement preStatement = null;
		try {
			preStatement = con.prepareStatement("SELECT * FROM AA_USERS");
		
			ResultSet rs = null;
			rs = preStatement.executeQuery();
			
			while(rs.next()) {
				UserBean user = new UserBean();
				
				user.setFirstName(rs.getString("FNAME"));
				user.setLastName(rs.getString("LNAME"));
				user.setEmail(rs.getString("EMAIL"));
				
				users.add(user);

			}
		} catch (SQLException e) {
			errorLog = "Failed to get user list";
			e.printStackTrace();
		}
		
		return users;
		
	}
	
}

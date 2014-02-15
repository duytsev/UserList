package com.duytsev.userlist;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/*
 * Data Access Object class
 * Connects Oracle DB using jdbc
 */

public class UserBeanDao implements Closeable {

	private String errorLog;
	private Connection con;
	private ResultSet rs;
	private PreparedStatement preStatement;

	private boolean isUserExist(UserBean user) {

		try {
			preStatement = con
					.prepareStatement("SELECT * FROM AA_USERS WHERE FNAME = ? AND LNAME = ?");
			preStatement.setString(1, user.getFirstName());
			preStatement.setString(2, user.getLastName());

			rs = preStatement.executeQuery();

			return rs.isBeforeFirst();

		} catch (SQLException e) {
			errorLog = "Failed to check if user exists";
			e.printStackTrace();
		}
		return false;

	}

	public void connectDB() {

		DataSource ds = null;

		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:/jdbc/testAppDS");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}

		try {
			con = ds.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		/*
		 * try {
		 * 
		 * Class.forName("oracle.jdbc.driver.OracleDriver"); } catch
		 * (ClassNotFoundException e) { errorLog =
		 * "Failed to load the jdbc driver"; e.printStackTrace(); }
		 * 
		 * try { con =
		 * DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
		 * "system", "dbduika"); } catch (SQLException e) { errorLog =
		 * "Failed to connect database"; e.printStackTrace(); }
		 */

	}

	@Override
	public void close() throws IOException {
		try {
			rs.close();
			preStatement.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addUser(UserBean user) {

		if (!isUserExist(user)) {
			try {

				preStatement = con
						.prepareStatement("INSERT INTO AA_USERS(USER_ID, FNAME, "
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

	}

	public void deleteUser(String email) {
		try {

			preStatement = con.prepareStatement("DELETE "
					+ "FROM AA_USERS WHERE EMAIL = ?");

			preStatement.setString(1, email);

			preStatement.executeUpdate();

		} catch (SQLException e) {
			errorLog = "Failed to delete user";
			e.printStackTrace();
		}
	}

	public List<UserBean> getAllUsers() {

		List<UserBean> users = new ArrayList<UserBean>();

		try {
			preStatement = con.prepareStatement("SELECT * FROM AA_USERS");

			rs = preStatement.executeQuery();

			while (rs.next()) {
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

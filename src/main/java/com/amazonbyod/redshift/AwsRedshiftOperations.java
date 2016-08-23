package com.amazonbyod.redshift;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import com.amazonbyod.awsprop.AWSProjectProperties;

public class AwsRedshiftOperations {

	static AWSProjectProperties awscredentials = new AWSProjectProperties();
	static final String dbURL = "jdbc:redshift://immersion-project.cziozxqpyojq.us-west-2.redshift.amazonaws.com:5439/immersion";
	static final String MasterUsername = "immersion";
	static final String MasterUserPassword = "Immersion!2016";

	public Connection redShift() {
		Connection connect = null;
		try {
			Class.forName("com.amazon.redshift.jdbc4.Driver");
			Properties props = new Properties();
			props.setProperty("user", MasterUsername);
			props.setProperty("password", MasterUserPassword);
			connect = DriverManager.getConnection(dbURL, props);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connect;

	}

	public int redShiftDisconnect(Connection connect) {
		int flag = 0;
		if (connect != null) {
			try {
				connect.close();
				flag = 1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			flag = 0;
		}
		return flag;

	}

	public void insertStockData(Connection conn, List row) {

	}

	public void insertWeatherData(Connection conn, List row) {

	}

	public static void main(String[] args) throws IOException {
		Connection conn = null;
		Statement stmt = null;
		try {
			// Dynamically load driver at runtime.
			// Redshift JDBC 4.1 driver: com.amazon.redshift.jdbc41.Driver
			// Redshift JDBC 4 driver: com.amazon.redshift.jdbc4.Driver
			Class.forName("com.amazon.redshift.jdbc4.Driver");

			// Open a connection and define properties.
			System.out.println("Connecting to database...");
			Properties props = new Properties();

			// Uncomment the following line if using a keystore.
			// props.setProperty("ssl", "true");
			props.setProperty("user", MasterUsername);
			props.setProperty("password", MasterUserPassword);
			conn = DriverManager.getConnection(dbURL, props);

			// Try a simple query.
			System.out.println("Listing system tables...");
			stmt = conn.createStatement();
			String sql;
			sql = "select * from information_schema.tables;";
			ResultSet rs = stmt.executeQuery(sql);

			// Get the data from the result set.
			while (rs.next()) {
				// Retrieve two columns.
				String catalog = rs.getString("table_catalog");
				String name = rs.getString("table_name");

				// Display values.
				System.out.print("Catalog: " + catalog);
				System.out.println(", Name: " + name);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception ex) {
			// For convenience, handle all errors here.
			ex.printStackTrace();
		} finally {
			// Finally block to close resources.
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Finished connectivity test.");
	}
}

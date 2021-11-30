package com.parkit.parkingsystem.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Configuration of the database connection.
 *
 * @author Gilles Bernard
 * @version 1.0
 *
 */
public class DataBaseConfig {

    /**
     * The logger.
	 */
    private static final Logger LOGGER = LogManager.getLogger("DataBaseConfig");

	/**
	 * Connection to the database.
	 *
	 * @return Connection
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
		LOGGER.info("Create DB connection");
		Class.forName("com.mysql.cj.jdbc.Driver");

		try (InputStream fis = new FileInputStream("src/main/resources/database.properties")) {
			Properties prop = new Properties();
			prop.load(fis);

			return DriverManager.getConnection(
					  prop.getProperty("db.url.prod"),
					  prop.getProperty("db.user"),
					  prop.getProperty("db.password"));
		} catch (IOException e) {
			LOGGER.error("Error while opening database properties file.", e);
			throw e;
		}
	}

	/**
	 * Close the connection con.
	 *
	 * @param con
	 */
	public void closeConnection(final Connection con) {
		if (con != null) {
			try {
				con.close();
				LOGGER.info("Closing DB connection");
			} catch (SQLException e) {
				LOGGER.error("Error while closing connection", e);
			}
		}
	}

	/**
	 * Close the prepared statement ps.
	 *
	 * @param ps
	 */
	public void closePreparedStatement(final PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
				LOGGER.info("Closing Prepared Statement");
			} catch (SQLException e) {
				LOGGER.error("Error while closing prepared statement", e);
			}
		}
	}

	/**
	 * Close the result set rs.
	 *
	 * @param rs
	 */
	public void closeResultSet(final ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				LOGGER.info("Closing Result Set");
			} catch (SQLException e) {
				LOGGER.error("Error while closing result set", e);
			}
		}
	}
}

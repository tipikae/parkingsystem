package com.parkit.parkingsystem.unit.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.config.DataBaseConfig;

@ExtendWith(MockitoExtension.class)
class DataBaseConfigTest {
	
	private static DataBaseConfig dataBaseConfig;
	
	@Mock
	private static Connection con;
	
	@Mock
	private static PreparedStatement ps;
	
	@Mock
	private static ResultSet rs;

    @BeforeAll
    private static void setUp() {
    	dataBaseConfig = new DataBaseConfig();
    }
	
	@Test
	void closeConnection() {
		dataBaseConfig.closeConnection(con);
		try {
			verify(con, Mockito.times(1)).close();
		} catch (SQLException e) {
			fail("Failed to mock con.close()");
		}
	}

	@Test
	void closePreparedStatement() {
		dataBaseConfig.closePreparedStatement(ps);
		try {
			verify(ps, Mockito.times(1)).close();
		} catch (SQLException e) {
			fail("Failed to mock ps.close()");
		}
	}

	@Test
	void closeResultSet() {
		dataBaseConfig.closeResultSet(rs);
		try {
			verify(rs, Mockito.times(1)).close();
		} catch (SQLException e) {
			fail("Failed to mock rs.close()");
		}
	}
	
	@Test
	void closeConnectionWithNull() {
		dataBaseConfig.closeConnection(null);
		try {
			verify(con, Mockito.times(0)).close();
		} catch (SQLException e) {
			fail("Failed to mock con.close()");
		}
	}

	@Test
	void closePreparedStatementWithNull() {
		dataBaseConfig.closePreparedStatement(null);
		try {
			verify(ps, Mockito.times(0)).close();
		} catch (SQLException e) {
			fail("Failed to mock ps.close()");
		}
	}

	@Test
	void closeResultSetWithNull() {
		dataBaseConfig.closeResultSet(null);
		try {
			verify(rs, Mockito.times(0)).close();
		} catch (SQLException e) {
			fail("Failed to mock rs.close()");
		}
	}
}

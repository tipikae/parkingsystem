package com.parkit.parkingsystem.unit.config;

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
	void closeConnection() throws SQLException {
		dataBaseConfig.closeConnection(con);
		verify(con, Mockito.times(1)).close();
	}

	@Test
	void closePreparedStatement() throws SQLException {
		dataBaseConfig.closePreparedStatement(ps);
		verify(ps, Mockito.times(1)).close();
	}

	@Test
	void closeResultSet() throws SQLException {
		dataBaseConfig.closeResultSet(rs);
		verify(rs, Mockito.times(1)).close();
	}
	
	@Test
	void closeConnectionWithNull() throws SQLException {
		dataBaseConfig.closeConnection(null);
		verify(con, Mockito.times(0)).close();
	}

	@Test
	void closePreparedStatementWithNull() throws SQLException {
		dataBaseConfig.closePreparedStatement(null);
		verify(ps, Mockito.times(0)).close();
	}

	@Test
	void closeResultSetWithNull() throws SQLException {
		dataBaseConfig.closeResultSet(null);
		verify(rs, Mockito.times(0)).close();
	}
}

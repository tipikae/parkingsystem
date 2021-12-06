package com.parkit.parkingsystem.unit.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

@ExtendWith(MockitoExtension.class)
class TicketDAOTest {
	
	private static TicketDAO ticketDAO;
	
	@Mock
	private static DataBaseConfig dataBaseConfig;
    
    @BeforeAll
    private static void setUp() {
        ticketDAO = new TicketDAO();
    }
    
    @BeforeEach
    private void setUpPerTest() throws ClassNotFoundException, SQLException, IOException {
    	doThrow(ClassNotFoundException.class).when(dataBaseConfig).getConnection();
		ticketDAO.setDataBaseConfig(dataBaseConfig);
    }

	@Test
	public void saveTicketWithErrorConnection() {
		assertFalse(ticketDAO.saveTicket(new Ticket()));
	}

	@Test
	public void getTicketWithErrorConnection() {
		assertNull(ticketDAO.getTicket("ABCDEF"));
	}

	@Test
	public void updateTicketWithErrorConnection() {
		assertFalse(ticketDAO.updateTicket(new Ticket()));
	}

	@Test
	public void updateTicketInTimeWithErrorConnection() {
		assertFalse(ticketDAO.updateTicketInTime(new Ticket()));
	}
}

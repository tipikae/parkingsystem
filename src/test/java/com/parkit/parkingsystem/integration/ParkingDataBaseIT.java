package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.setDataBaseConfig(dataBaseTestConfig);
        ticketDAO = new TicketDAO();
        ticketDAO.setDataBaseConfig(dataBaseTestConfig);
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown(){
        dataBasePrepareService.clearDataBaseEntries();
    }

    @Test
    public void testParkingACar(){
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        
        // check ticket and parking
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        ParkingSpot parking = parkingSpotDAO.getParking(ticket.getParkingSpot().getId());
        assertNotNull(ticket);
        assertEquals(0, ticket.getPrice());
        assertNull(ticket.getOutTime());
        assertNotNull(parking);
        assertFalse(parking.isAvailable());
    }

    @Test
    public void testParkingLotExit(){
        testParkingACar();
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        
        // si ticket.inTime = ticket.outTime -> FareCalculatorService.calculateFare() throws IllegalArgumentException
        // donc on recule ticket.inTime
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        ticket.setInTime(new Date(ticket.getInTime().getTime() - (2 * 60 * 60 * 1000)));
        ticketDAO.updateTicketInTime(ticket);
        
        parkingService.processExitingVehicle();
        
        // check ticket and parking
        ticket = ticketDAO.getTicket("ABCDEF");
        ParkingSpot parking = parkingSpotDAO.getParking(ticket.getParkingSpot().getId());
        assertNotNull(ticket.getOutTime());
        assertTrue(parking.isAvailable());
    }

	
    @Test 
    public void testParkingLotExitDuringFreeParkingTime() {
    	testParkingACar();
    	ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        
    	// on recule l'heure d'entrée de 15 minutes
    	Ticket ticket = ticketDAO.getTicket("ABCDEF");
        ticket.setInTime(new Date(ticket.getInTime().getTime() - (15 * 60 * 1000)));
        ticketDAO.updateTicketInTime(ticket);
        
        parkingService.processExitingVehicle();
        
        // check price = 0
        ticket = ticketDAO.getTicket("ABCDEF");
        assertEquals(0.0, ticket.getPrice());
    }
	 
    @Test
    public void testParkingLotExitForRecurringUsers() {
    	testParkingLotExit();
    	testParkingACar();
    	ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

    	Ticket ticket = ticketDAO.getTicket("ABCDEF");
        ticket.setInTime(new Date(ticket.getInTime().getTime() - (60 * 60 * 1000)));
        ticketDAO.updateTicketInTime(ticket);
    	
        parkingService.processExitingVehicle();
        
        // check ticket price = 1.425
        ticket = ticketDAO.getTicket("ABCDEF");
        double diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(
        		ticket.getOutTime().getTime() - ticket.getInTime().getTime());
        double duration = diffInMinutes / 60;
        double fare = Fare.CAR_RATE_PER_HOUR;
        fare *= duration;
        fare -= fare * 0.05;
        assertTrue(ticket.isRecurrent());
        assertEquals(fare, ticket.getPrice());
    }
}

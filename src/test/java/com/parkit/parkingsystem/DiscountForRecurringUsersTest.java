package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

@ExtendWith(MockitoExtension.class)
class DiscountForRecurringUsersTest {

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;
    
    @Mock
    private static TicketDAO ticketDAO;

    @BeforeAll
    private static void setUp() {
        fareCalculatorService = new FareCalculatorService();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }

    @Test
    public void calculateFareCarWithRecurringUser() {
		Date inTime = new Date();
    	inTime.setTime( System.currentTimeMillis() - ( 60 * 60 * 1000) );
    	Date outTime = new Date();
    	ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
	  
    	ticket.setVehicleRegNumber("ABCDEF");
    	ticket.setInTime(inTime);
    	ticket.setOutTime(outTime);
    	ticket.setParkingSpot(parkingSpot);
    	when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
        fareCalculatorService.ticketDAO = ticketDAO;
        
    	fareCalculatorService.calculateFare(ticket);
    	
    	double fare = Fare.CAR_RATE_PER_HOUR;
    	fare -= fare * 0.05;
    	assertEquals(fare, ticket.getPrice()); 
    }
    
    @Test
    public void calculateFareCarWithoutRecurringUser() {		
    	Date inTime = new Date();
    	inTime.setTime( System.currentTimeMillis() - ( 60 * 60 * 1000) );
    	Date outTime = new Date();
    	ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
	  
    	ticket.setVehicleRegNumber("ABCDEF");
    	ticket.setInTime(inTime);
    	ticket.setOutTime(outTime);
    	ticket.setParkingSpot(parkingSpot);
    	when(ticketDAO.getTicket(anyString())).thenReturn(null);
        fareCalculatorService.ticketDAO = ticketDAO;
        
    	fareCalculatorService.calculateFare(ticket);
    	
    	double fare = Fare.CAR_RATE_PER_HOUR;
    	assertEquals(fare, ticket.getPrice()); 
    }
}

package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

class DiscountForRecurringUsersTest {

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;
    
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
    	ticket.setRecurrent(true);
        
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
    	ticket.setRecurrent(false);
        
    	fareCalculatorService.calculateFare(ticket);
    	
    	double fare = Fare.CAR_RATE_PER_HOUR;
    	assertEquals(fare, ticket.getPrice()); 
    }
}

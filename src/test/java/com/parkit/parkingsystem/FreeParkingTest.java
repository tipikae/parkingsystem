package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

class FreeParkingTest {

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;
    
    @BeforeAll
    private static void setUp() {
        fareCalculatorService = new FareCalculatorService();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
        ticket.setRecurrent(false);
    }

    @ParameterizedTest(name = "{0} minutes must be free parking time")
    @ValueSource(ints = {15, 29})
    public void calculateFareCarWithFreeParkingTime(int arg) {
    	Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  arg * 60 * 1000) ); // {arg} minutes parking time should give 0 * parking fare per hour
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        
        fareCalculatorService.calculateFare(ticket);
        assertEquals(0.0 , ticket.getPrice());
    }

    @Test
    public void calculateFareCarWithMoreThanFreeParkingTime() {
    	Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  30 * 60 * 1000) );//30 minutes parking time should give a half parking fare per hour
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        ticket.setVehicleRegNumber("ABCDEF");
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        
        fareCalculatorService.calculateFare(ticket);
        assertEquals(0.5 * Fare.CAR_RATE_PER_HOUR, ticket.getPrice());
    }
}

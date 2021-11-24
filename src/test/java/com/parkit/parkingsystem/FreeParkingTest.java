package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;

@ExtendWith(MockitoExtension.class)
class FreeParkingTest {

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
    	when(ticketDAO.getTicket(anyString())).thenReturn(null);
        fareCalculatorService.ticketDAO = ticketDAO;
        
        fareCalculatorService.calculateFare(ticket);
        assertEquals(0.5 * Fare.CAR_RATE_PER_HOUR, ticket.getPrice());
    }
}

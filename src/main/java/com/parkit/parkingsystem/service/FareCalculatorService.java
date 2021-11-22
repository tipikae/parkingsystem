package com.parkit.parkingsystem.service;

import java.util.concurrent.TimeUnit;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {
	
	private static final int FREE_MINUTES_PARKING = 30;

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        // on calcule la différence en minutes entre l'heure d'entrée et l'heure de sortie
        double diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(
        		ticket.getOutTime().getTime() - ticket.getInTime().getTime());
        // on convertit les minutes en heures
        double duration = diffInMinutes / 60;
        
        switch (ticket.getParkingSpot().getParkingType()){
	        case CAR: {
	        	ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
	            break;
	        }
	        case BIKE: {
	        	ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
	            break;
	        }
	        default: throw new IllegalArgumentException("Unkown Parking Type");
	    }
    }
}
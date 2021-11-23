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
        
        // on définit le prix en fonction des différentes conditions
        double price = 0.0;
        
        if(duration <= ((double)FREE_MINUTES_PARKING / 60)) {
        	// le parking est gratuit pour les durées inférieures ou égales à 30 minutes
	        price = 0.0;
        } else {
        	// sinon le parking est payant
        	switch (ticket.getParkingSpot().getParkingType()){
	            case CAR: {
	                price = duration * Fare.CAR_RATE_PER_HOUR;
	                break;
	            }
	            case BIKE: {
	                price = duration * Fare.BIKE_RATE_PER_HOUR;
	                break;
	            }
	            default: throw new IllegalArgumentException("Unkown Parking Type");
	        }
        }
        
        ticket.setPrice(price);
    }
}
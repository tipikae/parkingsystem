package com.parkit.parkingsystem.service;

import java.util.concurrent.TimeUnit;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {
	
	private static final int FREE_MINUTES_PARKING = 30;
	private static final double DISCOUNT = 0.05;

	public TicketDAO ticketDAO = new TicketDAO();

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
        if(duration < ((double)FREE_MINUTES_PARKING / 60)) {
        	// le parking est gratuit pour les durées inférieures ou égales à 30 minutes
	        price = 0.0;
        } else {
        	// sinon le parking est payant
        	double fare;
        	switch (ticket.getParkingSpot().getParkingType()){
	            case CAR: {
	                fare =  Fare.CAR_RATE_PER_HOUR;
	                break;
	            }
	            case BIKE: {
	                fare =  Fare.BIKE_RATE_PER_HOUR;
	                break;
	            }
	            default: throw new IllegalArgumentException("Unkown Parking Type");
	        }
        	
        	if(isRecurringUser(ticket.getVehicleRegNumber())) fare -= fare * DISCOUNT;
        	price = duration * fare;
        }
        
        ticket.setPrice(price);
    }

    private boolean isRecurringUser(String vehicleRegNumber) {
    	boolean result = false;
		if(ticketDAO.getTicket(vehicleRegNumber) != null) {
			result = true;
		}
		return result;
	}
}
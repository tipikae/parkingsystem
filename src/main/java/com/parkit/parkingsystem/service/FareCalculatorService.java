package com.parkit.parkingsystem.service;

import java.util.concurrent.TimeUnit;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

/**
 * Service for calculate fare.
 *
 * @author Gilles Bernard
 * @version 1.0
 *
 */
public class FareCalculatorService {

	/**
	 * Calculate fare.
	 *
	 * @param ticket
	 */
	public void calculateFare(final Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}

		// on calcule la différence en minutes entre l'heure d'entrée et l'heure de
		// sortie
		double diffInMinutes = TimeUnit.MILLISECONDS
				.toMinutes(ticket.getOutTime().getTime() - ticket.getInTime().getTime());
		// on convertit les minutes en heures
		double duration = diffInMinutes / 60;

		// on définit le prix en fonction des différentes conditions
		double price = 0.0;
		if (duration < ((double) Fare.FREE_MINUTES_PARKING / 60)) {
			// le parking est gratuit pour les durées inférieures ou égales à 30 minutes
			price = 0.0;
		} else {
			// sinon le parking est payant
			double fare;
			switch (ticket.getParkingSpot().getParkingType()) {
				case CAR:
					fare = Fare.CAR_RATE_PER_HOUR;
					break;
				case BIKE:
					fare = Fare.BIKE_RATE_PER_HOUR;
					break;
				default:
					throw new IllegalArgumentException("Unkown Parking Type");
			}

			if (ticket.isRecurrent()) {
				fare -= fare * Fare.DISCOUNT;
			}
			price = duration * fare;
		}

		ticket.setPrice(price);
	}
}

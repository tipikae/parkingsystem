package com.parkit.parkingsystem.constants;

/**
 * Constant to calculate fare.
 *
 * @author Gilles Bernard
 * @version 1.0
 *
 */
public class Fare {

	protected Fare() {
		throw new UnsupportedOperationException();
	}

    /**
     * Bike rate per hour.
     */
    public static final double BIKE_RATE_PER_HOUR = 1.0;

    /**
     * Car rate per hour.
     */
    public static final double CAR_RATE_PER_HOUR = 1.5;

    /**
     * Free parking time in minutes.
     */
	public static final int FREE_MINUTES_PARKING = 30;

	/**
	 * Discount rate.
	 */
	public static final double DISCOUNT = 0.05;
}

package com.parkit.parkingsystem.model;

import java.util.Date;

/**
 * Ticket model.
 *
 * @author Gilles Bernard
 * @version 1.0
 *
 */
public class Ticket {
    /**
     * Id.
     */
	private int id;
    /**
     * ParkingSpot.
     */
	private ParkingSpot parkingSpot;
    /**
     * Vehicle registration number.
     */
	private String vehicleRegNumber;
    /**
     * Price.
     */
	private double price;
    /**
     * In time.
     */
	private Date inTime;
	/**
	 * Out time.
	 */
	private Date outTime;
    /**
     * Is recurrent user.
     */
	private boolean isRecurrent;

    /**
     * Get id.
     *
     * @return int
     */
	public int getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param mId
     */
	public void setId(final int mId) {
        this.id = mId;
    }

    /**
     * Get parking spot.
     *
     * @return ParkingSpot
     */
	public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    /**
     * Set parking spot.
     *
     * @param mParkingSpot
     */
	public void setParkingSpot(final ParkingSpot mParkingSpot) {
        this.parkingSpot = mParkingSpot;
    }

    /**
     * Get vehicle registration number.
     *
     * @return String
     */
	public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    /**
     * Set vehicle registration number.
     *
     * @param mVehicleRegNumber
     */
	public void setVehicleRegNumber(final String mVehicleRegNumber) {
        this.vehicleRegNumber = mVehicleRegNumber;
    }

    /**
     * Get price.
     *
     * @return double
     */
	public double getPrice() {
        return price;
    }

    /**
     * Set price.
     *
     * @param mPrice
     */
	public void setPrice(final double mPrice) {
        this.price = mPrice;
    }

    /**
     * Get inTime.
     *
     * @return Date
     */
	public Date getInTime() {
        return (Date) inTime.clone();
    }

    /**
     * Set inTime.
     *
     * @param mInTime
     */
	public void setInTime(final Date mInTime) {
        this.inTime = (Date) mInTime.clone();
    }

    /**
     * Get outTime.
     *
     * @return Date
     */
	public Date getOutTime() {
        return outTime;
    }

    /**
     * Set outTime.
     *
     * @param mOutTime
     */
	public void setOutTime(Date mOutTime) {
        this.outTime = mOutTime;
    }

	/**
	 * Get recurrent.
	 *
	 * @return boolean
	 */
	public boolean isRecurrent() {
		return isRecurrent;
	}

	/**
	 * Set recurrent.
	 *
	 * @param mIsRecurrent
	 */
	public void setRecurrent(final boolean mIsRecurrent) {
		this.isRecurrent = mIsRecurrent;
	}
}

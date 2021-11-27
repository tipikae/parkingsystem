package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;

/**
 * ParkingSpot model.
 *
 * @author Gilles Bernard
 * @version 1.0
 *
 */
public class ParkingSpot {
    /**
     * Number.
     */
	private int number;

	/**
	 * Parking type.
	 */
	private ParkingType parkingType;

	/**
	 * Availability.
	 */
	private boolean isAvailable;

    /**
     * Constructor.
     *
     * @param mNumber
     * @param mParkingType
     * @param mIsAvailable
     */
	public ParkingSpot(final int mNumber, final ParkingType mParkingType, final boolean mIsAvailable) {
        this.number = mNumber;
        this.parkingType = mParkingType;
        this.isAvailable = mIsAvailable;
    }

    /**
     * Get id.
     *
     * @return int
     */
	public int getId() {
        return number;
    }

    /**
     * Set id.
     *
     * @param mNumber
     */
	public void setId(final int mNumber) {
        this.number = mNumber;
    }

    /**
     * Get parking type.
     *
     * @return ParkingType
     */
	public ParkingType getParkingType() {
        return parkingType;
    }

    /**
     * Set parking type.
     *
     * @param mParkingType
     */
	public void setParkingType(final ParkingType mParkingType) {
        this.parkingType = mParkingType;
    }

    /**
     * Get availability.
     *
     * @return boolean
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Set availability.
     *
     * @param mAvailable
     */
    public void setAvailable(final boolean mAvailable) {
        isAvailable = mAvailable;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
        	return true;
        }
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }
        ParkingSpot that = (ParkingSpot) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return number;
    }
}

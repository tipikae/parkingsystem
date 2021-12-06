package com.parkit.parkingsystem.constants;

/**
 * Constant SQl queries.
 *
 * @author Gilles Bernard
 * @version 1.0
 *
 */
public class DBConstants {

	protected DBConstants() {
		throw new UnsupportedOperationException();
	}

	/*
	 * Queries
	 */

    /**
     * SQL query to get next parking spot.
     */
	public static final String QUERY_GET_NEXT_PARKING_SPOT = "select min(PARKING_NUMBER) from parking where AVAILABLE = true and TYPE = ?";

	/**
	 * SQL query to update parking spot.
	 */
	public static final String QUERY_UPDATE_PARKING_SPOT = "update parking set available = ? where PARKING_NUMBER = ?";

	/**
	 * SQL query to get parking spot.
	 */
	public static final String QUERY_GET_PARKING_SPOT = "select PARKING_NUMBER, AVAILABLE, TYPE from parking where PARKING_NUMBER = ?";

	/**
	 * SQL query to save a ticket.
	 */
	public static final String QUERY_SAVE_TICKET = "insert into ticket(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME, IS_RECURRENT) values(?,?,?,?,?,?)";

	/**
	 * SQL query to update a ticket.
	 */
	public static final String QUERY_UPDATE_TICKET = "update ticket set PRICE=?, OUT_TIME=? where ID=?";

	/**
	 * SQL query to to update ticket inTime.
	 */
	public static final String QUERY_UPDATE_TICKET_IN_TIME = "update ticket set IN_TIME=? where ID=?";

	/**
	 * SQL query to get a ticket.
	 */
	public static final String QUERY_GET_TICKET = "select t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE, t.IS_RECURRENT from ticket t,parking p where p.parking_number = t.parking_number and t.VEHICLE_REG_NUMBER=? order by t.IN_TIME DESC limit 1";

	/*
	 * Fields
	 */

	/**
	 * GET_NEXT_PARKING_SPOT TYPE field.
	 */
	public static final int GET_NEXT_PARKING_SPOT_FIELD_TYPE = 1;

	/**
	 * UPDATE_PARKING_SPOT AVAILABLE field.
	 */
	public static final int UPDATE_PARKING_SPOT_FIELD_AVAILABLE = 1;

	/**
	 * UPDATE_PARKING_SPOT PARKING_NUMBER field.
	 */
	public static final int UPDATE_PARKING_SPOT_FIELD_PARKING_NUMBER = 2;

	/**
	 * GET_PARKING_SPOT PARKING_NUMBER field.
	 */
	public static final int GET_PARKING_SPOT_FIELD_PARKING_NUMBER = 1;

	/**
	 * SAVE_TICKET PARKING_NUMBER field.
	 */
	public static final int SAVE_TICKET_FIELD_PARKING_NUMBER = 1;

	/**
	 * SAVE_TICKET VEHICLE_REG_NUMBER field.
	 */
	public static final int SAVE_TICKET_FIELD_VEHICLE_REG_NUMBER = 2;

	/**
	 * SAVE_TICKET PRICE field.
	 */
	public static final int SAVE_TICKET_FIELD_PRICE = 3;

	/**
	 * SAVE_TICKET IN_TIME field.
	 */
	public static final int SAVE_TICKET_FIELD_IN_TIME = 4;

	/**
	 * SAVE_TICKET OUT_TIME field.
	 */
	public static final int SAVE_TICKET_FIELD_OUT_TIME = 5;

	/**
	 * SAVE_TICKET IS_RECURRENT field.
	 */
	public static final int SAVE_TICKET_FIELD_IS_RECURRENT = 6;

	/**
	 * UPDATE_TICKET PRICE field.
	 */
	public static final int UPDATE_TICKET_FIELD_PRICE = 1;

	/**
	 * UPDATE_TICKET OUT_TIME field.
	 */
	public static final int UPDATE_TICKET_FIELD_OUT_TIME = 2;

	/**
	 * UPDATE_TICKET ID field.
	 */
	public static final int UPDATE_TICKET_FIELD_ID = 3;

	/**
	 * UPDATE_TICKET_IN_TIME IN_TIME field.
	 */
	public static final int UPDATE_TICKET_IN_TIME_FIELD_IN_TIME = 1;

	/**
	 * UPDATE_TICKET_IN_TIME ID field.
	 */
	public static final int UPDATE_TICKET_IN_TIME_FIELD_ID = 2;

	/**
	 * GET_TICKET VEHICLE_REG_NUMBER field.
	 */
	public static final int GET_TICKET_FIELD_VEHICLE_REG_NUMBER = 1;

	/*
	 * Results
	 */

	/**
	 * GET_NEXT_PARKING_SPOT PARKING_NUMBER result.
	 */
	public static final int GET_NEXT_PARKING_SPOT_RESULT_PARKING_NUMBER = 1;

	/**
	 * GET_PARKING_SPOT PARKING_NUMBER result.
	 */
	public static final int GET_PARKING_SPOT_RESULT_PARKING_NUMBER = 1;

	/**
	 * GET_PARKING_SPOT AVAILABLE result.
	 */
	public static final int GET_PARKING_SPOT_RESULT_AVAILABLE = 2;

	/**
	 * GET_PARKING_SPOT TYPE result.
	 */
	public static final int GET_PARKING_SPOT_RESULT_TYPE = 3;

	/**
	 * GET_TICKET PARKING_NUMBER result.
	 */
	public static final int GET_TICKET_RESULT_PARKING_NUMBER = 1;

	/**
	 * GET_TICKET ID result.
	 */
	public static final int GET_TICKET_RESULT_ID = 2;

	/**
	 * GET_TICKET PRICE result.
	 */
	public static final int GET_TICKET_RESULT_PRICE = 3;

	/**
	 * GET_TICKET IN_TIME result.
	 */
	public static final int GET_TICKET_RESULT_IN_TIME = 4;

	/**
	 * GET_TICKET OUT_TIME result.
	 */
	public static final int GET_TICKET_RESULT_OUT_TIME = 5;

	/**
	 * GET_TICKET TYPE result.
	 */
	public static final int GET_TICKET_RESULT_TYPE = 6;

	/**
	 * GET_TICKET IS_RECURRENT result.
	 */
	public static final int GET_TICKET_RESULT_IS_RECURRENT = 7;
}

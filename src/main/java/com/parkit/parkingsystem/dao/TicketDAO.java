package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * DAO for Ticket model.
 *
 * @author Gilles Bernard
 * @version 1.0
 *
 */
public class TicketDAO {

	/**
	 * The logger.
	 */
	private static final Logger LOGGER = LogManager.getLogger("TicketDAO");

	/**
	 * The database configuration.
	 */
	private DataBaseConfig dataBaseConfig;

	/**
	 * Constructor.
	 */
	public TicketDAO() {
		dataBaseConfig = new DataBaseConfig();
	}

	/**
	 * Get the database configuration.
	 *
	 * @return DataBaseConfig
	 */
	public DataBaseConfig getDataBaseConfig() {
		return dataBaseConfig;
	}

	/**
	 * Set the database configuration.
	 *
	 * @param dbConfig
	 */
	public void setDataBaseConfig(final DataBaseConfig dbConfig) {
		this.dataBaseConfig = dbConfig;
	}

	/**
	 * Save a ticket.
	 *
	 * @param ticket
	 * @return boolean
	 */
	public boolean saveTicket(final Ticket ticket) {
		Connection con = null;
		PreparedStatement ps = null;
		boolean result = false;
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.QUERY_SAVE_TICKET);
			ps.setInt(DBConstants.SAVE_TICKET_FIELD_PARKING_NUMBER, ticket.getParkingSpot().getId());
			ps.setString(DBConstants.SAVE_TICKET_FIELD_VEHICLE_REG_NUMBER, ticket.getVehicleRegNumber());
			ps.setDouble(DBConstants.SAVE_TICKET_FIELD_PRICE, ticket.getPrice());
			ps.setTimestamp(DBConstants.SAVE_TICKET_FIELD_IN_TIME, new Timestamp(ticket.getInTime().getTime()));
			ps.setTimestamp(DBConstants.SAVE_TICKET_FIELD_OUT_TIME, (ticket.getOutTime() == null) ? null : (new Timestamp(ticket.getOutTime().getTime())));
			ps.setBoolean(DBConstants.SAVE_TICKET_FIELD_IS_RECURRENT, ticket.isRecurrent());
			result = ps.execute();
		} catch (Exception ex) {
			LOGGER.error("Error saving ticket", ex);
		} finally {
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
		
		return result;
	}

	/**
	 * Get a ticket.
	 *
	 * @param vehicleRegNumber
	 * @return Ticket
	 */
	public Ticket getTicket(final String vehicleRegNumber) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Ticket ticket = null;
		
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.QUERY_GET_TICKET);
			ps.setString(1, vehicleRegNumber);
			rs = ps.executeQuery();
			if (rs.next()) {
				ticket = new Ticket();
				ParkingSpot parkingSpot = new ParkingSpot(rs.getInt(DBConstants.GET_TICKET_RESULT_PARKING_NUMBER), ParkingType.valueOf(rs.getString(DBConstants.GET_TICKET_RESULT_TYPE)), false);
				ticket.setParkingSpot(parkingSpot);
				ticket.setId(rs.getInt(DBConstants.GET_TICKET_RESULT_ID));
				ticket.setVehicleRegNumber(vehicleRegNumber);
				ticket.setPrice(rs.getDouble(DBConstants.GET_TICKET_RESULT_PRICE));
				ticket.setInTime(rs.getTimestamp(DBConstants.GET_TICKET_RESULT_IN_TIME));
				ticket.setOutTime(rs.getTimestamp(DBConstants.GET_TICKET_RESULT_OUT_TIME));
				ticket.setRecurrent(rs.getBoolean(DBConstants.GET_TICKET_RESULT_IS_RECURRENT));
			}
		} catch (Exception ex) {
			LOGGER.error("Error getting ticket, regNumber=" + vehicleRegNumber, ex);
		} finally {
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
		
		return ticket;
	}

	/**
	 * Update a ticket.
	 *
	 * @param ticket
	 * @return boolean
	 */
	public boolean updateTicket(final Ticket ticket) {
		Connection con = null;
		PreparedStatement ps = null;
		boolean result = false;
		
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.QUERY_UPDATE_TICKET);
			ps.setDouble(DBConstants.UPDATE_TICKET_FIELD_PRICE, ticket.getPrice());
			ps.setTimestamp(DBConstants.UPDATE_TICKET_FIELD_OUT_TIME, new Timestamp(ticket.getOutTime().getTime()));
			ps.setInt(DBConstants.UPDATE_TICKET_FIELD_ID, ticket.getId());
			ps.execute();
			result = true;
		} catch (Exception ex) {
			LOGGER.error("Error saving ticket info", ex);
		} finally {
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
		
		return result;
	}

	/**
	 * Update inTime ticket.
	 *
	 * @param ticket
	 * @return boolean
	 */
	public boolean updateTicketInTime(final Ticket ticket) {
		Connection con = null;
		PreparedStatement ps = null;
		boolean result = false;
		
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.QUERY_UPDATE_TICKET_IN_TIME);
			ps.setTimestamp(DBConstants.UPDATE_TICKET_IN_TIME_FIELD_IN_TIME, new Timestamp(ticket.getInTime().getTime()));
			ps.setInt(DBConstants.UPDATE_TICKET_IN_TIME_FIELD_ID, ticket.getId());
			ps.execute();
			result = true;
		} catch (Exception ex) {
			LOGGER.error("Error saving ticket inTime", ex);
		} finally {
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
		return result;
	}
}

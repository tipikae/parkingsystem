package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * DAO for ParkingSpot model.
 *
 * @author Gilles Bernard
 * @version 1.0
 *
 */
public class ParkingSpotDAO {
	/**
	 * The logger.
	 */
	private static final Logger LOGGER = LogManager.getLogger("ParkingSpotDAO");

	/**
	 * The database configuration.
	 */
	private DataBaseConfig dataBaseConfig;

	/**
	 * Constructor.
	 */
	public ParkingSpotDAO() {
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
	 * Get the next available parking slot.
	 *
	 * @param parkingType
	 * @return int
	 */
	public int getNextAvailableSlot(final ParkingType parkingType) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = -1;
		
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.QUERY_GET_NEXT_PARKING_SPOT);
			ps.setString(DBConstants.GET_NEXT_PARKING_SPOT_FIELD_TYPE, parkingType.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt(DBConstants.GET_NEXT_PARKING_SPOT_RESULT_PARKING_NUMBER);
			}
		} catch (Exception ex) {
			LOGGER.error("Error fetching next available slot", ex);
		} finally {
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
		
		return result;
	}

	/**
	 * Update a parking spot.
	 *
	 * @param parkingSpot
	 * @return boolean
	 */
	public boolean updateParking(final ParkingSpot parkingSpot) {
		// update the availability for that parking slot
		Connection con = null;
		PreparedStatement ps = null;
		boolean result = false;
		
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.QUERY_UPDATE_PARKING_SPOT);
			ps.setBoolean(DBConstants.UPDATE_PARKING_SPOT_FIELD_AVAILABLE, parkingSpot.isAvailable());
			ps.setInt(DBConstants.UPDATE_PARKING_SPOT_FIELD_PARKING_NUMBER, parkingSpot.getId());
			int updateRowCount = ps.executeUpdate();
			result = updateRowCount == 1;
		} catch (Exception ex) {
			LOGGER.error("Error updating parking info", ex);
		} finally {
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}
		
		return result;
	}

	/**
	 * Get a parking spot.
	 *
	 * @param id
	 * @return ParkingSpot
	 */
	public ParkingSpot getParking(final int id) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ParkingSpot parking = null;
		
		try {
			con = dataBaseConfig.getConnection();
			ps = con.prepareStatement(DBConstants.QUERY_GET_PARKING_SPOT);
			ps.setInt(DBConstants.GET_PARKING_SPOT_FIELD_PARKING_NUMBER, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				parking = new ParkingSpot(id,
						ParkingType.valueOf(rs.getString(DBConstants.GET_PARKING_SPOT_RESULT_TYPE)),
						rs.getBoolean(DBConstants.GET_PARKING_SPOT_RESULT_AVAILABLE));
			}
		} catch (Exception ex) {
			LOGGER.error("Error getting parking, parking_number=" + id, ex);
		} finally {
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
			dataBaseConfig.closeConnection(con);
		}

		return parking;
	}
}

package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.InteractiveShellConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * Service for parking.
 *
 * @author Gilles Bernard
 * @version 1.0
 *
 */
public class ParkingService {

	/**
	 * The logger.
	 */
	private static final Logger LOGGER = LogManager.getLogger("ParkingService");

	/**
	 * The calculator.
	 */
	private static FareCalculatorService fareCalculatorService = new FareCalculatorService();

	/**
	 * InputReaderUtil.
	 */
	private InputReaderUtil inputReaderUtil;
	/**
	 * ParkingSpot DAO.
	 */
	private ParkingSpotDAO parkingSpotDAO;
	/**
	 * Ticket DAO.
	 */
	private TicketDAO ticketDAO;

	/**
	 * Constructor.
	 *
	 * @param mInputReaderUtil
	 * @param mParkingSpotDAO
	 * @param mTicketDAO
	 */
	public ParkingService(final InputReaderUtil mInputReaderUtil, final ParkingSpotDAO mParkingSpotDAO, final TicketDAO mTicketDAO) {
		this.inputReaderUtil = mInputReaderUtil;
		this.parkingSpotDAO = mParkingSpotDAO;
		this.ticketDAO = mTicketDAO;
	}

	/**
	 * Process for incoming vehicles.
	 */
	public void processIncomingVehicle() {
		try {
			ParkingSpot parkingSpot = getNextParkingNumberIfAvailable();
			if (parkingSpot != null && parkingSpot.getId() > 0) {
				String vehicleRegNumber = getVehichleRegNumber();
				parkingSpot.setAvailable(false);
				parkingSpotDAO.updateParking(parkingSpot); // allot this parking space and mark it's availability as
															// false

				boolean isRecurrent = isRecurringUser(vehicleRegNumber);

				Date inTime = new Date();
				Ticket ticket = new Ticket();
				// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
				// ticket.setId(ticketID);
				ticket.setParkingSpot(parkingSpot);
				ticket.setVehicleRegNumber(vehicleRegNumber);
				ticket.setPrice(0);
				ticket.setInTime(inTime);
				ticket.setOutTime(null);
				if (isRecurrent) {
					ticket.setRecurrent(true);
				} else {
					ticket.setRecurrent(false);
				}
				ticketDAO.saveTicket(ticket);

				if (isRecurrent) {
					System.out.println("Welcome back! As a recurring user of our parking lot, "
							+ "you'll benefit from a 5% discount.");
				}
				System.out.println("Generated Ticket and saved in DB");
				System.out.println("Please park your vehicle in spot number:" + parkingSpot.getId());
				System.out.println("Recorded in-time for vehicle number:" + vehicleRegNumber + " is:" + inTime);
			} else {
				System.out.println("Sorry there is no parking slot available.");
			}
		} catch (Exception e) {
			LOGGER.error("Unable to process incoming vehicle", e);
		}
	}

	private String getVehichleRegNumber() throws Exception {
		System.out.println("Please type the vehicle registration number and press enter key");
		return inputReaderUtil.readVehicleRegistrationNumber();
	}

	/**
	 * Get the next available parking number.
	 *
	 * @return ParkingSpot
	 */
	public ParkingSpot getNextParkingNumberIfAvailable() {
		int parkingNumber = 0;
		ParkingSpot parkingSpot = null;
		try {
			ParkingType parkingType = getVehichleType();
			parkingNumber = parkingSpotDAO.getNextAvailableSlot(parkingType);
			if (parkingNumber > 0) {
				parkingSpot = new ParkingSpot(parkingNumber, parkingType, true);
			} else {
				throw new Exception("Error fetching parking number from DB. Parking slots might be full");
			}
		} catch (IllegalArgumentException ie) {
			LOGGER.error("Error parsing user input for type of vehicle", ie);
		} catch (Exception e) {
			LOGGER.error("Error fetching next available parking slot", e);
		}
		return parkingSpot;
	}

	private ParkingType getVehichleType() {
		System.out.println("Please select vehicle type from menu");
		System.out.println(InteractiveShellConstants.MENU_CAR + " CAR");
		System.out.println(InteractiveShellConstants.MENU_BIKE + " BIKE");
		int input = inputReaderUtil.readSelection();
		switch (input) {
			case InteractiveShellConstants.MENU_CAR:
				return ParkingType.CAR;
			case InteractiveShellConstants.MENU_BIKE:
				return ParkingType.BIKE;
			default:
				System.out.println("Incorrect input provided");
				throw new IllegalArgumentException("Entered input is invalid");
		}
	}

	/**
	 * Process for exiting vehicle.
	 */
	public void processExitingVehicle() {
		try {
			String vehicleRegNumber = getVehichleRegNumber();
			Ticket ticket = ticketDAO.getTicket(vehicleRegNumber);
			if (ticket != null && ticket.getOutTime() == null) {
				Date outTime = new Date();
				ticket.setOutTime(outTime);
				fareCalculatorService.calculateFare(ticket);
				if (ticketDAO.updateTicket(ticket)) {
					ParkingSpot parkingSpot = ticket.getParkingSpot();
					parkingSpot.setAvailable(true);
					parkingSpotDAO.updateParking(parkingSpot);
					System.out.println("Please pay the parking fare:" + ticket.getPrice());
					System.out.println(
							"Recorded out-time for vehicle number:" + ticket.getVehicleRegNumber() + " is:" + outTime);
				} else {
					System.out.println("Unable to update ticket information. Error occurred");
				}
			} else {
				System.out.println("No vehicle with registration number: " + vehicleRegNumber + " was found.");
			}
		} catch (Exception e) {
			LOGGER.error("Unable to process exiting vehicle", e);
		}
	}

	private boolean isRecurringUser(final String vehicleRegNumber) {
		if (ticketDAO.getTicket(vehicleRegNumber) != null) {
			return true;
		}
		return false;
	}
}

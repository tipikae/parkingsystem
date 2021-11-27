package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.InteractiveShellConstants;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * User interface.
 *
 * @author Gilles Bernard
 * @version 1.0
 *
 */
public class InteractiveShell {

	/**
	 * The logger.
	 */
	private static final Logger LOGGER = LogManager.getLogger("InteractiveShell");

	protected InteractiveShell() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Load the interface.
	 */
	public static void loadInterface() {
		LOGGER.info("App initialized!!!");
		System.out.println("Welcome to Parking System!");

		boolean continueApp = true;
		InputReaderUtil inputReaderUtil = new InputReaderUtil();
		ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
		TicketDAO ticketDAO = new TicketDAO();
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

		while (continueApp) {
			loadMenu();
			int option = inputReaderUtil.readSelection();
			switch (option) {
				case InteractiveShellConstants.MENU_INCOMING:
					parkingService.processIncomingVehicle();
					break;
				case InteractiveShellConstants.MENU_OUTCOMING:
					parkingService.processExitingVehicle();
					break;
				case InteractiveShellConstants.MENU_SHUTDOWN:
					System.out.println("Exiting from the system!");
					continueApp = false;
					break;
				default:
					System.out.println("Unsupported option. Please enter a number corresponding to the provided menu");
			}
		}
	}

	private static void loadMenu() {
		System.out.println("Please select an option. Simply enter the number to choose an action");
		System.out.println(InteractiveShellConstants.MENU_INCOMING + " New Vehicle Entering - Allocate Parking Space");
		System.out.println(InteractiveShellConstants.MENU_OUTCOMING + " Vehicle Exiting - Generate Ticket Price");
		System.out.println(InteractiveShellConstants.MENU_SHUTDOWN + " Shutdown System");
	}
}

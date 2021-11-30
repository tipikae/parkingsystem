package com.parkit.parkingsystem.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/**
 * Class for reading input user.
 *
 * @author Gilles Bernard
 * @version 1.0
 *
 */
public class InputReaderUtil {

	/**
	 * The scanner.
	 */
	public static Scanner scan = new Scanner(System.in, "utf-8");
	/**
	 * The logger.
	 */
	private static final Logger LOGGER = LogManager.getLogger("InputReaderUtil");

	/**
	 * Read the user selection.
	 *
	 * @return int
	 */
	public int readSelection() {
		try {
			int input = Integer.parseInt(scan.nextLine());
			return input;
		} catch (Exception e) {
			LOGGER.error("Error while reading user input from Shell", e);
			System.out.println("Error reading input. Please enter valid number for proceeding further");
			return -1;
		}
	}

	/**
	 * Read the vehicle registration number.
	 *
	 * @return String
	 * @throws Exception
	 */
	public String readVehicleRegistrationNumber() throws Exception {
		try {
			String vehicleRegNumber = scan.nextLine();
			if (vehicleRegNumber == null || vehicleRegNumber.trim().length() == 0) {
				throw new IllegalArgumentException("Invalid input provided");
			}
			return vehicleRegNumber;
		} catch (Exception e) {
			LOGGER.error("Error while reading user input from Shell", e);
			System.out.println("Error reading input. Please enter a valid string for vehicle registration number");
			throw e;
		}
	}

}

package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The entry point of the application.
 *
 * @author Gilles Bernard
 * @version 1.0
 *
 */
public final class App {
    /**
     * The logger.
     */
    private static final Logger LOGGER = LogManager.getLogger("App");

    protected App() {
		throw new UnsupportedOperationException();
    }

    /**
     *
     * @param args an array of arguments
     */
    public static void main(final String[] args) {
        LOGGER.info("Initializing Parking System");
        InteractiveShell.loadInterface();
    }
}

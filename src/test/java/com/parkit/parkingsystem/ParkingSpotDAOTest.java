package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.model.ParkingSpot;

@ExtendWith(MockitoExtension.class)
class ParkingSpotDAOTest {

	private static ParkingSpotDAO parkingSpotDAO;
	
	@Mock
	private static DataBaseConfig dataBaseConfig;
    
    @BeforeAll
    private static void setUp() {
    	parkingSpotDAO = new ParkingSpotDAO();
    }
    
    @BeforeEach
    private void setUpPerTest() {
    	try {
			doThrow(ClassNotFoundException.class).when(dataBaseConfig).getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			fail("Failed to mock doThrow on dataBaseConfig");
		}
    	parkingSpotDAO.setDataBaseConfig(dataBaseConfig);
    }
    
	@Test
	void getNextAvailableSpotWithErrorConnection() {
		assertEquals(-1, parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE));
	}
    
	@Test
	void updateParkingWithErrorConnection() {
		assertFalse(parkingSpotDAO.updateParking(new ParkingSpot(0, null, false)));
	}
    
	@Test
	void getParkingWithErrorConnection() {
		assertNull(parkingSpotDAO.getParking(1));
	}
}

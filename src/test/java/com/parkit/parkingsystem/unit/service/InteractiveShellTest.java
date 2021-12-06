package com.parkit.parkingsystem.unit.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.service.InteractiveShell;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
class InteractiveShellTest {
	
	@Mock
	private static ParkingService parkingService;
	
	@Mock
	private static InputReaderUtil inputReaderUtil;
	
	@Test
	void loadInterface_whenInputOne_thenCallProcessIncomingVehicle() {
		when(inputReaderUtil.readSelection()).thenReturn(1, 3);
		InteractiveShell.inputReaderUtil = inputReaderUtil;
		InteractiveShell.parkingService = parkingService;
		
	    InteractiveShell.loadInterface();
	    verify(parkingService, Mockito.times(1)).processIncomingVehicle();
	}
	
	@Test
	void loadInterface_whenInputTwo_thenCallProcessExitingVehicle() {
		when(inputReaderUtil.readSelection()).thenReturn(2, 3);
		InteractiveShell.inputReaderUtil = inputReaderUtil;
		InteractiveShell.parkingService = parkingService;
		
	    InteractiveShell.loadInterface();
	    verify(parkingService, Mockito.times(1)).processExitingVehicle();
	}

}

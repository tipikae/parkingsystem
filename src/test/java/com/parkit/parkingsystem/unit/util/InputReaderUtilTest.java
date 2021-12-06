package com.parkit.parkingsystem.unit.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.util.InputReaderUtil;

class InputReaderUtilTest {
	
	private static InputReaderUtil inputReaderUtil;
	
	@BeforeAll
	private static void setUp() {
	}

	@Test
	void readSelection_whenIntegerParseOne_thenReturnsOne() {
	    System.setIn(new ByteArrayInputStream("1\n".getBytes()));
	    InputReaderUtil.scan = new Scanner(System.in);
		inputReaderUtil = new InputReaderUtil();
		
		assertEquals(1, inputReaderUtil.readSelection());
	}

	@Test
	void readSelection_whenIntegerParseIntThrowsError_thenReturnsMinusOne() {
	    System.setIn(new ByteArrayInputStream("a\n".getBytes()));
	    InputReaderUtil.scan = new Scanner(System.in);
		inputReaderUtil = new InputReaderUtil();
		
		assertEquals(-1, inputReaderUtil.readSelection());
	}

	@Test
	void readVehicleRegistrationNumber_whenInputIsNotNull_thenReturnsString() throws Exception {
		String regNumber = "ABCDEF";
		System.setIn(new ByteArrayInputStream((regNumber + "\n").getBytes()));
	    InputReaderUtil.scan = new Scanner(System.in);
		inputReaderUtil = new InputReaderUtil();
		
		assertEquals(regNumber, inputReaderUtil.readVehicleRegistrationNumber());
	}

	@Test
	void readVehicleRegistrationNumber_whenInputIsNull_thenThrowsException() {
		System.setIn(new ByteArrayInputStream("\n".getBytes()));
	    InputReaderUtil.scan = new Scanner(System.in);
		inputReaderUtil = new InputReaderUtil();
		
		assertThrows(Exception.class, () -> inputReaderUtil.readVehicleRegistrationNumber());
	}
}

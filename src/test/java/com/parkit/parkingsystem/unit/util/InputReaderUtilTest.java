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
	    InputReaderUtil.SCAN = new Scanner(System.in);
		inputReaderUtil = new InputReaderUtil();
		
		assertEquals(1, inputReaderUtil.readSelection());
	}

	@Test
	void readSelection_whenIntegerParseIntThrowsError_thenReturnsMinusOne() {
	    System.setIn(new ByteArrayInputStream("a\n".getBytes()));
	    InputReaderUtil.SCAN = new Scanner(System.in);
		inputReaderUtil = new InputReaderUtil();
		
		assertEquals(-1, inputReaderUtil.readSelection());
	}

	@Test
	void readVehicleRegistrationNumber_whenInputIsNotNull_thenReturnsString() {
		String regNumber = "ABCDEF";
		System.setIn(new ByteArrayInputStream((regNumber + "\n").getBytes()));
	    InputReaderUtil.SCAN = new Scanner(System.in);
		inputReaderUtil = new InputReaderUtil();
		
		try {
			assertEquals(regNumber, inputReaderUtil.readVehicleRegistrationNumber());
		} catch (Exception e) {
			fail("Assertion not OK", e);
		}
	}

	@Test
	void readVehicleRegistrationNumber_whenInputIsNull_thenThrowsException() {
		System.setIn(new ByteArrayInputStream("\n".getBytes()));
	    InputReaderUtil.SCAN = new Scanner(System.in);
		inputReaderUtil = new InputReaderUtil();
		
		assertThrows(Exception.class, () -> inputReaderUtil.readVehicleRegistrationNumber());
	}
}

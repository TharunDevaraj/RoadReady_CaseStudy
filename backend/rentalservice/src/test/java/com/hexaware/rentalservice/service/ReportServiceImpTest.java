package com.hexaware.rentalservice.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReportServiceImpTest {

	@Autowired
	ReportServiceImp reportService;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	@Disabled
	void testGetTotalRevenue() {
		double result = reportService.getTotalRevenue();

        assertEquals(5000.0, result);
	}

	@Test
	@Disabled
	void testGetTotalReservations() {
		long count = reportService.getTotalReservations();

        assertEquals(4L, count);
	}

}

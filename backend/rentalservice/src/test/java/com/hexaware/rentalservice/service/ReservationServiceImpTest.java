package com.hexaware.rentalservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.rentalservice.dto.ReservationDTO;
import com.hexaware.rentalservice.entity.Reservation;
import com.hexaware.rentalservice.repository.ReservationRepository;

@SpringBootTest
class ReservationServiceImpTest {

	@Autowired
	ReservationServiceImp reservationService;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	@Disabled
	void testCreateReservation() {
		
		ReservationDTO dto = new ReservationDTO();
        dto.setStartDate(LocalDate.now().plusDays(1));
        dto.setEndDate(LocalDate.now().plusDays(5));
        dto.setCustomerId(100L);
        dto.setCarId(200L);
        
        ReservationDTO result = reservationService.createReservation(dto);

        assertNotNull(result);
        assertEquals("Reserved", result.getReservationStatus());
		
	}

	@Test
	@Disabled
	void testGetAllReservations() {
		
		 List<ReservationDTO> result = reservationService.getAllReservations();

	        assertEquals(4, result.size());
		
	}

	@Test
	@Disabled
	void testGetReservationById() {
		
		ReservationDTO dto = reservationService.getReservationById(52L);

        assertEquals(52, dto.getReservationId());
        assertEquals(100, dto.getCustomerId());
		
	}

	@Test
	@Disabled
	void testUpdateReservation() {
		
		ReservationDTO dto = reservationService.getReservationById(52L);
		LocalDate originalEndDate = dto.getEndDate();
		dto.setEndDate(dto.getEndDate().plusDays(1));
		
		ReservationDTO updated = reservationService.updateReservation(dto);
		
		assertEquals(originalEndDate.plusDays(1), updated.getEndDate());
		
	}

	@Test
	@Disabled
	void testCancelReservationById() {
		
		reservationService.cancelReservationById(52L);
		
		ReservationDTO dto= reservationService.getReservationById(52L);
		
		assertEquals("Cancelled",dto.getReservationStatus() );
		assertThrows(RuntimeException.class, () -> reservationService.cancelReservationById(100L));
	}

	@Test
	@Disabled
	void testFindBookedCars() {
		
		LocalDate startDate=LocalDate.of(2025, 06, 01);
		LocalDate endDate=LocalDate.of(2025, 06, 07);

        List<Long> result = reservationService.findBookedCars(startDate, endDate);

        assertEquals(1, result.size());
	}

	@Test
	@Disabled
	void testCheckIn() {
		ReservationDTO result = reservationService.checkIn(52L);

        assertEquals("Reserved", result.getReservationStatus());
        assertNotNull(result.getCheckInTime());
	}

	@Test
	@Disabled
	void testCheckOut() {
		 ReservationDTO result = reservationService.checkOut(1L);

	     assertEquals("Completed", result.getReservationStatus());
	     assertNotNull(result.getCheckOutTime());
		
	}

}

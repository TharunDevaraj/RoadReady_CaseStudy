package com.hexaware.rentalservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.rentalservice.dto.PaymentDTO;
import com.hexaware.rentalservice.entity.Payment;
import com.hexaware.rentalservice.exception.PaymentNotFoundException;
@SpringBootTest
class PaymentServiceImpTest {

	@Autowired
	PaymentServiceImp paymentService;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	@Disabled
	void testMakePayment() {
		PaymentDTO payment = new PaymentDTO();
        payment.setPaymentType("Credit Card");
        payment.setPaymentDate(LocalDate.now());
        payment.setAmount(500.0);
        payment.setReservationId(1001L);
        
        PaymentDTO result = paymentService.makePayment(payment);

        assertNotNull(result);
        assertEquals(payment.getAmount(), result.getAmount());
        assertEquals(payment.getPaymentType(), result.getPaymentType());
		
	}

	@Test
	@Disabled
	void testGetPaymentById() throws PaymentNotFoundException {
		
		PaymentDTO result = paymentService.getPaymentById(52L);

        assertNotNull(result);
		
	}

	@Test
	@Disabled
	void testGetAllPayments() {
		
		List<PaymentDTO> result = paymentService.getAllPayments();

        assertEquals(4, result.size());
		
	}

	@Test
	@Disabled
	void testGetPaymentsByReservationId() {
		
		List<PaymentDTO> result = paymentService.getPaymentsByReservationId(1001L);

        assertEquals(1, result.size());
		
	}

	@Test
	@Disabled
	void testGetPaymentsByCustomerId() {
		List<PaymentDTO> result = paymentService.getPaymentsByCustomerId(1L);

        assertEquals(2, result.size());
	}

}

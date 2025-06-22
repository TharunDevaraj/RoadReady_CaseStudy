package com.hexaware.rentalservice.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.rentalservice.dto.PaymentDTO;
import com.hexaware.rentalservice.exception.PaymentNotFoundException;
import com.hexaware.rentalservice.service.IPaymentService;

import jakarta.validation.Valid;

/**
 * Date: 02-06-2025
 * Author: Tharun D
 * REST controller for managing payment operations such as makepayment,
 * getpayment by paymentId or by reservationId.
 */

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/payment")
public class PaymentRestController {

	@Autowired
    IPaymentService paymentService;

    @PostMapping("/make")
    public ResponseEntity<PaymentDTO> makePayment(@RequestBody @Valid PaymentDTO paymentDTO) {
        PaymentDTO payment = paymentService.makePayment(paymentDTO);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @GetMapping("/get/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long paymentId) throws PaymentNotFoundException {
        PaymentDTO dto = paymentService.getPaymentById(paymentId);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByReservationId(@PathVariable Long reservationId) {
        List<PaymentDTO> payments = paymentService.getPaymentsByReservationId(reservationId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByCustomerId(@PathVariable Long customerId) {
        List<PaymentDTO> payments = paymentService.getPaymentsByCustomerId(customerId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}

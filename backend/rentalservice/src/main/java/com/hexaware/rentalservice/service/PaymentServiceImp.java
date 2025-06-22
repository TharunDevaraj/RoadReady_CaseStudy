package com.hexaware.rentalservice.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hexaware.rentalservice.dto.PaymentDTO;
import com.hexaware.rentalservice.dto.ReservationDTO;
import com.hexaware.rentalservice.entity.Payment;
import com.hexaware.rentalservice.entity.Reservation;
import com.hexaware.rentalservice.exception.PaymentNotFoundException;
import com.hexaware.rentalservice.repository.PaymentRepository;
import com.hexaware.rentalservice.repository.ReservationRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Date: 02-06-2025
 * Author: Tharun D
 * Handles all business logic related to payments such as 
 * makePayments, getting Payment details, etc.
 */

@Service
@Slf4j
public class PaymentServiceImp implements IPaymentService{
	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	IReservationService reservationService;
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public PaymentDTO makePayment(PaymentDTO paymentDTO) {
		log.info("Creating payment for reservationId={}, amount={} on {}", 
                paymentDTO.getReservationId(), paymentDTO.getAmount(), LocalDate.now());
		Payment payment = new Payment();
		
		if(reservationRepository.findById(paymentDTO.getReservationId())==null)
		{
			log.warn("No reservation with reservation id:"+paymentDTO.getReservationId());
			throw new RuntimeException("Reservation Id not found ");
		}
        
        payment.setPaymentType(paymentDTO.getPaymentType());
        payment.setAmount(paymentDTO.getAmount());
        payment.setReservationId(paymentDTO.getReservationId());
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentStatus("Pending");
        
        reservationService.updateReservationStatus(paymentDTO.getReservationId(),"Reserved");
        
        ReservationDTO reservation=reservationService.getReservationById(paymentDTO.getReservationId());
        
        String vehicleServiceUrl = "http://vehicleservice:8181/api/cars/updateStatus/" +reservation.getCarId() + "/Rented";
        restTemplate.put(vehicleServiceUrl, null);
        payment.setPaymentStatus("Successful");
        
        Payment savedPayment = paymentRepository.save(payment);
        log.debug("Payment saved successfully: {}", savedPayment);
        return mapToDTO(savedPayment);
	}

	@Override
	public PaymentDTO getPaymentById(Long paymentId) throws PaymentNotFoundException {
		log.info("Fetching payment with paymentId={}", paymentId);
		Payment payment = paymentRepository.findById(paymentId).orElse(null);
        if (payment != null) {
            return mapToDTO(payment);
        }
        log.warn("Payment with paymentId={} not found",paymentId);
        throw new PaymentNotFoundException();
	}

	@Override
	public List<PaymentDTO> getAllPayments() {
		log.info("Fetching all payments");
        List<Payment> payments = paymentRepository.findAll();
        log.debug("Total payments fetched: {}", payments.size());
        
        List<PaymentDTO>paymentDTOs=new ArrayList<>();
        for(Payment payment:payments)
        {
        	paymentDTOs.add(mapToDTO(payment));
        }
        return paymentDTOs;
	}

	@Override
	public List<PaymentDTO> getPaymentsByReservationId(Long reservationId) {
		log.info("Fetching payments for reservationId={}", reservationId);
        List<Payment> payments = paymentRepository.findByReservationId(reservationId);
        log.debug("Found {} payments for reservationId={}", payments.size(), reservationId);
        List<PaymentDTO>paymentDTOs=new ArrayList<>();
        for(Payment payment:payments)
        {
        	paymentDTOs.add(mapToDTO(payment));
        }
        return paymentDTOs;
	}

	@Override
	public List<PaymentDTO> getPaymentsByCustomerId(Long customerId) {
		log.info("Fetching payments for customerId={}", customerId);
		List<Reservation> reservations = reservationRepository.findByCustomerId(customerId);
	    List<PaymentDTO> customerPayments = new ArrayList<>();

	    for (Reservation reservation : reservations) {
	        List<PaymentDTO> payments = getPaymentsByReservationId(reservation.getReservationId());
	        customerPayments.addAll(payments);
	    }
	    log.debug("Found {} payments for customerId={}", customerPayments.size(), customerId);
	    return customerPayments;
	}
	
	private PaymentDTO mapToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setPaymentType(payment.getPaymentType());
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setReservationId(payment.getReservationId());
        dto.setPaymentStatus(payment.getPaymentStatus());
        return dto;
    }

	@Override
	public PaymentDTO updatePaymentStatus(Long paymentId,String status) {
		
		Payment payment=paymentRepository.findById(paymentId).orElse(null);
		payment.setPaymentStatus(status);
		return mapToDTO(paymentRepository.save(payment));
	}

}

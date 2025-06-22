package com.hexaware.rentalservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.rentalservice.repository.PaymentRepository;
import com.hexaware.rentalservice.repository.ReservationRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Date: 02-06-2025
 * Author: Tharun D
 * Handles all business logic related to reports such as 
 * generating total revenue and total reservations.
 */

@Service
@Slf4j
public class ReportServiceImp implements IReportService{

	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Override
	public double getTotalRevenue() {
		log.info("Generating total revenue");
        Double revenue = paymentRepository.findTotalRevenue();
        log.debug("Total Revenue: {}", revenue);
        return revenue;
	}

	@Override
	public long getTotalReservations() {
		log.info("Counting total reservations");
        long count = reservationRepository.count();
        log.debug("Total reservations counted: {}", count);
        return count;
	}

}

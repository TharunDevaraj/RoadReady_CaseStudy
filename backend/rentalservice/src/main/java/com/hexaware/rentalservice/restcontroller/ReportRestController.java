package com.hexaware.rentalservice.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.rentalservice.service.IReportService;

/**
 * Date: 02-06-2025
 * Author: Tharun D
 * REST controller for managing Admin operations such as
 * generating total revenue, count total reservations.
 */

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/report")
public class ReportRestController {

	@Autowired
    private IReportService reportService;

    @GetMapping("/total-revenue")
    public ResponseEntity<Double> getTotalRevenue() {
        return new ResponseEntity<Double>( reportService.getTotalRevenue(),HttpStatus.OK);
    }
    
    @GetMapping("/total-reservations")
    public ResponseEntity<Long> getTotalReservations() {
    	return new ResponseEntity<Long>( reportService.getTotalReservations(),HttpStatus.OK);
    }
}

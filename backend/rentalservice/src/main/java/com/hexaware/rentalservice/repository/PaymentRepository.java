package com.hexaware.rentalservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.rentalservice.entity.Payment;

/**
 * Repository for performing database operations related to Payment entities.
 */

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

	public List<Payment> findByReservationId(Long reservationId);
	
	@Query("SELECT SUM(p.amount) FROM Payment p")
    public Double findTotalRevenue();
}

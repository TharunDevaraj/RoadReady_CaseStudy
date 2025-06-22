package com.hexaware.rentalservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.rentalservice.entity.Feedback;

/**
 * Repository for performing database operations related to Feedback entities.
 */

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>{
	public List<Feedback> findByCustomerId(Long customerId);
}

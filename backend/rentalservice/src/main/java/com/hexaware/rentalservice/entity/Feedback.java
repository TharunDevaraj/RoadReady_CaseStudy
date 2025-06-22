package com.hexaware.rentalservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entity representing feedback information in the system.
 * Includes fields like rating, comment, and status.
 */

@Entity
public class Feedback {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long feedbackId;

	 private Long reservationId;
	 private Long customerId;
	 private String comment;
	 private int rating; 
	 private String adminResponse;
	 private String status; 

	 public Feedback() {
		 
	 }

	public Feedback(Long feedbackId, Long reservationId, Long customerId, String comment, int rating, String status) {
		super();
		this.feedbackId = feedbackId;
		this.reservationId = reservationId;
		this.customerId = customerId;
		this.comment = comment;
		this.rating = rating;
		this.status = status;
	}

	public Long getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

	public String getAdminResponse() {
		return adminResponse;
	}

	public void setAdminResponse(String adminResponse) {
		this.adminResponse = adminResponse;
	}

	@Override
	public String toString() {
		return "Feedback [feedbackId=" + feedbackId + ", reservationId=" + reservationId + ", customerId=" + customerId
				+ ", comment=" + comment + ", rating=" + rating + ", status=" + status + "]";
	}

}

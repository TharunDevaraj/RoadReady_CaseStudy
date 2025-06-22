package com.hexaware.userservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for capturing vehicle reservation request details such as feedback information.
 */


public class FeedbackDTO {
	
	private Long feedbackId;

	@NotNull(message = "Reservation ID is required")
	 private Long reservationId;
	 
	 @NotNull(message = "Customer ID is required")
	 private Long customerId;
	 
	 @NotBlank(message = "Comment cannot be blank")
	 @Size(max = 500, message = "Comment must not exceed 500 characters")
	 private String comment;
	 
	 @Min(value = 1, message = "Rating must be at least 1")
	 @Max(value = 5, message = "Rating must be at most 5")
	 private int rating;
	 
	 private String adminResponse;
	 private String status; 
	 
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
	public String getAdminResponse() {
		return adminResponse;
	}
	public void setAdminResponse(String adminResponse) {
		this.adminResponse = adminResponse;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(Long feedbackId) {
		this.feedbackId = feedbackId;
	} 
	 
	 
	 
}

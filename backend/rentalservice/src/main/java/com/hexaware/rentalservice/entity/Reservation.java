package com.hexaware.rentalservice.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entity representing reservation information in the system.
 * Includes fields like startDate, endDate, and reservationStatus.
 */

@Entity
public class Reservation {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long reservationId;
	 private LocalDate startDate;
	 private LocalDate endDate;
	 private String reservationStatus;
	 private LocalDateTime checkInTime;
	 private LocalDateTime checkOutTime;
	 
	 public LocalDateTime getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(LocalDateTime checkInTime) {
		this.checkInTime = checkInTime;
	}

	public LocalDateTime getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(LocalDateTime checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	private Long customerId;  
	 private Long carId;
	 
	 public Reservation()
	 {
		 
	 }

	public Reservation(Long reservationId, LocalDate startDate, LocalDate endDate, String reservationStatus,
			Long customerId, Long carId) {
		super();
		this.reservationId = reservationId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reservationStatus = reservationStatus;
		this.customerId = customerId;
		this.carId = carId;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	@Override
	public String toString() {
		return "Reservation [reservationId=" + reservationId + ", reservationStatus=" + reservationStatus
				+ ", customerId=" + customerId + ", carId=" + carId + "]";
	}
	 
	 

}

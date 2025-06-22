package com.hexaware.vehicleservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Entity representing car information in the system.
 * Includes fields like carModel, year, and pricePerDay.
 */

@Entity
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long carId;
    private String carName;
    private int year;
    private String make;
    private String carStatus;
    private String location;
    private int passengerCapacity;
    private double pricePerDay;
    private String carImageUrl;

    
    public Car()
    {
    	
    }


	public Car(Long carId, String carName, int year, String make, String carStatus, String location,
			int passengerCapacity, double pricePerDay, String carImageUrl) {
		super();
		this.carId = carId;
		this.carName = carName;
		this.year = year;
		this.make = make;
		this.carStatus = carStatus;
		this.location = location;
		this.passengerCapacity = passengerCapacity;
		this.pricePerDay = pricePerDay;
		this.carImageUrl = carImageUrl;
	}


	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getPassengerCapacity() {
		return passengerCapacity;
	}

	public void setPassengerCapacity(int passengerCapacity) {
		this.passengerCapacity = passengerCapacity;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}


	public String getCarImageUrl() {
		return carImageUrl;
	}


	public void setCarImageUrl(String carImageUrl) {
		this.carImageUrl = carImageUrl;
	}


	@Override
	public String toString() {
		return "Car [carId=" + carId + ", carName=" + carName + ", year=" + year + ", make=" + make + ", carStatus="
				+ carStatus + ", location=" + location + ", passengerCapacity=" + passengerCapacity + ", pricePerDay="
				+ pricePerDay + ", carImageUrl=" + carImageUrl + "]";
	}

    
}

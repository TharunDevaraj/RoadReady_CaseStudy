package com.hexaware.vehicleservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hexaware.vehicleservice.entity.Car;

/**
 * Repository for performing database operations related to Car entities.
 */

public interface CarRepository extends JpaRepository<Car, Long>{

	 public List<Car> findByCarStatus(String status);
	 public List<Car> findByLocationAndPassengerCapacityGreaterThanEqualAndCarStatus( String location, int passengerCapacity, String status);
	 
	 @Query("SELECT c FROM Car c WHERE c.location = ?1 AND c.passengerCapacity >= ?2 AND c.id NOT IN ?3 AND c.carStatus = 'available'")
	 public List<Car> findAvailableCars(String location, int passengerCapacity, List<Long> excludedIds);

}
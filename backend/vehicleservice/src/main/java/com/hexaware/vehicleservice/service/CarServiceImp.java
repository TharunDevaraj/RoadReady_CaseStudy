package com.hexaware.vehicleservice.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hexaware.vehicleservice.dto.CarDTO;
import com.hexaware.vehicleservice.entity.Car;
import com.hexaware.vehicleservice.exception.CarNotFoundException;
import com.hexaware.vehicleservice.repository.CarRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Date: 02-06-2025
 * Author: Tharun D
 * Handles all business logic related to cars such as adding cars,
 * updating car details, and removing cars.
 */

@Service
@Slf4j
public class CarServiceImp implements ICarService{

	
	@Autowired
	CarRepository carRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public CarDTO addCar(CarDTO carDTO) {
		log.info("Adding a new car: {}", carDTO);
		
		Car car=new Car();
		car.setCarName(carDTO.getCarName());
		car.setMake(carDTO.getMake());
		car.setYear(carDTO.getYear());
		car.setCarStatus(carDTO.getCarStatus());
		car.setLocation(carDTO.getLocation());
		car.setPassengerCapacity(carDTO.getPassengerCapacity());
		car.setPricePerDay(carDTO.getPricePerDay());
		car.setCarImageUrl(carDTO.getCarImageUrl());
		Car savedCar = carRepository.save(car);
        log.debug("Car saved successfully: {}", savedCar);
        return mapToDTO(savedCar);
	}

	@Override
	public CarDTO getCarById(Long carId) throws CarNotFoundException {
		log.info("Fetching car with id={} ", carId);
		Car car=carRepository.findById(carId).orElse(null);
		
		if(car==null)
		{
			log.warn("Car with id={} not found", carId);
			throw new CarNotFoundException();
		}
		log.debug("Car found: {}", car);
		 return mapToDTO(car);
		
	}

	@Override
	public List<CarDTO> getAllCars() {
		
		List<Car> cars = carRepository.findAll();
		
		List<CarDTO> carDTOs=new ArrayList<>();
		
		for(Car car:cars)
		{
			carDTOs.add(mapToDTO(car));
		}
		return carDTOs;
	}

	@Override
	public CarDTO updateCar(Long carId,CarDTO updatedCarDTO) throws CarNotFoundException {
	
		log.info("Updating car: {}", updatedCarDTO);
		Car existingCar = carRepository.findById(carId).orElse(null);
        if (existingCar==null){
        	log.warn("Attempted to update non-existing car with id={} ", carId);
        	throw new CarNotFoundException();
        }
        
        existingCar.setCarName(updatedCarDTO.getCarName());
        existingCar.setYear(updatedCarDTO.getYear());
        existingCar.setMake(updatedCarDTO.getMake());
        existingCar.setCarStatus(updatedCarDTO.getCarStatus());
        existingCar.setLocation(updatedCarDTO.getLocation());
        existingCar.setPassengerCapacity(updatedCarDTO.getPassengerCapacity());
        existingCar.setPricePerDay(updatedCarDTO.getPricePerDay());
        existingCar.setCarImageUrl(updatedCarDTO.getCarImageUrl());
        log.debug("Car updated successfully: {}", existingCar);
        return mapToDTO(carRepository.save(existingCar));
	}

	@Override
	public void deleteCarById(Long carId) throws CarNotFoundException {
		log.info("Removing car with id={} ", carId);
		Car car=carRepository.findById(carId).orElse(null);
		
		if(car==null)
		{
			log.warn("Attempted to remove non-existing car with id={} ", carId);
			throw new CarNotFoundException();
		}
		log.debug("Car with id={} successfully removed", carId);
		carRepository.deleteById(carId);
	}

	@Override
	public List<CarDTO> getAvailableCars() {
		log.info("Fetching all available cars");
		List<Car> cars= carRepository.findByCarStatus("Available");
		log.debug("Found {} available cars",cars.size());
		List<CarDTO> carDTOs=new ArrayList<>();
		
		for(Car car:cars)
		{
			carDTOs.add(mapToDTO(car));
		}
		return carDTOs;
	}
	
	@Override
	public List<CarDTO> findAvailableCarsByFilter(String location, int passengerCapacity, LocalDate startDate, LocalDate endDate) {
	    List<Long> bookedCarIds = restTemplate.getForObject("http://rentalservice:8282/api/reservation/getbookedcars?startDate="+startDate+"&endDate="+endDate, List.class);
	    List<Car> cars=  carRepository.findAvailableCars(location, passengerCapacity, bookedCarIds);
	    
	    List<CarDTO> carDTOs=new ArrayList<>();
		
		for(Car car:cars)
		{
			carDTOs.add(mapToDTO(car));
		}
		return carDTOs;
	}

	@Override
	public List<CarDTO> searchVehicles(String location, int passengerCapacity) {
		
		List<Car> cars=carRepository.findByLocationAndPassengerCapacityGreaterThanEqualAndCarStatus(location, passengerCapacity, "available");
		
		List<CarDTO> carDTOs=new ArrayList<>();
		
		for(Car car:cars)
		{
			carDTOs.add(mapToDTO(car));
		}
		return carDTOs;
	}

	@Override
	public boolean isCarAvailable(Long carId) {
		
		Car car = carRepository.findById(carId).orElse(null);
        if (car != null && "available".equalsIgnoreCase(car.getCarStatus())) {
            return true;
        }
        return false;
	}
	
	@Override
	public CarDTO updateCarPricing(Long carId, double newPricePerDay) throws CarNotFoundException {
		log.info("Updating price of car with cardId {}",carId);
	    Car car = carRepository.findById(carId).orElse(null);
	    
	    if(car==null)
	    {
	    	log.warn("Attempted to update non-existing car with id={} ", carId);
			throw new CarNotFoundException();
	    }
	    log.debug("Updated price of car with cardId {}",carId);          
	    car.setPricePerDay(newPricePerDay);
	    return mapToDTO(carRepository.save(car));
	}
	
	private CarDTO mapToDTO(Car car) {
        CarDTO dto = new CarDTO();
        dto.setCarId(car.getCarId());
        dto.setCarName(car.getCarName());
        dto.setYear(car.getYear());
        dto.setMake(car.getMake());
        dto.setCarStatus(car.getCarStatus());
        dto.setLocation(car.getLocation());
        dto.setPassengerCapacity(car.getPassengerCapacity());
        dto.setPricePerDay(car.getPricePerDay());
        dto.setCarImageUrl(car.getCarImageUrl());
        return dto;
    }

	@Override
	public CarDTO updateVehicleStatus(Long carId, String newStatus) throws CarNotFoundException {
		log.info("Updating status of car with cardId {}",carId);
		Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException());

        car.setCarStatus(newStatus);
        log.debug("Updated status of car with cardId {}",carId);
        return mapToDTO(carRepository.save(car));
	}

}

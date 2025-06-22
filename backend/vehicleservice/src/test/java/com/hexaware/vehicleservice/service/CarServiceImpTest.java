package com.hexaware.vehicleservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.hexaware.vehicleservice.dto.CarDTO;
import com.hexaware.vehicleservice.entity.Car;
import com.hexaware.vehicleservice.exception.CarNotFoundException;

@SpringBootTest
class CarServiceImpTest {

	@Autowired
	CarServiceImp carService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	@Disabled
	void testAddCar() {
		CarDTO sampleCarDTO = new CarDTO();
        sampleCarDTO.setCarName("Toyota Corolla");
        sampleCarDTO.setYear(2020);
        sampleCarDTO.setMake("Toyota");
        sampleCarDTO.setCarStatus("available");
        sampleCarDTO.setLocation("New York");
        sampleCarDTO.setPassengerCapacity(5);
        sampleCarDTO.setPricePerDay(49.99);
        
        CarDTO result = carService.addCar(sampleCarDTO);
        assertNotNull(result);
        assertEquals("Toyota Corolla", result.getCarName());
	}

	@Test
	@Disabled
	void testGetCarById() throws CarNotFoundException {
		CarDTO carDTO = carService.getCarById(202L);
        assertEquals("Toyota Corolla", carDTO.getCarName());
		
        assertThrows(CarNotFoundException.class, () -> carService.getCarById(10L));
	}

	@Test
	@Disabled
	void testGetAllCars() {
		
		 List<CarDTO> cars = carService.getAllCars();
	        assertEquals(6, cars.size());
	}

	@Test
	@Disabled
	void testUpdateCar() throws CarNotFoundException {
		
		CarDTO sampleCarDTO=new CarDTO();
		sampleCarDTO.setLocation("Hyderabad");
		
		CarDTO updatedCar = carService.updateCar(202L, sampleCarDTO);
        assertEquals("Hyderabad", updatedCar.getLocation());
        
        assertThrows(CarNotFoundException.class, () -> carService.updateCar(99L, sampleCarDTO));
	}

	@Test
	@Disabled
	void testDeleteCarById() throws CarNotFoundException {
		
		carService.deleteCarById(302L);
		
		assertThrows(CarNotFoundException.class, () -> carService.getCarById(302L));
		
	}

	@Test
	@Disabled
	void testGetAvailableCars() {
		
		List<CarDTO> availableCars = carService.getAvailableCars();
        assertEquals(4, availableCars.size());
		
	}

	@Test
	@Disabled
	void testFindAvailableCarsByFilter() {
		
		List<CarDTO> result = carService.findAvailableCarsByFilter("New York", 5, LocalDate.now(), LocalDate.now().plusDays(2));
        assertEquals(1, result.size());
		
	}

	@Test
	@Disabled
	void testSearchVehicles() {
		
		List<CarDTO> result = carService.searchVehicles("New York", 5);
        assertEquals(1, result.size());
		
	}

	@Test
	@Disabled
	void testIsCarAvailable() {
		
		assertTrue(carService.isCarAvailable(352L));
		
		assertFalse(carService.isCarAvailable(152L));
		
	}

	@Test
	@Disabled
	void testUpdateCarPricing() throws CarNotFoundException {
		
		CarDTO updatedCar = carService.updateCarPricing(352L, 59.99);
        assertEquals(59.99, updatedCar.getPricePerDay());
        
        assertThrows(CarNotFoundException.class, () -> carService.updateCarPricing(400L, 59.99));
		
	}

	@Test
	@Disabled
	void testUpdateVehicleStatus() throws CarNotFoundException {
		CarDTO updated = carService.updateVehicleStatus(352L, "rented");
        assertEquals("rented", updated.getCarStatus());
        
        assertThrows(CarNotFoundException.class, () -> carService.updateVehicleStatus(400L, "maintenance"));
	}

}

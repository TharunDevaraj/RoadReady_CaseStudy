package com.hexaware.userservice.restcontroller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hexaware.userservice.dto.CarDTO;
import com.hexaware.userservice.dto.ChangePasswordDTO;
import com.hexaware.userservice.dto.FeedbackDTO;
import com.hexaware.userservice.dto.PaymentDTO;
import com.hexaware.userservice.dto.ReservationDTO;
import com.hexaware.userservice.dto.UserInfoDTO;
import com.hexaware.userservice.exception.UserNotFoundException;
import com.hexaware.userservice.service.IUserInfoService;
import com.hexaware.userservice.service.JwtService;

/**
 * Date: 02-06-2025
 * Author: Tharun D
 * REST controller for managing User operations such as USER registration,
 * login, and user deactivation.
 */

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserInfoRestController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	IUserInfoService userService;
	
	@Autowired
    private RestTemplate restTemplate;
	
		// User-Based Operations
	
	 	@GetMapping("/getAllUsers")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<List<UserInfoDTO>> getAllUsers() {
	        return new ResponseEntity<List<UserInfoDTO>>(userService.getAllUsers(), HttpStatus.OK);
	    }

	    @GetMapping("/getUser/{id}")
	    @PreAuthorize("hasAnyAuthority('admin','customer','agent')")
	    public ResponseEntity<UserInfoDTO> getUserById(@PathVariable Long id) throws UserNotFoundException {
	        return  new ResponseEntity<UserInfoDTO>(userService.getUserById(id), HttpStatus.OK);
	    }

	    @PutMapping("/updateUser/{id}")
	    @PreAuthorize("hasAnyAuthority('admin','customer','agent')")
	    public ResponseEntity<UserInfoDTO> updateUser(@PathVariable Long id, @RequestBody UserInfoDTO user) throws UserNotFoundException {
	        return new ResponseEntity<UserInfoDTO>(userService.updateUser(id, user), HttpStatus.OK);
	    }
	    
	    @PutMapping("/updatePassword")
	    @PreAuthorize("hasAnyAuthority('admin','customer','agent')")
	    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) throws UserNotFoundException
	    {
	    	return new ResponseEntity<String>(userService.updatePassword(changePasswordDTO.getUserId(), changePasswordDTO.getNewPassword()), HttpStatus.OK);
	    }
	    

	    @DeleteMapping("/deactivateUser/{id}")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<String> deactivateUser(@PathVariable Long id) throws UserNotFoundException {
	        userService.deactivateUser(id);
	        return new ResponseEntity<String>("User deactivated successfully",HttpStatus.OK);
	    }
	   
        // Car-Based Operations
	    
	    private final String CAR_SERVICE_BASE_URL = "http://vehicleservice:8181/api/cars";

	    @GetMapping("/getCar/{carId}")
	    @PreAuthorize("hasAnyAuthority('admin','customer','agent','user')")
	    public ResponseEntity<CarDTO> getCarById(@PathVariable Long carId) {
	        return new ResponseEntity<CarDTO>(restTemplate.getForObject(CAR_SERVICE_BASE_URL + "/get/" + carId, CarDTO.class), HttpStatus.OK);
	    }
	    
	    @PostMapping("/addCar")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<CarDTO> addCar(@RequestBody CarDTO carDTO) {
	        CarDTO createdCar = restTemplate.postForObject(CAR_SERVICE_BASE_URL + "/add", carDTO, CarDTO.class);
	        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
	    }

	    @GetMapping("/getAllCars")
	    @PreAuthorize("hasAnyAuthority('customer','admin','agent')")
	    public ResponseEntity< List<CarDTO>> getAllCars() {
	        CarDTO dtos[]= restTemplate.getForObject(CAR_SERVICE_BASE_URL + "/get", CarDTO[] .class);
	        List<CarDTO> cars = Arrays.asList(dtos);
	        return new ResponseEntity<>(cars, HttpStatus.OK);   
	    }
	    
	    @PutMapping("/updateCar/{carId}")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<String> updateCar(@PathVariable Long carId, @RequestBody CarDTO carDTO) {
	        restTemplate.put(CAR_SERVICE_BASE_URL + "/update/" + carId, carDTO);
	        return new ResponseEntity<>("Car updated successfully", HttpStatus.OK);
	    }

	    @GetMapping("/getAvailableCars")
	    @PreAuthorize("hasAnyAuthority('admin','customer','agent','user')")
	    public ResponseEntity<List<CarDTO>> getAvailableCars() {
	        CarDTO[] cars = restTemplate.getForObject(CAR_SERVICE_BASE_URL + "/available", CarDTO[].class);
	        return new ResponseEntity<>(Arrays.asList(cars), HttpStatus.OK);
	    }
	    
	    @DeleteMapping("/deleteCar/{carId}")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<String> deleteCar(@PathVariable Long carId) {
	        restTemplate.delete(CAR_SERVICE_BASE_URL + "/delete/" + carId);
	        return new ResponseEntity<>("Car deleted successfully", HttpStatus.OK);
	    }
	    
	    @PutMapping("/updateCarPrice/{carId}/{price}")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<String> updateCarPrice(@PathVariable Long carId, @PathVariable double price) {
	        restTemplate.put(CAR_SERVICE_BASE_URL + "/updateprice/" + carId + "/" + price, null);
	        return new ResponseEntity<>("Car price updated successfully", HttpStatus.OK);
	    }
	    
	    @GetMapping("/getCarsByFilter")
	    @PreAuthorize("hasAnyAuthority('admin','customer','agent','user')")
	    public ResponseEntity<List<CarDTO>> getAvailableCarsByFilter(
	            @RequestParam String location,
	            @RequestParam int passengerCapacity,
	            @RequestParam String startDate,  // Format: yyyy-MM-dd
	            @RequestParam String endDate     // Format: yyyy-MM-dd
	    ) {
	        String url = String.format("%s/availablecarsbyfilter?location=%s&passengerCapacity=%d&startDate=%s&endDate=%s",
	                CAR_SERVICE_BASE_URL, location, passengerCapacity, startDate, endDate);
	        CarDTO[] cars = restTemplate.getForObject(url, CarDTO[].class);
	        return new ResponseEntity<>(Arrays.asList(cars), HttpStatus.OK);
	    }
	    
	    @PutMapping("/updateCarStatus/{carId}/{status}")
	    @PreAuthorize("hasAnyAuthority('admin','agent')")
	    public ResponseEntity<String> updateVehicleStatus(@PathVariable Long carId, @PathVariable String status) {
	        restTemplate.put(CAR_SERVICE_BASE_URL + "/updateStatus/" + carId + "/" + status, null);
	        return new ResponseEntity<>("Vehicle status updated successfully", HttpStatus.OK);
	    }
	    
	    
	    // Reservation-Based Operations
	    
	    private final String RESERVATION_SERVICE_BASE_URL = "http://rentalservice:8282/api/reservation";
	    
	    @PostMapping("/makeReservation")
	    @PreAuthorize("hasAnyAuthority('customer')")
	    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
	        ReservationDTO created = restTemplate.postForObject(
	                RESERVATION_SERVICE_BASE_URL + "/create",
	                reservationDTO,
	                ReservationDTO.class
	        );
	        return new ResponseEntity<>(created, HttpStatus.CREATED);
	    }
	    
	    @PutMapping("/updateReservation")
	    @PreAuthorize("hasAnyAuthority('admin','customer','agent','user')")
	    public ResponseEntity<String> updateReservation(@RequestBody ReservationDTO reservationDTO) {
	        restTemplate.put(RESERVATION_SERVICE_BASE_URL + "/update", reservationDTO);
	        return new ResponseEntity<>("Reservation updated successfully", HttpStatus.OK);
	    }
	    
	    @DeleteMapping("/cancelReservation/{reservationId}")
	    @PreAuthorize("hasAnyAuthority('admin','customer','agent','user')")
	    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId) {
	        restTemplate.delete(RESERVATION_SERVICE_BASE_URL + "/cancel/" + reservationId);
	        return new ResponseEntity<>("Reservation cancelled successfully", HttpStatus.OK);
	    }
	    
	    @GetMapping("/getReservation/{reservationId}")
	    @PreAuthorize("hasAnyAuthority('admin','customer','agent','user')")
	    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long reservationId) {
	        ReservationDTO dto = restTemplate.getForObject(
	                RESERVATION_SERVICE_BASE_URL + "/get/" + reservationId,
	                ReservationDTO.class
	        );
	        return new ResponseEntity<>(dto, HttpStatus.OK);
	    }
	    
	    @GetMapping("/getReservationByCustomer/{customerId}")
	    @PreAuthorize("hasAnyAuthority('customer','admin')")
	    public ResponseEntity<List<ReservationDTO>> getReservationsByCustomerId(@PathVariable Long customerId) {
	        ReservationDTO[] reservations = restTemplate.getForObject(
	                RESERVATION_SERVICE_BASE_URL + "/getbycustomer/" + customerId,
	                ReservationDTO[].class
	        );
	        return new ResponseEntity<>(Arrays.asList(reservations), HttpStatus.OK);
	    }
	    
	    @GetMapping("/getReservationByCar/{carId}")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<List<ReservationDTO>> getReservationsByCarId(@PathVariable Long carId) {
	        ReservationDTO[] reservations = restTemplate.getForObject(
	                RESERVATION_SERVICE_BASE_URL + "/getbycar/" + carId,
	                ReservationDTO[].class
	        );
	        return new ResponseEntity<>(Arrays.asList(reservations), HttpStatus.OK);
	    }
	    
	    @GetMapping("/getAllReservations")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
	        ReservationDTO[] reservations = restTemplate.getForObject(
	                RESERVATION_SERVICE_BASE_URL + "/get",
	                ReservationDTO[].class
	        );
	        return new ResponseEntity<>(Arrays.asList(reservations), HttpStatus.OK);
	    }
	    
	    @GetMapping("/getBookedCars")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<List<Long>> getBookedCars(
	            @RequestParam String startDate,  // ISO format: yyyy-MM-dd
	            @RequestParam String endDate
	    ) {
	        String url = String.format("%s/getbookedcars?startDate=%s&endDate=%s",
	                RESERVATION_SERVICE_BASE_URL, startDate, endDate);
	        Long[] carIds = restTemplate.getForObject(url, Long[].class);
	        return new ResponseEntity<>(Arrays.asList(carIds), HttpStatus.OK);
	    }

	    @PutMapping("/checkin/{id}")
	    @PreAuthorize("hasAnyAuthority('admin','agent')")
	    public ResponseEntity<String> checkIn(@PathVariable Long id) {
	        restTemplate.put(RESERVATION_SERVICE_BASE_URL + "/checkin/" + id, null);
	        return new ResponseEntity<>("Check-in completed successfully", HttpStatus.OK);
	    }

	    @PutMapping("/checkout/{id}")
	    @PreAuthorize("hasAnyAuthority('admin','agent')")
	    public ResponseEntity<String> checkOut(@PathVariable Long id) {
	        restTemplate.put(RESERVATION_SERVICE_BASE_URL + "/checkout/" + id, null);
	        return new ResponseEntity<>("Check-out completed successfully", HttpStatus.OK);
	    }
	    
	    // Report-Related Operations
	    
	    private final String REPORT_SERVICE_BASE_URL = "http://rentalservice:8282/api/report";
	    
	    @GetMapping("/total-revenue")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<Double> getTotalRevenue() {
	        Double revenue = restTemplate.getForObject(REPORT_SERVICE_BASE_URL + "/total-revenue", Double.class);
	        return new ResponseEntity<>(revenue, HttpStatus.OK);
	    }

	    @GetMapping("/total-reservations")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<Double> getTotalReservations() {
	        Double count = restTemplate.getForObject(REPORT_SERVICE_BASE_URL + "/total-reservations", Double.class);
	        return new ResponseEntity<>(count, HttpStatus.OK);
	    }
	        
	     // Payment-Related Operations 
	        
	        private final String PAYMENT_SERVICE_BASE_URL = "http://rentalservice:8282/api/payment";
	        
	        @PostMapping("/makePayment")
	        @PreAuthorize("hasAnyAuthority('customer')")
	        public ResponseEntity<PaymentDTO> makePayment(@RequestBody PaymentDTO paymentDTO) {
	            PaymentDTO response = restTemplate.postForObject(
	                PAYMENT_SERVICE_BASE_URL + "/make", paymentDTO, PaymentDTO.class);
	            return new ResponseEntity<>(response, HttpStatus.CREATED);
	        }

	        @GetMapping("/getPayment/{paymentId}")
	        @PreAuthorize("hasAnyAuthority('admin','customer')")
	        public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long paymentId) {
	            PaymentDTO response = restTemplate.getForObject(
	                PAYMENT_SERVICE_BASE_URL + "/get/" + paymentId, PaymentDTO.class);
	            return new ResponseEntity<>(response, HttpStatus.OK);
	        }

	        @GetMapping("/getAllPayments")
	        @PreAuthorize("hasAnyAuthority('admin')")
	        public ResponseEntity<List<PaymentDTO>> getAllPayments() {
	            PaymentDTO[] payments = restTemplate.getForObject(
	                PAYMENT_SERVICE_BASE_URL + "/get", PaymentDTO[].class);
	            return new ResponseEntity<>(Arrays.asList(payments), HttpStatus.OK);
	        }

	        @GetMapping("/getPaymentByReservation/{reservationId}")
	        @PreAuthorize("hasAnyAuthority('admin','customer')")
	        public ResponseEntity<List<PaymentDTO>> getPaymentsByReservationId(@PathVariable Long reservationId) {
	            PaymentDTO[] payments = restTemplate.getForObject(
	                PAYMENT_SERVICE_BASE_URL + "/reservation/" + reservationId, PaymentDTO[].class);
	            return new ResponseEntity<>(Arrays.asList(payments), HttpStatus.OK);
	        }

	        @GetMapping("/getPaymentByCustomer/{customerId}")
	        @PreAuthorize("hasAnyAuthority('admin','customer')")
	        public ResponseEntity<List<PaymentDTO>> getPaymentsByCustomerId(@PathVariable Long customerId) {
	            PaymentDTO[] payments = restTemplate.getForObject(
	                PAYMENT_SERVICE_BASE_URL + "/customer/" + customerId, PaymentDTO[].class);
	            return new ResponseEntity<>(Arrays.asList(payments), HttpStatus.OK);
	        }  
	        
	    // Feedback-Related Operations  
	        
	    private final String FEEDBACK_BASE_URL = "http://rentalservice:8282/api/feedback";  
	    
	    @PostMapping("/submitFeedback")
	    @PreAuthorize("hasAnyAuthority('customer')")
	    public ResponseEntity<FeedbackDTO> submitFeedback(@RequestBody FeedbackDTO feedbackDTO) {
	        FeedbackDTO submitted = restTemplate.postForObject(
	            FEEDBACK_BASE_URL + "/submit", feedbackDTO, FeedbackDTO.class);
	        return new ResponseEntity<>(submitted, HttpStatus.CREATED);
	    }

	    @GetMapping("/getAllFeedbacks")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<List<FeedbackDTO>> getAllFeedback() {
	        FeedbackDTO[] feedbacks = restTemplate.getForObject(
	            FEEDBACK_BASE_URL + "/get", FeedbackDTO[].class);
	        return new ResponseEntity<>(Arrays.asList(feedbacks), HttpStatus.OK);
	    }

	    @GetMapping("/getFeedback/{feedbackId}")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<FeedbackDTO> getFeedbackById(@PathVariable Long feedbackId) {
	        FeedbackDTO feedback = restTemplate.getForObject(
	            FEEDBACK_BASE_URL + "/get/" + feedbackId, FeedbackDTO.class);
	        return new ResponseEntity<>(feedback, HttpStatus.OK);
	    }

	    @GetMapping("/getFeedbackByCustomer/{customerId}")
	    @PreAuthorize("hasAnyAuthority('admin','customer')")
	    public ResponseEntity<List<FeedbackDTO>> getFeedbackByCustomerId(@PathVariable Long customerId) {
	        FeedbackDTO[] feedbacks = restTemplate.getForObject(
	            FEEDBACK_BASE_URL + "/getbycustomerid/" + customerId, FeedbackDTO[].class);
	        return new ResponseEntity<>(Arrays.asList(feedbacks), HttpStatus.OK);
	    }

	    @DeleteMapping("/removeFeedback/{feedbackId}")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<String> deleteFeedback(@PathVariable Long feedbackId) {
	        restTemplate.delete(FEEDBACK_BASE_URL + "/remove/" + feedbackId);
	        return new ResponseEntity<>("Feedback deleted successfully", HttpStatus.OK);
	    }

	    @PutMapping("/setFeedbackStatus/{id}")
	    @PreAuthorize("hasAnyAuthority('admin')")
	    public ResponseEntity<FeedbackDTO> setFeedbackStatus(
	            @PathVariable Long id,
	            @RequestParam String adminResponse) {

	        String url = FEEDBACK_BASE_URL + "/setstatus/" + id + "?adminResponse=" + adminResponse;
	        restTemplate.put(url, null);
	        FeedbackDTO updated = restTemplate.getForObject(FEEDBACK_BASE_URL + "/get/" + id, FeedbackDTO.class);
	        return new ResponseEntity<>(updated, HttpStatus.OK);
	    }
	    
	   
	    
}

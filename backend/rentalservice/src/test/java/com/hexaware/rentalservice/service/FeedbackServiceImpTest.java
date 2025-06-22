package com.hexaware.rentalservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.rentalservice.dto.FeedbackDTO;
import com.hexaware.rentalservice.entity.Feedback;
import com.hexaware.rentalservice.exception.FeedbackNotFoundException;

@SpringBootTest
class FeedbackServiceImpTest {

	@Autowired
	FeedbackServiceImp feedbackService;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	@Disabled
	void testSubmitFeedback() {
		
		FeedbackDTO dto = new FeedbackDTO();
        dto.setReservationId(101L);
        dto.setCustomerId(201L);
        dto.setComment("Excellent service");
        dto.setRating(5);
        
        FeedbackDTO result = feedbackService.submitFeedback(dto);

        assertNotNull(result);
        assertEquals(5, result.getRating());
		
	}

	@Test
	@Disabled
	void testGetFeedbackByCustomerId() {
		 List<FeedbackDTO> result = feedbackService.getFeedbackByCustomerId(201L);

	     assertEquals(1, result.size());
	     assertEquals(201L, result.get(0).getCustomerId());
	}

	@Test
	@Disabled
	void testGetAllFeedback() {
		 List<FeedbackDTO> result = feedbackService.getAllFeedback();

	        assertEquals(1, result.size());
	}

	@Test
	@Disabled
	void testDeleteFeedback() {
		
		feedbackService.deleteFeedback(2L);
		
		assertThrows(FeedbackNotFoundException.class, ()-> feedbackService.getFeedbackById(2L));
		
	}

	@Test
	@Disabled
	void testGetFeedbackById() throws FeedbackNotFoundException {
		FeedbackDTO result = feedbackService.getFeedbackById(52L);

        assertNotNull(result);
	}

	@Test
	@Disabled
	void testSetFeedbackStatus() throws FeedbackNotFoundException {
		
		FeedbackDTO updated = feedbackService.setFeedbackStatus(52L,"Thank you for submitting your feedback, will ensure you have smooth experience");

        assertEquals("Resolved", updated.getStatus());
		
	}

}

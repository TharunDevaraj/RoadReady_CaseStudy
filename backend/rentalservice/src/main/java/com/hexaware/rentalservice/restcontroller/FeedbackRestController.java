package com.hexaware.rentalservice.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.hexaware.rentalservice.dto.FeedbackDTO;
import com.hexaware.rentalservice.exception.FeedbackNotFoundException;
import com.hexaware.rentalservice.service.FeedbackServiceImp;

import jakarta.validation.Valid;

/**
 * Date: 02-06-2025
 * Author: Tharun D
 * REST controller for managing user operations such as submitting feedback,
 * resolving feedback.
 */
@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/feedback/")
public class FeedbackRestController {

	@Autowired
	FeedbackServiceImp feedbackService;
	
	@PostMapping("/submit")
	public FeedbackDTO submitFeedback(@RequestBody @Valid FeedbackDTO feedbackDTO) {
	    return feedbackService.submitFeedback(feedbackDTO);
	}
	
	@GetMapping("/get")
	public List<FeedbackDTO> getAll() {
	    return feedbackService.getAllFeedback();
	}
	
	@DeleteMapping("/remove/{feedbackId}")
	public String removeFeedback(@PathVariable Long feedbackId) throws FeedbackNotFoundException
	{
		FeedbackDTO feedback=feedbackService.getFeedbackById(feedbackId);
		if(feedback==null)
		{
			throw new FeedbackNotFoundException();
		}
		feedbackService.deleteFeedback(feedbackId);
		return "Feedback deleted!";
	}
	
	@GetMapping("/get/{feedbackId}")
	public FeedbackDTO getByFeedbackId(@PathVariable Long feedbackId) throws FeedbackNotFoundException {
		FeedbackDTO feedback=feedbackService.getFeedbackById(feedbackId);
		if(feedback==null)
		{
			throw new FeedbackNotFoundException();
		}
		return feedback;
	}
	
	@GetMapping("/getbycustomerid/{customerId}")
	public List<FeedbackDTO> getFeedbackByCustomerId(@PathVariable Long customerId) {
	    return feedbackService.getFeedbackByCustomerId(customerId);
	}
	
	@PutMapping("/setstatus/{id}")
	public FeedbackDTO setFeedbackStatus(@PathVariable Long id, @RequestParam String adminResponse) throws FeedbackNotFoundException
	{
		return feedbackService.setFeedbackStatus(id,adminResponse);
	}
	
}

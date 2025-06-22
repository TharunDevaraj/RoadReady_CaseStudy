package com.hexaware.rentalservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.rentalservice.dto.FeedbackDTO;
import com.hexaware.rentalservice.entity.Feedback;
import com.hexaware.rentalservice.exception.FeedbackNotFoundException;
import com.hexaware.rentalservice.repository.FeedbackRepository;

/**
 * Date: 02-06-2025
 * Author: Tharun D
 * Handles all business logic related to feedback such as 
 * submitting feedback , resolving feedback etc.
 */

@Service
public class FeedbackServiceImp implements IFeedbackService {

	@Autowired
	FeedbackRepository feedbackRepository;

	public FeedbackDTO submitFeedback(FeedbackDTO feedbackDTO) {

		Feedback feedback = new Feedback();
		feedback.setReservationId(feedbackDTO.getReservationId());
		feedback.setCustomerId(feedbackDTO.getCustomerId());
		feedback.setComment(feedbackDTO.getComment());
		feedback.setRating(feedbackDTO.getRating());
		feedback.setAdminResponse("No Response");
		feedback.setStatus("Pending");
		return mapToDTO( feedbackRepository.save(feedback));
	}

	public List<FeedbackDTO> getFeedbackByCustomerId(Long customerId) {
		List<Feedback> feedbacks= feedbackRepository.findByCustomerId(customerId);
		
		List<FeedbackDTO> feedbackDTOs=new ArrayList<>();
		
		for(Feedback feedback:feedbacks)
		{
			feedbackDTOs.add(mapToDTO(feedback));
		}
		
		return feedbackDTOs;
	}

	public List<FeedbackDTO> getAllFeedback() {
		List<Feedback> feedbacks= feedbackRepository.findAll();
		
List<FeedbackDTO> feedbackDTOs=new ArrayList<>();
		
		for(Feedback feedback:feedbacks)
		{
			feedbackDTOs.add(mapToDTO(feedback));
		}
		
		return feedbackDTOs;
	}

	public void deleteFeedback(Long feedbackId) {

		feedbackRepository.deleteById(feedbackId);
	}

	public FeedbackDTO getFeedbackById(Long id) throws FeedbackNotFoundException {
		Feedback feedback= feedbackRepository.findById(id).orElse(null);
		
		if(feedback==null)
		{
			throw new FeedbackNotFoundException();
		}
		return mapToDTO(feedback);
	}

	public FeedbackDTO setFeedbackStatus(Long id, String adminResponse) throws FeedbackNotFoundException {
		Feedback feedback = feedbackRepository.findById(id).orElse(null);
		if (feedback == null) {
			throw new FeedbackNotFoundException();
		}
		feedback.setStatus("Resolved");
		feedback.setAdminResponse(adminResponse);
		return mapToDTO(feedbackRepository.save(feedback));
	}
	
	private FeedbackDTO mapToDTO(Feedback feedback)
	{
		FeedbackDTO dto=new FeedbackDTO();
		
		dto.setFeedbackId(feedback.getFeedbackId());
		dto.setReservationId(feedback.getReservationId());
		dto.setCustomerId(feedback.getCustomerId());
		dto.setComment(feedback.getComment());
		dto.setRating(feedback.getRating());
		dto.setAdminResponse(feedback.getAdminResponse());
		dto.setStatus(feedback.getStatus());
		return dto;
	}

}

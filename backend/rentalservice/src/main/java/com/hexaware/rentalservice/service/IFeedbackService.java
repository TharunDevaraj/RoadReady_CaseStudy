package com.hexaware.rentalservice.service;

import java.util.List;

import com.hexaware.rentalservice.dto.FeedbackDTO;
import com.hexaware.rentalservice.entity.Feedback;
import com.hexaware.rentalservice.exception.FeedbackNotFoundException;

public interface IFeedbackService {
	
	public FeedbackDTO submitFeedback(FeedbackDTO feedbackDTO);
	
	public FeedbackDTO getFeedbackById(Long id) throws FeedbackNotFoundException;
	
    public List<FeedbackDTO> getAllFeedback();
    
    public List<FeedbackDTO> getFeedbackByCustomerId(Long customerId);
    
    public void deleteFeedback(Long feedbackId);
    
    public FeedbackDTO setFeedbackStatus(Long id,String adminResponse) throws FeedbackNotFoundException;
}

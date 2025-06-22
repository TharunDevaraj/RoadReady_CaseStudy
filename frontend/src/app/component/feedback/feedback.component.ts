import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RentalService } from 'src/app/service/RentalService/rental.service';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrls: ['./feedback.component.css']
})
export class FeedbackComponent implements OnInit {

  feedback = {
    reservationId: '',
    customerId: '',
    customerName: '',
    rating: 0,
    comments: ''
  };

  constructor(private router: Router,private rentalService:RentalService,private location:Location) {}


goBack(): void {
  this.location.back();
}

  ngOnInit(): void {
    const navData = history.state;
    this.feedback.reservationId = navData.reservationId;
    this.feedback.customerId = navData.customerId;
    this.feedback.customerName = navData.customerName;
  }

  submitFeedback(): void {
    const feedbackSubmission = {
      reservationId: this.feedback.reservationId,
      customerId: this.feedback.customerId,
      rating: this.feedback.rating,
      comment: this.feedback.comments
    };
    this.rentalService.submitFeedback(feedbackSubmission).subscribe(
       (response) => {
        alert('Feedback submitted successfully!');
        this.router.navigate(['/myFeedback']);
      },
      (err) => {
        console.error('Feedback submission failed', err);
        alert('Failed to submit feedback. Please try again later.');
      }
    );
  }
}

// src/app/component/my-feedback/my-feedback.component.ts
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/service/AuthService/auth.service';
import { FeedbackDTO } from 'src/app/dto/feedback';
import { RentalService } from 'src/app/service/RentalService/rental.service';

@Component({
  selector: 'app-my-feedback',
  templateUrl: './my-feedback.component.html',
  styleUrls: ['./my-feedback.component.css']
})
export class MyFeedbackComponent implements OnInit {
  feedbackList:any[] = [];

constructor(
  private feedbackService: RentalService,  // Has getFeedbacksByCustomer & getReservationById
  private authService: AuthService
) {}

ngOnInit(): void {
  const userId = this.authService.getUserIdFromToken();

  this.feedbackService.getFeedbacksByCustomer(userId).subscribe({
    next: (feedbacks) => {
      feedbacks.forEach(fb => {
        this.feedbackService.getReservationById(fb.reservationId).subscribe({
          next: (res: any) => {
            fb.carId = res.carId; 
            this.feedbackList.push(fb);
          },
          error: (err) => console.error('Error fetching reservation', err)
        });
      });
    },
    error: (err) => console.error('Error fetching feedbacks', err)
  });
}
}

import { Component, OnInit } from '@angular/core';
import { RentalService } from 'src/app/service/RentalService/rental.service';

@Component({
  selector: 'app-view-feedbacks',
  templateUrl: './view-feedbacks.component.html',
  styleUrls: ['./view-feedbacks.component.css']
})
export class ViewFeedbacksComponent implements OnInit {

  feedbackList: any[] = [];

  constructor(private rentalService: RentalService) {}

  ngOnInit(): void {
    this.rentalService.getAllFeedbacks().subscribe({
      next: (data) => {

        // Fetch car IDs
        data.forEach(fb => {
          this.rentalService.getReservationById(fb.reservationId).subscribe({
            next: res => {
              fb.carId = res.carId; 
              this.feedbackList.push(fb);
            },
            error: err => console.error('Error fetching reservation', err)
          });
        });
      },
      error: err => console.error('Error fetching feedbacks', err)
    });
  }

  submitAdminResponse(fb: any): void {
    if (!fb.tempResponse || fb.tempResponse.trim() === '') {
      alert('Please enter a response before submitting.');
      return;
    }

    
    const adminResponse= fb.tempResponse

    this.rentalService.respondToFeedback(fb.feedbackId, adminResponse).subscribe({
      next: (res:any) => {
        fb.adminResponse = fb.tempResponse;
        fb.status = res.status
        fb.tempResponse = '';
        alert('Response sent successfully.');
      },
      error: err => {
        console.error('Failed to send response', err);
        alert('Failed to send response. Try again.');
      }
    });
  }
}

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PaymentDTO } from 'src/app/dto/payment';
import { ReservationDTO } from 'src/app/dto/reservation';
import { AuthService } from 'src/app/service/AuthService/auth.service';
import { RentalService } from 'src/app/service/RentalService/rental.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent {

  upcomingReservations: ReservationDTO[] = [];   // status = "Rented"
  completedReservations: ReservationDTO[] = [];  // status = "Completed"
  cancelledReservations: ReservationDTO[] = []; // status = "Cancelled"
  paymentHistory: PaymentDTO[] = [];

  constructor(
    private rentalService: RentalService,
    private authService: AuthService,
    private router:Router
  ) {}

  ngOnInit(): void {

    this.loadReservationsAndPayments();
    
  }

  loadReservationsAndPayments() {
  const userId = this.authService.getUserIdFromToken();

  this.rentalService.getReservationsByCustomerId(userId).subscribe(reservations => {
    this.upcomingReservations = reservations.filter(r => r.reservationStatus === 'Reserved');
    this.completedReservations = reservations.filter(r => r.reservationStatus === 'Completed');
    this.cancelledReservations = reservations.filter(r => r.reservationStatus === 'Cancelled');
  });

  this.rentalService.getPaymentsByCustomerId(userId).subscribe(payments => {
      this.paymentHistory = payments;
  })
}


    goToChangeDate(reservation: any) {
      this.router.navigate(['/changeDate'], {
        queryParams: {
      id: reservation.reservationId,
      carId: reservation.carId,
      custId: reservation.customerId,
      start: reservation.startDate,
      end: reservation.endDate
    }
  });
}


  confirmCancellation(reservationId: number) {
  const confirmed = confirm("Are you sure you want to cancel this reservation?");
  if (confirmed) {
    this.rentalService.cancelReservation(reservationId).subscribe(() => {
      alert("Reservation cancelled successfully");
       this.loadReservationsAndPayments();
    });
  }
}




  navigateToFeedback(summaryReservationId:any): void {
  this.router.navigate(['/feedback'], {
    state: {
      reservationId: summaryReservationId,
      customerId: this.authService.getUserIdFromToken(),
      customerName: this.authService.getUsernameFromToken() 
    }
  });
}

}

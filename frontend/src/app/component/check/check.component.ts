import { Component } from '@angular/core';
import { RentalService } from 'src/app/service/RentalService/rental.service';
import { ReservationDTO } from 'src/app/dto/reservation';
import { UserService } from 'src/app/service/UserService/user.service';

@Component({
  selector: 'app-checkin',
  templateUrl: './check.component.html',
  styleUrls: ['./check.component.css']
})
export class CheckComponent {
  reservationId!: number;
  customerId!: number;
  verified = false;
  checkedIn = false;
  checkedOut=false;
  errorMessage = '';
  actionMessage = '';
  reservation!: ReservationDTO;

  constructor(private rentalService: RentalService,private userService:UserService) {}

  verifyReservation() {
  this.errorMessage = '';
  this.actionMessage = '';

  this.rentalService.getReservationById(this.reservationId).subscribe(
    (res) => {
      console.log("res details:",res)
      if (res.customerId === this.customerId) {
        this.reservation = res;
        this.checkedIn = res.checkInTime !== null;
        this.checkedOut = res.checkOutTime !== null;
        this.verified = true;

        // Fetch customer name separately
        this.userService.getUserById(this.customerId).subscribe((customer) => {
          this.reservation.customerName = customer.userName;
        });
      } else {
        this.errorMessage = 'Customer ID does not match the reservation.';
      }
    },
    () => {
      this.errorMessage = 'Invalid Reservation ID.';
    }
  );
}


  checkIn() {
    this.rentalService.checkIn(this.reservationId).subscribe(
      () => {
        this.checkedIn = true;
        this.actionMessage = 'Check-In successful!';
      },
       () => {
        this.actionMessage = 'Check-In failed.';
      }
    );
  }

  checkOut() {
    this.rentalService.checkOut(this.reservationId).subscribe(
       () => {
        this.checkedOut=true;
        this.actionMessage = 'Check-Out successful!';
      },
       () => {
        this.actionMessage = 'Check-Out failed.';
      }
    );
  }
}

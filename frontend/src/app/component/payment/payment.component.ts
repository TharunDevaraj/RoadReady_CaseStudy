import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PaymentDTO } from 'src/app/dto/payment';
import { ReservationDTO } from 'src/app/dto/reservation';
import { RentalService } from 'src/app/service/RentalService/rental.service';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit{
  paymentType = '';
  upiId = '';
  cardNumber = '';
  expiryDate = '';
  cvv = '';
  totalAmount = 0;

  reservationData!: ReservationDTO;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private reservationService: RentalService,
    private paymentService: RentalService
  ) {}

  ngOnInit(): void {
    const state = history.state;
    this.reservationData = state.reservationData;
    this.totalAmount = state.totalAmount;
  }

  makePayment(): void {
    this.reservationService.createReservation(this.reservationData).subscribe(res => {
       console.log("Reservation response:", res);
      
    const reservationId = res.reservationId;
    

    console.log("Reservation saved. ID:", reservationId); 
    const paymentDTO = {
      reservationId: reservationId,
      paymentType: this.paymentType,
      amount: this.totalAmount
    };

      console.log("Sending payment:", paymentDTO);

      this.paymentService.makePayment(paymentDTO).subscribe(payRes => {
        this.reservationService.getReservationById(reservationId).subscribe(res=>
        {
          this.reservationData=res;
          alert("Payment successful!");
        
        this.router.navigate(['/success'], { state: { reservation: this.reservationData, payment: payRes } });
      
        }
        )
        });
    });
  }

}

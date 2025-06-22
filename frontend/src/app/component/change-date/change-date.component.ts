import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RentalService } from 'src/app/service/RentalService/rental.service';

@Component({
  selector: 'app-change-date',
  templateUrl: './change-date.component.html',
  styleUrls: ['./change-date.component.css']
})
export class ChangeDateComponent implements OnInit {
  reservationId!: number;
  carId!: number;
  customerId!: number;

  startDate!: string;
  endDate!: string;

  minStartDate: string = '';
originalDayGap: number = 0;
newGap=0;


  constructor(
    private route: ActivatedRoute,
    private rentalService: RentalService,
    private router: Router,
    private location:Location
  ) {}

  goBack(): void {
  this.location.back();
}

  ngOnInit(): void {
  const today = new Date();
  const yyyy = today.getFullYear();
  const mm = String(today.getMonth() + 1).padStart(2, '0');
  const dd = String(today.getDate()).padStart(2, '0');
  this.minStartDate = `${yyyy}-${mm}-${dd}`;

    this.route.queryParams.subscribe(params => {
      this.reservationId = params['id'];
      this.carId = params['carId'];
      this.customerId = params['custId'];
      this.startDate = params['start'];
      this.endDate = params['end'];

      const start = new Date(this.startDate);
    const end = new Date(this.endDate);
    this.originalDayGap = Math.ceil((end.getTime() - start.getTime()) / (1000 * 3600 * 24));
    });
  }

  calculateDays()
  {
    const newStart = new Date(this.startDate);
  const newEnd = new Date(this.endDate);
  this.newGap = Math.ceil((newEnd.getTime() - newStart.getTime()) / (1000 * 3600 * 24));
  }

  submitChange(): void {
    

  if (this.newGap !== this.originalDayGap) {
    alert(`Please maintain the same rental duration of ${this.originalDayGap} day(s).`);
    return;
  }

  const updatedReservation = {
    reservationId: this.reservationId,
    carId: this.carId,
    customerId: this.customerId,
    startDate: this.startDate,
    endDate: this.endDate
  };

  this.rentalService.updateReservation(updatedReservation).subscribe(
    () => {
      alert('Reservation updated successfully');
      this.router.navigate(['/history']);
    },
    (err) => {
      console.error('Error updating reservation:', err);
      alert('Failed to update reservation. Please try again later.');
    }
  );
}

}
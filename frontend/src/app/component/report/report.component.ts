import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { RentalService } from 'src/app/service/RentalService/rental.service';
import { CarService } from 'src/app/service/CarService/car.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  totalRevenue: number = 0;
  totalReservations: number = 0;
  totalCars: number = 0;
  availableCars: number = 0;
  rentedCars: number = 0;
  maintenanceCars: number = 0;
  errorMessage: string = '';

  constructor(private http: HttpClient,private carService:CarService) {}

  ngOnInit(): void {
    this.fetchTotalRevenue();
    this.fetchTotalReservations();
    this.fetchCarStats();
  }

  fetchTotalRevenue() {
    const headers=new HttpHeaders().set('Authorization','Bearer '+localStorage.getItem('token'));
    this.http.get<number>('http://localhost:8080/api/users/total-revenue',{headers}).subscribe({
      next: data => this.totalRevenue = data,
      error: err => this.errorMessage = "Failed to fetch total revenue"
    });
  }

  fetchTotalReservations() {
    const headers=new HttpHeaders().set('Authorization','Bearer '+localStorage.getItem('token'));
    this.http.get<number>('http://localhost:8080/api/users/total-reservations',{headers}).subscribe({
      next: data => this.totalReservations = data,
      error: err => this.errorMessage = "Failed to fetch total reservations"
    });
  }

  fetchCarStats() {
    
    this.carService.getAllCars().subscribe({
      next: cars => {
        this.totalCars = cars.length;
        this.availableCars = cars.filter(c => c.carStatus === 'Available').length;
        this.rentedCars = cars.filter(c => c.carStatus === 'Rented').length;
        this.maintenanceCars = cars.filter(c => c.carStatus === 'Maintenance').length;
      },
      error: () => this.errorMessage = "Failed to fetch car data"
    });
  }
}

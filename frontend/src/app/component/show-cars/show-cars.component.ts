import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CarDTO } from 'src/app/dto/car';
import { AuthService } from 'src/app/service/AuthService/auth.service';
import { CarService } from 'src/app/service/CarService/car.service';

@Component({
  selector: 'app-show-cars',
  templateUrl: './show-cars.component.html',
  styleUrls: ['./show-cars.component.css']
})
export class ShowCarsComponent implements OnInit {

  customFilter = {
    location: '',
    passengerCapacity: 0,
    sortBy: ''
  };

  carList: CarDTO[] = [];
  filteredCars: CarDTO[] = [];
  locations: string[] = ['Chennai', 'Hyderabad', 'Coimbatore', 'Bangalore', 'Delhi', 'Kolkata', 'Ahmedabad'];

  constructor(
    private carService: CarService,
    private router: Router,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.getCars();
  }

  getCars(): void {
    this.carService.getAllCars().subscribe(data => {
      this.carList = data;
      this.filteredCars = [...this.carList];
    });
  }

  applyCustomFilter(): void {
    let cars = [...this.carList];

    if (this.customFilter.location) {
      cars = cars.filter(c => c.location === this.customFilter.location);
    }

    if (this.customFilter.passengerCapacity > 0) {
      cars = cars.filter(c => c.passengerCapacity >= this.customFilter.passengerCapacity);
    }

    if (this.customFilter.sortBy === 'priceLowToHigh') {
      cars.sort((a, b) => a.pricePerDay - b.pricePerDay);
    } else if (this.customFilter.sortBy === 'priceHighToLow') {
      cars.sort((a, b) => b.pricePerDay - a.pricePerDay);
    }

    this.filteredCars = cars;
  }

  onReset(): void {
    this.customFilter = {
      location: '',
      passengerCapacity: 0,
      sortBy: ''
    };
    this.filteredCars = [...this.carList];
  }

  reserveCar(car: CarDTO): void {
    this.router.navigate(['/reserve'], {
      queryParams: {
        carId: car.carId,
        carName: car.carName,
        location: car.location,
        passengerCapacity: car.passengerCapacity,
        pricePerDay: car.pricePerDay,
        customerName: this.authService.getUsernameFromToken()
      }
    });
  }

    deleteCar(carId: number) {
  if (confirm('Are you sure you want to delete this car?')) {
    this.carService.deleteCar(carId).subscribe(() => {
      alert('Car deleted successfully');
      this.getCars(); 
    });
  }
}

navigateToUpdate(carId: number) {
  this.router.navigate(['/updateCar'], { queryParams: { carId: carId } });
}


}

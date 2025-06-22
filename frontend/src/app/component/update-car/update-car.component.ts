import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CarDTO } from 'src/app/dto/car';
import { CarService } from 'src/app/service/CarService/car.service';

@Component({
  selector: 'app-update-car',
  templateUrl: './update-car.component.html',
  styleUrls: ['./update-car.component.css']
})
export class UpdateCarComponent {

  car!: CarDTO ;

  originalCar!: CarDTO;

  constructor(private route: ActivatedRoute, private carService: CarService, private router: Router,private location:Location) {}

  goBack()
  {
    this.location.back();
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
    const carId = params['carId'];
    if (carId) {
      this.carService.getCarById(carId).subscribe(data => {
        console.log(data);
        this.car = data;
        this.originalCar = { ...data };
      });
    }
  });
  }

  hasChanges(): boolean {
  return JSON.stringify(this.car) !== JSON.stringify(this.originalCar);
}

  updateCar() {
    this.carService.updateCar(this.car.carId, this.car).subscribe(() => {
      alert('Car updated successfully');
      this.router.navigate(['/showCars']);
    });
  }
}

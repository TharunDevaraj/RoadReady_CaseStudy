import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CarDTO } from 'src/app/dto/car';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  baseUrl : string = 'http://localhost:8080/api/users/';
  
    constructor(private http:HttpClient) { }

    getAllCars():Observable<CarDTO[]>
    {
      console.log("In service : getAllCars method called")

      const token = localStorage.getItem('token');

      const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
  
      return this.http.get<CarDTO[]>(this.baseUrl+"getAllCars",{headers})
    }

    addCar(car:any):Observable<CarDTO>
    {
      console.log("In service : addCar method called")

      const token = localStorage.getItem('token');

      const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
  
      return this.http.post<CarDTO>(this.baseUrl+"addCar",car,{headers})
    }

    getCarById(carId:any):Observable<CarDTO>
    {
      console.log("In service : getCarById method called")

      const token = localStorage.getItem('token');

      const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
  
      return this.http.get<CarDTO>(this.baseUrl+"getCar/"+carId,{headers})
    }

    updateCar(carId:any,car:any)
    {
      console.log("In service : updateCar method called")

      const token = localStorage.getItem('token');

      const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
  
      return this.http.put(this.baseUrl+"updateCar/"+carId,car,{headers, responseType: 'text'})
    }

    deleteCar(carId:any)
    {
      console.log("In service : deleteCar method called")

      const token = localStorage.getItem('token');

      const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
  
      return this.http.delete(this.baseUrl+"deleteCar/"+carId,{headers, responseType: 'text'})
    }



}

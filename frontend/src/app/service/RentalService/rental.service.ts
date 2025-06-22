import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PaymentDTO } from 'src/app/dto/payment';
import { ReservationDTO } from 'src/app/dto/reservation';

@Injectable({
  providedIn: 'root'
})
export class RentalService {
  
  
  baseUrl : string = 'http://localhost:8080/api/users/';

  constructor(private http:HttpClient) { }


  // Reservation Related

  getReservationById(reservationId:any):Observable<ReservationDTO>
  {
    console.log("In service: getReservationById method called");

    const token=localStorage.getItem('token');

    const headers=new HttpHeaders().set('Authorization','Bearer '+token);

    return this.http.get<ReservationDTO>(this.baseUrl+"getReservation/"+reservationId,{headers})
  }

  createReservation(reservation: any): Observable<ReservationDTO> {
    console.log("In service : createReservation method called")
    
          const token = localStorage.getItem('token');
    
          const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);

  return this.http.post<ReservationDTO>(this.baseUrl+'makeReservation', reservation,{headers});
  }

  updateReservation(reservation: any): Observable<any> {
    console.log("In service : updateReservation method called")
    
          const token = localStorage.getItem('token');
    
          const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);

    return this.http.put(this.baseUrl+'updateReservation', reservation,{headers, responseType: 'text' });

}

cancelReservation(reservationId:any): Observable<any> {
    console.log("In service : createReservation method called")
    
          const token = localStorage.getItem('token');
    
          const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);

  return this.http.delete(this.baseUrl+'cancelReservation/'+reservationId,{headers, responseType: 'text'});
}

checkIn(reservationId: any): Observable<any> {
    console.log("In service : checkin method called")
    
          const token = localStorage.getItem('token');
    
          const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);

    return this.http.put(this.baseUrl+'checkin/'+reservationId,null,{headers, responseType: 'text' });

}

checkOut(reservationId: any): Observable<any> {
    console.log("In service : checkout method called")
    
          const token = localStorage.getItem('token');
    
          const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);

    return this.http.put(this.baseUrl+'checkout/'+reservationId,null, {headers, responseType: 'text' });

}


// Payment Related

  makePayment(payment: any): Observable<PaymentDTO> {
    console.log("In service : makePayment method called")

      const token = localStorage.getItem('token');

      const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
  return this.http.post<PaymentDTO>(this.baseUrl+'makePayment', payment,{headers});
}

// History Related

  getReservationsByCustomerId(customerId: number) {
    console.log("In service : getReservationsByCustomerId method called")

      const token = localStorage.getItem('token');

      const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
  return this.http.get<any[]>(this.baseUrl+"getReservationByCustomer/"+customerId,{headers});
}

  getPaymentsByCustomerId(customerId: number) {
    console.log("In service : getPaymentsByCustomerId method called")

      const token = localStorage.getItem('token');

      const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
  return this.http.get<any[]>(this.baseUrl+"getPaymentByCustomer/"+customerId,{headers});
}


// Feedback Related Operations

submitFeedback(feedbackData: any) {

  console.log("In service : submitFeedback method called")

      const token = localStorage.getItem('token');

      const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
    return this.http.post(this.baseUrl+"submitFeedback", feedbackData,{headers});
  }

  getFeedbacksByCustomer(userId: number) {
    console.log("In service : getFeedbacksByCustomerId method called")

      const token = localStorage.getItem('token');

      const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
  return this.http.get<any[]>(this.baseUrl+"getFeedbackByCustomer/"+userId,{headers});
  }

  respondToFeedback(feedbackId: any, adminResponse: any) {
    console.log("In service : getAllFeedbacks method called")

      const token = localStorage.getItem('token');

      const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);

      return this.http.put(this.baseUrl+"setFeedbackStatus/"+feedbackId+"?adminResponse="+encodeURIComponent(adminResponse),null,{headers});
  }

  getAllFeedbacks() {
    console.log("In service : getAllFeedbacks method called")

      const token = localStorage.getItem('token');

      const headers = new HttpHeaders().set('Authorization', 'Bearer '+token);
  return this.http.get<any[]>(this.baseUrl+"getAllFeedbacks",{headers});
  }
  

}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthRequestDTO } from 'src/app/dto/authrequest';
import { UserDTO } from 'src/app/dto/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseUrl : string = 'http://localhost:8080/api/authenticate/'

  constructor(private http:HttpClient,private router:Router) { }

  registerUser(user:UserDTO):Observable<UserDTO>
  {
    console.log("In service : register method called")
    return this.http.post<UserDTO>(this.baseUrl+"register",user);
  }

  loginUser(authRequest:AuthRequestDTO) {
    console.log("In service : login method called")
    return this.http.post(this.baseUrl + 'login', authRequest, { responseType: 'text' });
  }

  verifyForgotDetails(forgotDetails: { userName: string; userId: null; phoneNumber: string; }) {
    console.log("In service : verifyDetails method called")
    return this.http.post(this.baseUrl + 'verifyDetails', forgotDetails, { responseType: 'text' });
  }

  getUsernameFromToken(): string | null {
  const token = localStorage.getItem('token');
  if (!token) return null;

  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.sub;
  } 
  catch (e) {
    console.error('Error decoding token:', e);
    return null;
    }
  }

  getUserIdFromToken(): number {
  const token = localStorage.getItem('token');
  if (!token) return 0;

  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.userId;
  } 
  catch (e) {
    console.error('Error decoding token:', e);
    return 0;
    }  // `userId` is now in token
}

getRoleFromToken(): string | null {
  const token = localStorage.getItem('token');
  if (!token) return null;

  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.role;
  } 
  catch (e) {
    console.error('Error decoding token:', e);
    return null;
    }  // `role` is now in token
}

  isAdmin(): boolean {
  return this.getRoleFromToken() === 'admin';
}

isAgent():boolean
{
  return this.getRoleFromToken()==='agent';

}

isCustomer():boolean
{
  return this.getRoleFromToken()==='customer';

}

logOut(){
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}

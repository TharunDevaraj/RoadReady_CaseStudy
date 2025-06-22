import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/AuthService/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

constructor(public router:Router,public authService:AuthService)
{

}

currentYear: number = new Date().getFullYear();


isLoggedIn(): boolean {
  return !!localStorage.getItem('token') && this.router.url!=='/changePassword';
}



  

}

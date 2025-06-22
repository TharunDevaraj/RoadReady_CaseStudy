import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/service/AuthService/auth.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit{
  isCustomer: boolean=false;
  isAgent: boolean=false;
  isAdmin: boolean=false;

  constructor(public authService: AuthService) {}
  
  ngOnInit(): void {
    console.log("Decoded Role:", this.authService.getRoleFromToken());

  this.isAdmin = this.authService.isAdmin();
  this.isAgent = this.authService.isAgent();
  this.isCustomer = this.authService.isCustomer();

  console.log("isAdmin", this.isAdmin);
  console.log("isAgent", this.isAgent);
  console.log("isCustomer", this.isCustomer);
  }
}

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserDTO } from 'src/app/dto/user';
import { AuthService } from 'src/app/service/AuthService/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{

  user: UserDTO = {
  userName: '',
  email: '',
  password: '',
  phoneNumber: '',
  roles: '',
  adminKey: ''
};

errorMessage:string='';


constructor(private authService:AuthService,private router:Router)
{

}

ngOnInit(): void {
    this.user = {
      userName: '',
      email: '',
      password: '',
      phoneNumber: '',
      roles: '',
      adminKey: ''
    };
  }

  onSubmit(form: any): void {
  if (form.valid) {
    this.authService.registerUser(this.user).subscribe(
      (response) => {
        console.log("User registered successfully:", response);
        this.errorMessage = '';
        form.resetForm();  
        alert("Registration successful! Please login.");
        this.router.navigate(['/login']);
      },
      (error) => {
        console.error("Registration failed:", error);
        const message = error?.error;

        if (message?.includes("Username")) {
          this.errorMessage = 'Username already exists.';
        } else if (message?.includes("Email")) {
          this.errorMessage = 'Email already exists.';
        }
        else if (message?.includes("key")) {
          this.errorMessage = 'Incorrect admin secret key.';
        } 
        else  {
          this.errorMessage = 'Registration failed. Try again.';
        }
        alert(this.errorMessage)
      }
    );
  }
}


}

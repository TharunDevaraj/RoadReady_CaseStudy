import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/AuthService/auth.service';
import { UserService } from 'src/app/service/UserService/user.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent {

  forgotDetails = {
    userName: '',
    userId:null,
    phoneNumber: ''
  };

  newPassword: string = '';
  confirmPassword: string = '';
  verified: boolean = false;
  message: string = '';
  success: boolean = false;

  constructor(private authService: AuthService,private userService:UserService,private router:Router) {}

  verifyUser() {
    this.authService.verifyForgotDetails(this.forgotDetails).subscribe(
       res => {
        if(res)
        {
          
          this.verified = true;
        this.message = "Verification successful. Please enter your new password.";
        this.success = true;
        localStorage.setItem('token',res);
        }
        else{
            this.message = "Verification failed. Please check the details.";
        this.success = false;
        }
      },
       err => {
        this.message = "Verification failed. Please check the details.";
        this.success = false;
      }
    );
  }

  changePassword() {
    if (this.newPassword !== this.confirmPassword) {
      this.message = "Passwords do not match.";
      this.success = false;
      return;
    }

    const payload = {
      userId:this.forgotDetails.userId,
      newPassword: this.newPassword
    };

    this.userService.changePassword(payload).subscribe({
      next: res => {
        this.message = "Password changed successfully. Now login using your new password";
        this.success = true;
        localStorage.removeItem('token');
        setTimeout(() => {
            this.router.navigate(['/login']);
          }, 4000);
      },
      error: err => {
        this.message = "Failed to update password. Try again later";
        this.success = false;
        localStorage.removeItem('token');
      }
    });

    
  }
}

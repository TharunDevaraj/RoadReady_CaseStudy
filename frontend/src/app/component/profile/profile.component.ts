import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserDTO } from 'src/app/dto/user';
import { AuthService } from 'src/app/service/AuthService/auth.service';
import { UserService } from 'src/app/service/UserService/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  user!: UserDTO ;

  isEditable: boolean = false;
  originalUser: any = {};

  constructor(
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private userService: UserService
  ) {}

  enableEdit(): void {
    this.isEditable = true;
  }

  ngOnInit(): void {
    const id = this.authService.getUserIdFromToken();
    if (id) {
      this.userService.getUserById(+id).subscribe(data => {
        this.user = data;
        this.originalUser = {...data};
      });
    }
  }

  hasChanges(): boolean {
  return this.user.userName !== this.originalUser.userName ||
         this.user.email !== this.originalUser.email ||
         this.user.phoneNumber !== this.originalUser.phoneNumber;
}


  updateUser() {
    const previousUsername = this.originalUser.userName;
  const newUsername = this.user.userName;

  this.userService.updateUser(this.user.userId!, this.user).subscribe(
       (updatedUser) => {
        this.user = updatedUser;
        this.originalUser = { ...updatedUser };
        this.isEditable = false;
        if (previousUsername !== newUsername) {
        alert('Username updated successfully. Login using your new username.');
        this.authService.logOut(); 
      } else {
        alert('Profile updated successfully!');
      }
      },
       (err) => {
        console.error('Error updating user:', err);
        alert('Failed to update profile.');
      });
    }
  }

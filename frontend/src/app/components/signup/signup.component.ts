import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { User } from '../../models/User.model';
import { Direction } from '../../models/Direction.model';
import { ShoppingCart } from '../../models/ShoppingCart.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, RouterOutlet],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  
  user: User;
  address: Direction;
  shoppingCart: ShoppingCart;

  @ViewChild('avatar')
  avatar: any;

  constructor(private router: Router, public loginService: LoginService){
    this.address = {street: '', number:'', zipCode:'', city: ''};
    this.shoppingCart = {totalCost: 0, items: []};
    this.user = {username: '', name: '', email: '', password: '', passwordConfirmation: '', rol: 'USER', direction: this.address, favouritesItems: [], shoppingCart: this.shoppingCart, orders: []};

  }

  registerUser(){
    if(!this.user.username || !this.user.name || !this.user.email || !this.user.password 
      || this.user.password != this.user.passwordConfirmation || !this.address.street || !this.address.number
      || !this.address.zipCode || !this.address.city){
        alert("some fields are empty");
    } else {
      let image: any;
      image = this.avatar.nativeElement.files[0];
      if(!image){
        alert("please, insert a profile image");
      } else {
        this.loginService.addUser(this.user).subscribe(
          (user: any) => {

            this.uploadImage(user);
          },
          (_: any) => alert("registering failed")
        );
      }
    }
  }

  getUnknownImage(){
    return this.loginService.getAnonymousUserImage();
  }

  uploadImage(user: User){
    const image = this.avatar.nativeElement.files[0];
    if(image) {
      let formData = new FormData();
      formData.append("avatar", image);
      this.loginService.setUserImage(user, formData).subscribe(
        (_:any) => {
          alert("registered successfully");
          this.router.navigate(['/signup'])
        },
        (error: any) => alert("error uploading user image")
      );
    }   
  }

}

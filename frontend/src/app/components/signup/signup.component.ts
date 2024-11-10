import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { User } from '../../models/User.model';
import { Direction } from '../../models/Direction.model';
import { ShoppingCart } from '../../models/ShoppingCart.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  
  user: User;
  address: Direction;
  shoppingCart: ShoppingCart;

  @ViewChild('file')
  avatar: any;

  constructor(private router: Router, public loginService: LoginService){
    this.address = {street: '', number:0, zipCode:0, city: ''};
    this.shoppingCart = {totalCost: 0, items: []};
    this.user = {username: '', name: '', email: '', password: '', passwordConfirmation: '', rol: 'USER', direction: this.address, favouritesItems: [], shoppingCart: this.shoppingCart, orders: []};

  }

  registerUser(){
    this.loginService.addUser(this.user).subscribe(
      (user: any) => {
        if(user == null){
          alert("it might be some empty fields");
        }
        this.uploadImage(user);
      },
      (_: any) => alert("registering faild")
    );
  }

  uploadImage(user: User){
    const image = this.avatar.nativeElement.files[0];
    if(image) {
      let formData = new FormData();
      formData.append("imageField", image);
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

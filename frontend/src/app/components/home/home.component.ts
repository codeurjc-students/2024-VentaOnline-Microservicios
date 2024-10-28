import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html'
  //styleUrls: ['./home.component.css']
})
export class HomeComponent {

  constructor(public loginService: LoginService){}

}

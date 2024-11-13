import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(private router: Router, public loginService: LoginService){}

  login(event: any, user: string, pass: string){
    event.preventDefault();

    this.loginService.login(user,pass);
    this.router.navigate(['/home']);
  }

}

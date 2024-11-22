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

  login(event: any, username: string, password: string){
    if(!username || !password){
      alert("some fields are incorrect");
    } else {
      event.preventDefault();

      this.loginService.login(username,password);
      this.router.navigate(['/home']);
    }
  }

}

import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { catchError, Subject, takeUntil, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  private onDestroy = new Subject<void>();
  constructor(private router: Router, public loginService: LoginService){}

  //espera
  async login(event: any, username: string, password: string){
    if(!username || !password){
      alert("some fields are incorrect");
    } else {
      event.preventDefault();
      
      await this.loginUser(username, password);
    }
  }

  loginUser(username: string, password: string){
    return new Promise((resolve, reject) => {
      
      this.loginService.login(username,password)
        .pipe(
          takeUntil(this.onDestroy),
          catchError((error: HttpErrorResponse) => {
            console.error("Error capturado:", error); // Aquí capturas el error de la petición
            
            // Mostrar mensaje de error al usuario
            alert(error.error.msg || 'Ha ocurrido un error inesperado.')
            
            reject(error); // Rechaza la promesa con el error capturado
            return throwError(error); // Asegúrate de retornar el error para manejarlo adecuadamente
          })
        )
        .subscribe((data: any) => {
          if (data.resp === 400 || data.resp === 500) {
            alert('Error ' + data.msg)
            reject(data.msg); // Rechazar la promesa si hay error en la respuesta del servidor
          } else {
            //console.log(data.data);
            this.router.navigate(['/home']);
            resolve(data.data); // Resolver la promesa con los datos recibidos
          }
        });
    });
  }

}

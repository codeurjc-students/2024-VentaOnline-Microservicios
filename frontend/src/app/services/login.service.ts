import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { User } from '../models/User.model';


const USER_URL = '/databases/users';

@Injectable({ providedIn: 'root'})
export class LoginService {

    user: any;
    logged: boolean = false;
    
    //constructor
    constructor(private https: HttpClient){
        this.reqIsLogged();
    }

    reqIsLogged() {
        this.https.get(USER_URL+ '/current', {withCredentials: true}).subscribe(
            response => {
                this.user = response as User;
                this.logged = true;
            }, error => {
                this.handleError(error);
            }
        );
    }

    isLogged(){
        return this.logged;
    }

    handleError(error: any){
        return throwError(() => "Server error (" + error.status + "): " + error.text());
    }
}
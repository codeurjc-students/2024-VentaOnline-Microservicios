import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { User } from '../models/User.model';
import { AuthResponse } from '../models/Auth-Ressponse.model';
import { error } from 'console';


const AUTH_URL = '/api/auth';
const USER_URL = '/databases/users';


@Injectable({ providedIn: 'root'})
export class LoginService {

    user: any;
    admin: any;
    logged: boolean = false;
    
    //constructor
    constructor(private https: HttpClient){
        this.reqIsLogged(); 
    }

    reqIsLogged() {
        this.https.get('/api/users/current', {withCredentials: true}).subscribe(
            (response) => {
                //console.log(response);
                this.user = response as User;
                this.logged = true;
            }, (error) => {
                if (error.status != 404) {
                    console.error('Error when asking if logged: ' + JSON.stringify(error));
                }
            }
        );
    }

    login(username: string, password: string) {
        this.https.post<AuthResponse>('/api/auth/login', {username, password}, {withCredentials: true}).subscribe(
            (response) => {
                this.reqIsLogged();
                //console.log(this.user);
                //console.log(this.logged);
            },
            error => alert("wrong credentials")
        );
    }

    logout(){
        this.https.post('/api/auth/logout', {}, { withCredentials: true}).subscribe(
            (resp: any) => {
                this.logged = false;
                this.user = undefined;
            }, (error) => {
                console.log(error)
            }
        );
    }

    isLogged(){
        return this.logged;
    }

    isUser(){
        return this.user && this.user.rol.indexOf('USER') !== -1;
    }

    isAdmin(){
        return this.admin && this.admin.rol.indexOf('ADMIN') !== -1;
    }

    getUserImage(){
        return '/api/users/' + this.user.id + '/image';      
    }

    getAnonymousUserImage(){
        return '/api/users/39/image';
    }

    getUserName(){
        return this.user.username;
    }

    getCurrentUser(){
        return this.user;
    }

    addUser(user: User){
        return this.https.post('/api/users/new',user).pipe(
            catchError((error) => {
                return this.handleError(error)
            })
        );

    }

    setUserImage(user: User, formData: FormData){
        return this.https.post('/api/users/' + user.id + '/image', formData).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        );
    }

    handleError(error: any){
        return throwError(() => "Server error (" + error.status + "): " + error.text());
    }
}
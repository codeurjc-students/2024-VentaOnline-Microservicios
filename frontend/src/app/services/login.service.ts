import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { User } from '../models/User.model';


const AUTH_URL = '/api/auth';
const USER_URL = '/databases/users';


@Injectable({ providedIn: 'root'})
export class LoginService {

    user: any;
    admin:any;
    logged: boolean = false;
    
    //constructor
    constructor(private https: HttpClient){
        //this.reqIsLogged();
    }

    reqIsLogged() {
        this.https.get(USER_URL + '/current', {withCredentials: true}).subscribe(
            response => {
                this.user = response as User;
                this.logged = true;
            }, error => {
                if (error.status != 404) {
                    console.error('Error when asking if logged: ' + JSON.stringify(error));
                }
            }
        );
    }

    login(user: string, pass: string) {
        this.https.post(AUTH_URL + '/login', {username: user, password: pass}, {withCredentials: true}).subscribe(
            (_: any) => this.reqIsLogged,
            (error) => alert("wrong credentials")
        );
    }

    logout(){
        this.https.post(AUTH_URL + '/logout', { withCredentials: true}).subscribe(
            (resp: any) => {
            this.logged = false;
            this.user = undefined;
        });
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

    getUserImage(): Observable<any>{
        return this.https.get(USER_URL + '/' + this.user.username + '/image').pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>; 
    }

    getAnonymousUserImage(){
        return 'https://localhost:8443' + USER_URL + '/39/image';
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
        return this.https.post(USER_URL + user.id + '/image', formData).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        );
    }

    handleError(error: any){
        return throwError(() => "Server error (" + error.status + "): " + error.text());
    }
}
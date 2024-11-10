import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';
import { User } from '../models/User.model';


const ROOT_URL = '/databases';
const AUTH_URL = '/api/auth';
const USER_URL = '/users';


@Injectable({ providedIn: 'root'})
export class LoginService {

    user: any;
    admin:any;
    logged: boolean = false;
    
    //constructor
    constructor(private https: HttpClient){
        this.reqIsLogged();
    }

    reqIsLogged() {
        this.https.get(ROOT_URL + USER_URL+ '/current', {withCredentials: true}).subscribe(
            response => {
                this.user = response as User;
                this.logged = true;
            }, error => {
                this.handleError(error);
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
        this.https.post(AUTH_URL + '/logout', { withCredentials: true})
        .subscribe((resp: any) => {
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

    getUserImage(){
        return ROOT_URL + '/' + this.user.username + '/image'; 
    }

    getAnonymousImage(){
        return ROOT_URL + USER_URL + '/39/image';
    }

    getUserName(){
        return this.user.username;
    }

    getCurrentUser(){
        return this.user;
    }

    addUser(user: User){
        return this.https.post(ROOT_URL + USER_URL + '/new',user).pipe(
            catchError((error) => {
                return this.handleError(error)
            })
        );

    }

    setUserImage(user: User, formData: FormData){
        return this.https.post(ROOT_URL + USER_URL + user.id + '/image', formData).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        );
    }

    handleError(error: any){
        return throwError(() => "Server error (" + error.status + "): " + error.text());
    }
}
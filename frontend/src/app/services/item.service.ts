import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, throwError } from "rxjs";

//const ROOT_URL = 'https://localhost:8443';
//const ITEM_URL = '/databases/items';
const BASE_URL = '/databases';

@Injectable({providedIn: 'root'})
export class ItemService{

    constructor(private http: HttpClient){}

    getFavouritesItems(tam: number, username: string): Observable<any>{
        return this.http.get(BASE_URL + '/items/favourites/'+ username +'?size=' + tam).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>;
    }

    getItems(tam: number): Observable<any>{
        return this.http.get(BASE_URL + '/items?size=' + tam).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>;
    }

    getTotalItems(): Observable<any>{
     return this.http.get(BASE_URL + '/itemsListing').pipe(
        catchError((error) => {
            return this.handleError(error);
        })
     )as Observable<any>; 
    }

    /*getItemImage(id: number | undefined): Observable<any>{
        return this.http.get(ITEM_URL + '/' + id + '/image').pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>;
    }*/

    private handleError(error: any){
        return throwError(() => "Server error(" + error.status + "): " + error.text);
    }

}
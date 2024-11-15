import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { catchError, Observable, throwError } from "rxjs";

const ITEM_URL = '/api/items';
const INVENTORY_URL = '/api/inventory/items';

@Injectable({providedIn: 'root'})
export class ItemService{

    constructor(private http: HttpClient){}

    getUserFavouritesItems(tam: number, username: string): Observable<any>{
        return this.http.get(ITEM_URL + '/favourites/'+ username +'?size=' + tam).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>;
    }

    getItems(tam: number): Observable<any>{
        return this.http.get(ITEM_URL + '?size=' + tam).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>;
    }

    getTotalItems(): Observable<any>{
        return this.http.get(ITEM_URL + '/listing').pipe(
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

    getItemImage(id: number | undefined){
        return '/api/inventory/items/' + id + '/image';
    }

    private handleError(error: any){
        return throwError(() => "Server error(" + error.status + "): " + error.text);
    }

}
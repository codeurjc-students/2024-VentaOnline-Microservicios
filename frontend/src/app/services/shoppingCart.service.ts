import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, throwError } from "rxjs";

const CART_URL = '/api/shoppingcart/';

@Injectable({ providedIn: 'root'})
export class ShoppingCartService {

    constructor(private https: HttpClient){}

    getUserItems(username: string){
        return this.https.get(CART_URL + username + '/items').pipe(
            catchError((error) => {
                return this.handleError(error)
            })
        );
    }

    deleteItem(id: number | undefined){
        return this.https.delete(CART_URL + 'items/' + id + '/remove').pipe(
            catchError((error) => {
                return this.handleError(error)
            })
        );
    }

    handleError(error: any){
        return throwError(() => "Server error (" + error.status + "): ") + error.text()
;    }
}
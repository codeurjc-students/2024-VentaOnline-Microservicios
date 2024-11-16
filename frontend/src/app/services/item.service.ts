import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { catchError, Observable, throwError } from "rxjs";
import { Item } from "../models/Item.model";

const ITEM_URL = '/api/items';
const INVENTORY_URL = '/api/inventory/items';

@Injectable({providedIn: 'root'})
export class ItemService{

    constructor(private https: HttpClient){}

    getUserFavouritesItems(tam: number, username: string): Observable<any>{
        return this.https.get(ITEM_URL + '/favourites/'+ username +'?size=' + tam).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>;
    }

    getItems(tam: number): Observable<any>{
        return this.https.get(ITEM_URL + '?size=' + tam).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>;
    }

    getTotalItems(): Observable<any>{
        return this.https.get(ITEM_URL + '/listing').pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>; 
    }

    setItem(item: Item): Observable<any>{
        return this.https.post(INVENTORY_URL, item).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        );
    }

    getItem(id: number): Observable<any>{
        return this.https.get(INVENTORY_URL + '/' + id).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>;
    }
    /*getItemImage(id: number | undefined): Observable<any>{
        return this.https.get(ITEM_URL + '/' + id + '/image').pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>;
    }*/

    getItemImage(id: number | undefined){
        return '/api/inventory/items/' + id + '/image';
    }

    setItemImage(item: Item, formData: FormData): Observable<any>{
        return this.https.post(INVENTORY_URL + item.id + '/image', formData).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        );
    }

    /*updateItem(id: number): Observable<any>{
        return this.https.put(INVENTORY_URL + ).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>;
    }*/

    deleteItem(id: number | undefined): Observable<any>{
        return this.https.delete(INVENTORY_URL + '/' + id).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>;
    }

    putItem(item: Item): Observable<any>{
        return this.https.put(INVENTORY_URL + '/' + item.id + '/update', item).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>;
    }

    private handleError(error: any){
        return throwError(() => "Server error(" + error.status + "): " + error.text);
    }
}
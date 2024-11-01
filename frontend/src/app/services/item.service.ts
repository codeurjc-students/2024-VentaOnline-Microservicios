import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, throwError } from "rxjs";

const ROOT_URL = '//localhost:8443';
const ITEM_ROOT_URL = '//localhost:8444';
const BASE_URL = '/databases/items';

@Injectable({providedIn: 'root'})
export class ItemService{

    constructor(private http: HttpClient){}

    getFoundedItems(): Observable<any>{
        return this.http.get(ROOT_URL + BASE_URL).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>;
    }

    getItems(tam: number): Observable<any>{
        return this.http.get(ROOT_URL + BASE_URL + '?size=' + tam).pipe(
            catchError((error) => {
                return this.handleError(error);
            })
        )as Observable<any>;
    }

    getItemImage(id: number | undefined){
        return this.http.get(ITEM_ROOT_URL + BASE_URL + '/' + id + '/image');
    }

    private handleError(error: any){
        return throwError(() => "Server error(" + error.status + "): " + error.text());
    }

}
import { ItemToBuy } from "./ItemToBuy.model";
import { Stock } from "./Stock.model";
import { User } from "./User.model";

export interface Item {
    id?: number,
    name: string,
    description: string,
    price: number,
    gender: string,
    type: string,
    stocks: Stock[],
    favouritesUsers: User[],
    itemsToBuy: ItemToBuy[]
}
import { ItemToBuy } from "./ItemToBuy.model";
import { User } from "./User.model";

export interface Item {
    id?: number,
    name: string,
    description: string,
    price: number,
    gender: string,
    type: string,
    stock: number,
    sizes: string[],
    stocks: number[],
    favouritesUsers: User[],
    itemsToBuy: ItemToBuy[]
}
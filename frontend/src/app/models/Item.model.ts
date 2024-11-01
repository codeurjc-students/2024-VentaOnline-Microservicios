import { ItemToBuy } from "./ItemToBuy.model";
import { User } from "./User.model";

export interface Item {
    id?: number,
    name: String,
    description: String,
    price: number,
    image: Blob,
    gender: String,
    type: String,
    stock: number,
    sizes: String[],
    stocks: number[],
    favouritesUsers: User[],
    itemsToBuy: ItemToBuy[]
}
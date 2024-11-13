import { Direction } from "./Direction.model";
import { Item } from "./Item.model";
import { Order } from "./Order.model";
import { ShoppingCart } from "./ShoppingCart.model";

export interface User {
    id?: number,
    username: string,
    name: string,
    email: string,
    password: string,
    passwordConfirmation: string,
    rol: string,
    direction: Direction,
    favouritesItems: Item[],
    shoppingCart: ShoppingCart,
    orders: Order[]
}
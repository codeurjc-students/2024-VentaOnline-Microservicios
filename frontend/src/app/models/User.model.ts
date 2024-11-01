import { Direction } from "./Direction.model";
import { Item } from "./Item.model";
import { Order } from "./Order.model";
import { ShoppingCart } from "./ShoppingCart.model";

export interface User {
    id?: number,
    username: String,
    name: String,
    email: String,
    password: String,
    passwordConfirmation: String,
    rol: String,
    avatar: Blob,
    direction: Direction,
    favouritesItems: Item[],
    shoppingCart: ShoppingCart,
    orders: Order[]
}
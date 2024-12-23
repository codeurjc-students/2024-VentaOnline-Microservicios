import { Item } from "./Item.model";
import { Order } from "./Order.model";
import { ShoppingCart } from "./ShoppingCart.model";

export interface ItemToBuy {
    id?: number,
    item: Item,
    size: String,
    count: number,
    order: Order,
    shoppingCart: ShoppingCart
}
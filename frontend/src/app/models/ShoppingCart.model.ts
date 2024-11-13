import { ItemToBuy } from "./ItemToBuy.model";
import { User } from "./User.model";

export interface ShoppingCart {
    id?: number,
    totalCost: number,
    items: ItemToBuy[],
    
}
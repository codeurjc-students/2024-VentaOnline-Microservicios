import { ItemToBuy } from "./ItemToBuy.model";
import { User } from "./User.model";

export interface ShoppingCart {
    id?: number,
    user: User,
    totalCost: number,
    items: ItemToBuy[],
    
}
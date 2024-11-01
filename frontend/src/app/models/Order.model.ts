import { User } from "./User.model";

export interface Order {
    id?: number,
    user: User,
    totalCost: number
}
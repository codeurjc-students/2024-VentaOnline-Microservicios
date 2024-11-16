import { User } from "./User.model";

export interface Direction{
    id?: number,
    street: string,
    number: string,
    zipCode: string,
    city: string
}
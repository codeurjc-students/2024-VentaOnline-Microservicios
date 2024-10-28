export interface User {
    id?: number,
    username: String,
    name: String,
    email: String,
    password: String,
    passwordConfirmation: String,
    rol: String,
    avatar: Blob
}
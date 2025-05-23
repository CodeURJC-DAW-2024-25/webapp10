import { TicketDTO } from "./ticket.dto";

export interface UserDTO {
    id?: number,
    fullName: string,
    userName: string,
    phone: number,
    email: string,
    password: string,
    age: number,
    numTicketsBought: number,
    favoriteGenre: string,
    image: boolean,
    roles: string[],
    tickets: TicketDTO[],
    csrfToken?: string
}
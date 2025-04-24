export interface TicketDTO {
    id?: number,
    ticketType: string,
    prices: number,
    userOwnerId?: number,
    numTickets: number,
    concertId?: number,
}
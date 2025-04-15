import { ArtistDTO } from "./artist.dto";
import { TicketDTO } from "./ticket.dto";

export interface ConcertDTO {
	id?: number,
	concertName: string,
	concertDetails: string,
    concertDate: string,
    concertTime: string,
    location: string,
    stadiumPrice: number,
    trackPrice: number,
    map: string,
	concertImage: boolean,
    color: string,
    artists: ArtistDTO[],
    tickets: TicketDTO[]
}
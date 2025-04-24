import { ConcertDTO } from "../dtos/concert.dto";

export function getConcertImage(concert: ConcertDTO): string {
  return concert.concertImage
    ? `/api/v1/concerts/${concert.id}/image`
    : '/assets/images/no_image.png';
}

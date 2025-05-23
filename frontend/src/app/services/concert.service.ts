import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { ConcertDTO } from "../dtos/concert.dto";
import { map } from 'rxjs/operators';

const BASE_URL = "/api/v1/concerts/";

@Injectable({ providedIn: "root" })
export class ConcertService {
    constructor(private httpClient: HttpClient) {}

    public getConcerts(userId: number | null, page = 0, size = 10): Observable<ConcertDTO[]> {
        let url = `/api/v1/concerts/?page=${page}&size=${size}`;
        if (userId !== null) {
          url += `&userId=${userId}`;
        }   
        return this.httpClient.get<any>(url).pipe(
          map(response => response.content)
        );
      }

    public getConcert(concertId: number | string): Observable<ConcertDTO> {
        const id = Number(concertId);
        return this.httpClient
            .get<ConcertDTO>(BASE_URL + id)
            .pipe(catchError((error) => this.handleError(error))) as Observable<ConcertDTO>;
    }

    public createOrReplaceConcert(concert: ConcertDTO): Observable<ConcertDTO> {
        if (!concert.id) {
            return this.httpClient
                .post<ConcertDTO>(BASE_URL, concert)
                .pipe(catchError((error) => this.handleError(error))) as Observable<ConcertDTO>;
        } else {
          return this.httpClient
            .put<ConcertDTO>(BASE_URL + concert.id, concert)
            .pipe(catchError((error) => this.handleError(error))) as Observable<ConcertDTO>;
        }
    }

    public createOrReplaceConcertImage(concert: ConcertDTO, formData: FormData): Observable<ConcertDTO> {
        if (concert.concertImage) {
            return this.httpClient
            .put<ConcertDTO>(BASE_URL + concert.id + "/image", formData)
            .pipe(catchError((error) => this.handleError(error))) as Observable<ConcertDTO>;
        } else {
            return this.httpClient
            .post<ConcertDTO>(BASE_URL + concert.id + "/image", formData)
            .pipe(catchError((error) => this.handleError(error))) as Observable<ConcertDTO>;
        }
    }

    public deleteConcertImage(concert: ConcertDTO): Observable<ConcertDTO> {
        return this.httpClient
          .delete<ConcertDTO>(BASE_URL + concert.id + "/image")
          .pipe(catchError((error) => this.handleError(error))) as Observable<ConcertDTO>;
    }

    public deleteConcert(concert: ConcertDTO): Observable<ConcertDTO> {
        return this.httpClient
          .delete<ConcertDTO>(BASE_URL + concert.id)
          .pipe(catchError((error) => this.handleError(error))) as Observable<ConcertDTO>;
    }
    
    private handleError(error: any) {
        console.log("ERROR:");
        console.error(error);
        return throwError("Server error (" + error.status + "): " + error.text());
    }
}
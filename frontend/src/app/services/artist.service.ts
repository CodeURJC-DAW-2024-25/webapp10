import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/operators";
import { ArtistDTO } from '../dtos/artist.dto';

const BASE_URL = '/api/v1/artists/';

@Injectable({ providedIn: 'root' })
export class ArtistService {
  constructor(private httpClient: HttpClient) {}

    public getArtists(): Observable<ArtistDTO[]> {
        return this.httpClient
            .get<ArtistDTO[]>(BASE_URL)
            .pipe(catchError((error) => this.handleError(error))) as Observable<ArtistDTO[]>;
    }
  
    public getArtist(id: number | string): Observable<ArtistDTO> {
        return this.httpClient
            .get<ArtistDTO>(BASE_URL + id)
            .pipe(catchError((error) => this.handleError(error))) as Observable<ArtistDTO>;
    }
  
    public createOrReplaceArtist(artist: ArtistDTO): Observable<ArtistDTO> {
        if (!artist.id) {
            return this.httpClient
                .post<ArtistDTO>(BASE_URL, artist)
                .pipe(catchError((error) => this.handleError(error))) as Observable<ArtistDTO>;
        } else {
        return this.httpClient
            .put<ArtistDTO>(BASE_URL + artist.id, artist)
            .pipe(catchError((error) => this.handleError(error))) as Observable<ArtistDTO>;
        }
    }
  
    public deleteArtist(artist: ArtistDTO): Observable<ArtistDTO> {
        return this.httpClient
        .delete<ArtistDTO>(BASE_URL + artist.id)
        .pipe(catchError((error) => this.handleError(error))) as Observable<ArtistDTO>;
    }
    
    private handleError(error: any) {
        console.log("ERROR:");
        console.error(error);
        return throwError("Server error (" + error.status + "): " + error.text());
    }
}
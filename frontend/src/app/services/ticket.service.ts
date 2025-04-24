

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { TicketDTO } from '../dtos/ticket.dto';

const BASE_URL = "/api/v1/tickets/";

@Injectable({ providedIn: 'root' })

export class TicketService {


    constructor(private httpClient: HttpClient) {}

    public createTicket(ticket: TicketDTO): Observable<TicketDTO> {
        return this.httpClient
            .post<TicketDTO>(BASE_URL,ticket)
            .pipe(catchError((error) => this.handleError(error)));
    }


    private handleError(error: any): Observable<never> {
        console.error('Error:', error);
        return throwError(() => new Error('Server error (' + error.status + '): ' + error.message || error));
    }
}





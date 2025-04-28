import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASE_URL = '/api/v1/graphics';

@Injectable({ providedIn: 'root' })

export class GraphicsService {

  constructor(private http: HttpClient) {}

  getPieChartData(concertId: number): Observable<any> {
    return this.http.get<any>(`${BASE_URL}/piechart/${concertId}`);
  }

  getBarChartData(): Observable<any> {
    return this.http.get<any>(`${BASE_URL}/bargraph`);
  }
}
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {

  constructor(private http: HttpClient) {}

  login(credentials: { username: string; password: string }): Observable<any> {
    return this.http.post('/api/v1/auth/login', credentials, { withCredentials: true });
  }

  register(data: { username: string; password: string }): Observable<any> {
    return this.http.post('/api/v1/auth/register', data, { withCredentials: true });
  }

  logout(): Observable<any> {
    return this.http.post('/api/v1/auth/logout', {}, { withCredentials: true });
  }

  getUserData(): Observable<any> {
    return this.http.get('/api/v1/me', { withCredentials: true });
  }
}



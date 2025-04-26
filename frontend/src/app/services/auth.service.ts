import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl: string = '/api/v1/auth';
  private loginStatusSubject = new Subject<boolean>();

  constructor(private http: HttpClient) {}

  register(data: FormData): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, data);
  }

  login(credentials: { username: string; password: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, credentials, { withCredentials: true }).pipe(
      tap((response: any) => {
        this.saveToken(response.token);
        this.emitLoginStatus(true);
      })
    );
  }

  logout(): Observable<any> {
    return this.http.post(`${this.baseUrl}/logout`, {}, { withCredentials: true }).pipe(
      tap(() => {
        this.clearToken();
        this.emitLoginStatus(false);
      })
    );
  }

  saveToken(token: string): void {
    localStorage.setItem('auth_token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }

  clearToken(): void {
    localStorage.removeItem('auth_token');
  }

  isAuthenticated(): boolean {
    return !!this.getToken();  
  }

  emitLoginStatus(logged: boolean): void {
    this.loginStatusSubject.next(logged);
  }

  getLoginStatus(): Observable<boolean> {
    return this.loginStatusSubject.asObservable();
  }

  
}

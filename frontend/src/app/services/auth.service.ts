import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl: string = '/api/v1/auth';

  constructor(private http: HttpClient) {}

  register(data: { fullName: string, username: string, phone: string, email: string, password: string, age: number, profilePhoto: File | null }): Observable<any> {
    const formData = new FormData();
    formData.append('fullName', data.fullName);
    formData.append('username', data.username);
    formData.append('phone', data.phone);
    formData.append('email', data.email);
    formData.append('password', data.password);
    formData.append('age', data.age?.toString() || '');

    if (data.profilePhoto) {
      formData.append('profilePhoto', data.profilePhoto, data.profilePhoto.name);
    }

    return this.http.post(`${this.baseUrl}/register`, formData);
  }

  login(credentials: { username: string; password: string }): Observable<any> {
    return this.http.post('/api/v1/auth/login', credentials, { withCredentials: true });
  }

  logout(): Observable<any> {
    return this.http.post('/api/v1/auth/logout', {}, { withCredentials: true });
  }
}


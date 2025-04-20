import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { UserDTO } from '../dtos/user.dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api/v1/users';

  constructor(private httpClient: HttpClient) {}

  getUser(userId: number | string): Observable<UserDTO> {
    return this.httpClient.get<UserDTO>(`${this.baseUrl}/${userId}`).pipe(
      catchError(error => {
        console.error(`Error fetching user with ID ${userId}:`, error);
        return throwError(error);
      })
    );
  }

  addOrUpdateUser(user: UserDTO) {
		if (!user.id) {
			return this.addUser(user);
		} else {
			return this.updateUser(user);
		}
	}

  private addUser(user: UserDTO) {
		return this.httpClient.post(`${this.baseUrl}`, user).pipe(
			catchError(error => this.handleError(error))
		);
	}

	private updateUser(user: UserDTO) {
		return this.httpClient.put(`${this.baseUrl}/${user.id}`, user).pipe(
			catchError(error => this.handleError(error))
		);
	}

	private handleError(error: any) {
		console.error(error);
		return throwError("Server error (" + error.status + "): " + error.text())
	}

}
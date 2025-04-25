import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError, BehaviorSubject  } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { UserDTO } from '../dtos/user.dto';

const BASE_URL = "/api/v1/users/";

@Injectable({ providedIn: 'root'})
export class UserService {

  private currentUserSubject = new BehaviorSubject<UserDTO | null>(null);  
  currentUser$ = this.currentUserSubject.asObservable(); 

  constructor(private httpClient: HttpClient) {}

  getUser(id: string | number): Observable<UserDTO> {
    const userId = Number(id);
    return this.httpClient
      .get<UserDTO>(BASE_URL + userId) 
      .pipe(catchError((error) => this.handleError(error)));
  }

  getCurrentUser(): Observable<UserDTO> {
    return this.httpClient.get<UserDTO>(BASE_URL+'currentUser', { withCredentials: true });
  }

  public createOrReplaceUser(user: UserDTO): Observable<UserDTO> {
    if (!user.id) {
      return this.httpClient
        .post<UserDTO>(BASE_URL, user)
        .pipe(
          catchError((error) => this.handleError(error))
        );
    } else {
      return this.httpClient
        .put<UserDTO>(BASE_URL + user.id, user)
        .pipe(
          catchError((error) => this.handleError(error))
        );
    }
  }

  public createOrReplaceUserImage(user: UserDTO, formData: FormData): Observable<UserDTO> {
    if (user.image) {
      return this.httpClient
        .put<UserDTO>(BASE_URL + user.id+"/image", formData)
        .pipe(
          catchError((error) => this.handleError(error)),
          tap(updatedUser => this.updateCurrentUser(updatedUser))
        );
    } else {
      return this.httpClient
        .post<UserDTO>(BASE_URL + user.id+ "/image", formData)
        .pipe(
          catchError((error) => this.handleError(error)),
          tap(updatedUser => this.updateCurrentUser(updatedUser)) 
        );
    }
  }
  public deleteUserImage(user: UserDTO): Observable<UserDTO> {
    return this.httpClient
      .delete<UserDTO>(BASE_URL + user.id+"/image")
      .pipe(
        catchError((error) => this.handleError(error))
      );
  }

    updateCurrentUser(user: UserDTO): void {
      this.currentUserSubject.next(user);
    }

  private handleError(error: any): Observable<never> {
    console.error('Error:', error);
    return throwError(() => new Error('Server error (' + error.status + '): ' + error.message || error));
  }
}

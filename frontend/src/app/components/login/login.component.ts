import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service'; 
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username = '';
  password = '';
  error: string = '';

  constructor(
    private auth: AuthService, 
    private router: Router,
    activatedRoute: ActivatedRoute) {
      const id = activatedRoute.snapshot.params["id"];
    }

    login(): void {
      this.auth.login({ username: this.username, password: this.password }).subscribe({
        next: (response) => {
          this.auth.saveToken(response.token); 
          this.auth.emitLoginStatus(true); 
          this.router.navigate(['/']); 
        },
        error: (error) => {
          console.error('Login failed:', error);
          this.router.navigate(['/error/login']);
        }
      });
    }
}



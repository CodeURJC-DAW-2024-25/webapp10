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

  login() {
    const loginData = {
      username: this.username,
      password: this.password
    };

    this.auth.login(loginData).subscribe({
      next: (response) => {
        localStorage.setItem('auth_token', response.token);
        alert('Login successful');
        this.router.navigate(['/']);
      },
      error: (err: HttpErrorResponse) => {
        console.error(err);
        this.router.navigate(['/error/login']);
      },
    });
  }
}



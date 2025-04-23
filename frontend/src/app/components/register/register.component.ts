import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service'; 
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  fullName = '';
  username = '';
  phone = '';
  email = '';
  password = '';
  confirmPassword = '';
  age = 0;
  profilePhoto: File | null = null;
  error: string = '';
  token: string = '';

  constructor(
    private auth: AuthService, 
    private router: Router,
    activatedRoute: ActivatedRoute) {
      const id = activatedRoute.snapshot.params["id"];
    }

  register() {
    if (this.password !== this.confirmPassword) {
      this.error = 'Passwords do not match'; 
      return;
    }

    const registerData = {
      fullName: this.fullName,
      username: this.username,
      phone: this.phone,
      email: this.email,
      password: this.password,
      age: this.age,
      profilePhoto: this.profilePhoto
    };

    this.auth.register(registerData).subscribe({
      next: (response) => {
        this.router.navigate(['/login']); 
      },
      error: (err: HttpErrorResponse) => {
        console.error(err);
        this.error = 'Registration failed: ' + (err.error?.message || 'Unknown error');
      },
    });
  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    this.profilePhoto = file ? file : null;
  }
}



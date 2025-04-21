import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
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

  constructor(private auth: AuthService, private router: Router) {}

  register() {
    if (this.password !== this.confirmPassword) {
      alert('Passwords do not match');
      return;
    }

    const registerData = {
      fullName: this.fullName,
      username: this.username,
      phone: this.phone,
      email: this.email,
      password: this.password,
      age: this.age,
      profilePhoto: this.profilePhoto,
      token: this.token
    };

    this.auth.register(registerData).subscribe({
      next: () => {
        alert('Registration successful');
        this.router.navigate(['/login']);
      },
      error: (err: HttpErrorResponse) => {
        console.error(err);
        alert('Registration failed: ' + (err.error?.message || 'unknown error'));
      },
    });
  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    this.profilePhoto = file ? file : null;
  }
}

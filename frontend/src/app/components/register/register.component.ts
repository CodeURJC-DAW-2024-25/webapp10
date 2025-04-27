import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service'; 
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

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
    
      const formData = new FormData();
      formData.append('fullName', this.fullName);
      formData.append('username', this.username);
      formData.append('phone', this.phone);
      formData.append('email', this.email);
      formData.append('password', this.password);
      formData.append('age', this.age.toString());
      if (this.profilePhoto) {
        formData.append('profilePhoto', this.profilePhoto, this.profilePhoto.name);
      }
    
      this.auth.register(formData).subscribe({
        next: () => this.router.navigate(['/login']),

        error: (err) => {
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



import { Component, OnInit, OnDestroy  } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';  
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit, OnDestroy  {
  logged: boolean = false;
  admin: boolean = false;
  userName: string = '';
  id: number | null = null;
  user: any = {};
  token: string = '';
  private userSubscription!: Subscription;


  constructor(private router: Router, private http: HttpClient, private authService: AuthService, private userService: UserService) {}

  ngOnInit(): void {
    this.authService.getLoginStatus().subscribe((loggedIn) => {
      if (loggedIn) {
        this.checkUserStatus();
      } else {
        this.resetUserState();
      }
    });

    this.checkUserStatus();
  }

  private resetUserState(): void {
    this.logged = false;
    this.admin = false;
    this.userName = '';
    this.id = null;
    this.user = {};
  }

  ngOnDestroy(): void {
    if (this.userSubscription) {
      this.userSubscription.unsubscribe();
    }
  }

  checkUserStatus(): void {
    this.http.get<any>('/api/v1/users/currentUser', { withCredentials: true }).subscribe({
      next: (data) => {
        this.logged = true;
        this.userName = data.userName;
        this.id = data.id;
        this.user = data;
        this.admin = data.roles.includes('ADMIN');
        this.token = data.csrfToken;
      },
      error: () => {
        this.resetUserState();
      }
    });
  }

  navigateTo(path: string): void {
    this.router.navigate([path]);
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: () => {
        this.logged = false;
        this.admin = false;
        this.userName = '';
        this.id = null;
        this.user = {};
        this.authService.clearToken();
        this.authService.emitLoginStatus(false);
        this.router.navigate(['/login']);
      },
      error: (error: any) => {
        console.error('Error during logout:', error);
      }
    });
  }
}
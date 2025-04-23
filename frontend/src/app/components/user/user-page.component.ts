import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../services/auth.service'; 
import { UserDTO } from '../../dtos/user.dto';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  user!: UserDTO;
  errorMessage: string = '';
  userId: string | null = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private router: Router,
    private authService: AuthService 
  ) { }

  ngOnInit(): void {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']);  
      return;
    }

    this.userId = this.activatedRoute.snapshot.paramMap.get('id');
    if (this.userId) {
      this.getUserInfo(this.userId); 
    }
  }

  getUserInfo(userId: string): void {
    this.userService.getUser(userId).subscribe(
      (data: UserDTO) => {
        this.user = data;
      },
      (error) => {
        this.errorMessage = 'Error fetching user data';
        console.error(error);
      }
    );
  }

  goBack() {
    this.router.navigate(['/']); 
  }

  editUser() {
    this.router.navigate([`/edit-user/${this.user.id}`]);  
  }
}

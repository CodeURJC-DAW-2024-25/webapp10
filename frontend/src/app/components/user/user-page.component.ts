import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { AuthService } from '../../services/auth.service'; 
import { UserDTO } from '../../dtos/user.dto';
import { TicketService } from '../../services/ticket.service';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html'
})
export class UserPageComponent implements OnInit {

  user!: UserDTO;
  errorMessage: string = '';
  userId: string | null = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private router: Router,
    private authService: AuthService,
    private ticketService: TicketService 
  ) { }

  ngOnInit(): void {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']);
      return;
    }

    this.userId = this.activatedRoute.snapshot.paramMap.get('id');

    if (this.userId) {
      this.userService.getCurrentUser().subscribe({
        next: (loggedUser) => {
          if (loggedUser.id !== Number(this.userId)) {
            this.router.navigate(['/error/unauthorized']);
            return;
          }

          this.getUserInfo(this.userId!);
        },
        error: (err) => {
          console.error('Error fetching logged user', err);
          this.router.navigate(['/login']);
        }
      });
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

  downloadTicket(): void {
    this.ticketService.downloadTickets().subscribe({
      next: (pdfData: Blob) => {
        const blob = new Blob([pdfData], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = 'tickets.pdf';
        link.click();
        window.URL.revokeObjectURL(url);
      },
      error: (error) => {
        console.error('Error downloading tickets', error);
      }
    });
  }
}

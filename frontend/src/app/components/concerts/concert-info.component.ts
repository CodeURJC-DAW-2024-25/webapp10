import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ConcertService } from '../../services/concert.service';
import { ArtistDTO } from '../../dtos/artist.dto';
import { ConcertDTO } from '../../dtos/concert.dto';

@Component({
  selector: 'app-concert-info',
  templateUrl: './concert-info.component.html',
})
export class ConcertInfoComponent implements OnInit {
  concert!: ConcertDTO;
  artists: ArtistDTO[] = [];
  logged: boolean = false;
  admin: boolean = false;
  id: number | null = null;
  user: any = {};
  token: string = '';

  constructor(
    private concertService: ConcertService,
    private authService: AuthService,
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const concertId = this.route.snapshot.paramMap.get('id');
    if (concertId) {
      this.loadConcertData(+concertId);
    }
    this.authService.getLoginStatus().subscribe((loggedIn) => {
      if (loggedIn) {
        this.checkUserStatus();
      } else {
        this.resetUserState();
      }
    });
  }

  checkUserStatus(): void {
    this.http.get<any>('/api/v1/users/currentUser', { withCredentials: true }).subscribe({
      next: (data) => {
        this.logged = true;
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

  private resetUserState(): void {
    this.logged = false;
    this.admin = false;
    this.id = null;
    this.user = {};
  }

  private loadConcertData(id: number): void {
    this.concertService.getConcert(id).subscribe({
      next: (concert) => {
        this.concert = concert;
        this.artists = concert.artists || [];
      },
      error: (err) => {
        console.error('Error loading concert data:', err);
      }
    });
  }

  navigateTo(path: string): void {
    this.router.navigate([path]);
  }

  deleteArtist(artistId: number): void {
    console.log('Delete artist with ID:', artistId);
    // Add logic to delete the artist
  }

  deleteConcert(concertId: number): void {
    console.log('Delete concert with ID:', concertId);
    // Add logic to delete the concert
  }
}
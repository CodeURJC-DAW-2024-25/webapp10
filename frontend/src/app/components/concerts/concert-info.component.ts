import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ConcertService } from '../../services/concert.service';
import { ArtistDTO } from '../../dtos/artist.dto';
import { ArtistService } from '../../services/artist.service';
import { ConcertDTO } from '../../dtos/concert.dto';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-concert-info',
  templateUrl: './concert-info.component.html',
})
export class ConcertInfoComponent implements OnInit {
  concert!: ConcertDTO;
  artists: ArtistDTO[] = [];
  logged: boolean = false;
  admin: boolean = false;
  userName: string = '';
  id: number | null = null;
  user: any = {};
  token: string = '';

  constructor(
    private concertService: ConcertService,
    private authService: AuthService,
    private http: HttpClient,
    private router: Router,
    private artistService: ArtistService,
    private route: ActivatedRoute
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

    this.checkUserStatus();
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
    this.artistService.deleteArtist({ id: artistId } as ArtistDTO).subscribe({
      next: () => {
        console.log(`Artist with ID ${artistId} deleted successfully.`);
        this.artists = this.artists.filter((artist) => artist.id !== artistId);
      },
      error: (err) => {
        console.error('Error deleting artist:', err);
      }
    });
  }

  deleteConcert(concertId: number): void {
    this.concertService.deleteConcert({ id: concertId } as ConcertDTO).subscribe({
      next: () => {
        console.log(`Concert with ID ${concertId} deleted successfully.`);
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error('Error deleting concert:', err);
      }
    });
  }

}
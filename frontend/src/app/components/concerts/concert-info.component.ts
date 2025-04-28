import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ConcertService } from '../../services/concert.service';
import { GraphicsService } from '../../services/graphics.service';
import { ArtistService } from '../../services/artist.service';
import { ArtistDTO } from '../../dtos/artist.dto';
import { ConcertDTO } from '../../dtos/concert.dto';
import { Chart } from 'chart.js/auto';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';


@Component({
  selector: 'app-concert-info',
  templateUrl: './concert-info.component.html'
  
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
  safeMap!: SafeHtml;

  @ViewChild('graphicPie') graphicPie!: ElementRef<HTMLCanvasElement>;
  @ViewChild('graphicBar') graphicBar!: ElementRef<HTMLCanvasElement>;
  
  constructor(
    private concertService: ConcertService,
    private authService: AuthService,
    private graphicsService: GraphicsService,
    private http: HttpClient,
    private router: Router,
    private artistService: ArtistService,
    private route: ActivatedRoute,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    const concertId = this.route.snapshot.paramMap.get('id');
    if (concertId) {
      this.loadConcertData(+concertId);
      this.loadAgeDistribution(+concertId);
      this.loadTicketsByConcert();
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
        this.safeMap = this.sanitizer.bypassSecurityTrustHtml(concert.map);
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
    console.log('Delete Artist button clicked. Artist ID:', artistId);

    if (!artistId) {
      console.error('Artist ID is undefined or null.');
      return;
    }
    this.artistService.deleteArtist(artistId).subscribe({
      next: () => {
        console.log(`Artist with ID ${artistId} deleted successfully.`);
        this.artists = this.artists.filter((artist) => artist.id !== artistId); // Remove artist from the list
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

  private loadAgeDistribution(concertId: number): void {
    this.graphicsService.getPieChartData(concertId).subscribe({
      next: (data) => {
        this.renderPieChart(data.data, data.backgroundColor, data.labels);
      },
      error: (err) => {
        console.error('Error loading age distribution data:', err);
      }
    });
  }

  private loadTicketsByConcert(): void {
    this.graphicsService.getBarChartData().subscribe({
      next: (data) => {
        this.renderBarChart(data.data, data.backgroundColor, data.labels);
      },
      error: (err) => {
        console.error('Error loading tickets by concert data:', err);
      }
    });
  }
  
  private renderPieChart(data: number[], backgroundColor: string[], labels: string[]): void {
    const ctx = this.graphicPie.nativeElement.getContext('2d');
    if (!ctx) {
      console.error('Context for Pie Chart not found!');
      return;
    }

    new Chart(ctx, {
      type: 'pie',
      data: {
        labels: labels,
        datasets: [
          {
            data: data,
            backgroundColor: backgroundColor,
          },
        ],
      },
    });
  }
  
  private renderBarChart(data: number[], backgroundColor: string[], labels: string[]): void {
    const ctx = this.graphicBar.nativeElement.getContext('2d');
    if (!ctx) {
      console.error('Context for Bar Chart not found!');
      return;
    }

    new Chart(ctx, {
      type: 'bar',
      data: {
        labels: labels,
        datasets: [
          {
            label: 'Tickets Sold',
            data: data,
            backgroundColor: backgroundColor,
          },
        ],
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });
  } 
}
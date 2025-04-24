import { Component, TemplateRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ArtistService } from '../../services/artist.service';
import { ArtistDTO } from '../../dtos/artist.dto';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-artist-form',
  templateUrl: './artist-form.component.html',
})
export class ArtistFormComponent {
  //users info
  logged: boolean = false;
  admin: boolean = false;
  userName: string = '';
  id: number | null = null;
  user: any = {};
  token: string = '';

  //artist info
  public newArtist: boolean = false;
  public artist!: ArtistDTO;
  public messageError: string = '';
  public messageErrorModal!: TemplateRef<any>;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private http: HttpClient,
    private artistService: ArtistService,
    private modalService: NgbModal,
    private authService: AuthService
  ) {
 
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']); 
      return;
    }
    const id = activatedRoute.snapshot.params['id'];

    if (id) {
      this.artistService.getArtist(id).subscribe(
        (artist) => {
          this.artist = artist;
        },
        (error) => console.error(error)
      );
      this.newArtist = false;
    } else {
      this.artist = {
        artistName: '',
        musicalStyle: '',
        artistInfo: '',
      };
      this.newArtist = true;
    }
  }

  ngOnInit(): void {
    this.authService.getLoginStatus().subscribe((loggedIn) => {
      if (loggedIn) {
        this.checkUserStatus();
      } else {
        this.logged = false;
        this.userName = '';
        this.id = null;
        this.user = {};
      }
    });
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
        this.logged = false;
        this.admin = false;
        this.userName = '';
        this.id = null;
        this.user = {};
      }
    });
  }

  public cancel(): void {
      this.router.navigate(['/']);
  }

  public save(): void {
    if (this.logged && this.admin) {
      this.artistService.createOrReplaceArtist(this.artist).subscribe(
      (artist: ArtistDTO) => this.afterSave(artist),
      (error) => {
        this.messageError = 'Error saving artist: ' + error;
        this.modalService.open(this.messageErrorModal, { centered: true });
      }
    );
    } else {
      this.messageError = 'You do not have permission to perform this action.';
      this.modalService.open(this.messageErrorModal, { centered: true });
    }  
  }

  private afterSave(artist: ArtistDTO): void {
    this.router.navigate(['/']);
  }
}
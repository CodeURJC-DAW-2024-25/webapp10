import { Component, TemplateRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ArtistService } from '../../services/artist.service';
import { ArtistDTO } from '../../dtos/artist.dto';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-artist-form',
  templateUrl: './artist-form.component.html',
})
export class ArtistFormComponent {
  logged: boolean = false;
  admin: boolean = false;
  userName: string = '';
  id: number | null = null;
  user: any = {};
  token: string = '';

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
    private authService: AuthService,
    private userService: UserService
    
  ) {}

  ngOnInit(): void {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']);
      return;
    }

    this.userService.getCurrentUser().subscribe({
      next: (loggedUser) => {
        if (!loggedUser.roles.includes('ADMIN')) {
          this.router.navigate(['/error/unauthorized']);
          return;
        }

        const id = this.activatedRoute.snapshot.paramMap.get('id');

        if (id) {
          this.artistService.getArtist(id).subscribe(
            (artist) => {
              this.artist = artist;
              this.newArtist = false;
            },
            (error) => console.error(error)
          );
        } else {
          this.artist = {
            artistName: '',
            musicalStyle: '',
            artistInfo: '',
          };
          this.newArtist = true;
        }
      },
      error: () => {
        this.router.navigate(['/login']);
      }
    });
  }

  public cancel(): void {
    this.router.navigate(['/']);
  }

  public save(): void {
    this.artistService.createOrReplaceArtist(this.artist).subscribe(
      (artist: ArtistDTO) => this.afterSave(artist),
      (error) => {
        this.messageError = 'Error saving artist: ' + error;
        this.modalService.open(this.messageErrorModal, { centered: true });
      }
    );
  }

  private afterSave(artist: ArtistDTO): void {
    this.router.navigate(['/']);
  }
}

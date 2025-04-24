import { Component, ElementRef, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConcertService } from '../../services/concert.service';
import { ConcertDTO } from '../../dtos/concert.dto';
import { ArtistDTO } from '../../dtos/artist.dto';
import { ArtistService } from '../../services/artist.service';
import { getConcertImage } from '../../utils/concert-utils';

@Component({
  selector: 'app-concert-form',
  templateUrl: './concert-form.component.html',
})
export class ConcertFormComponent {
  public newConcert: boolean;
  public concert!: ConcertDTO;
  public artists: ArtistDTO[] = [];
  public selectedArtists: number[] = [];
  public removeImage: boolean = false;
  public messageError: string = '';

  @ViewChild('file')
  public file!: ElementRef;
  @ViewChild('messageErrorModal')
  public messageErrorModal!: TemplateRef<void>;
  
  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private concertService: ConcertService,
    private artistService: ArtistService,
    private modalService: NgbModal
  ) {
    const id = activatedRoute.snapshot.params['id'];

    if (id) {
      this.concertService.getConcert(id).subscribe(
        (concert) => {
          this.concert = concert;
          this.selectedArtists = this.concert.artists
            .map((artist) => artist.id)
            .filter((id): id is number => id !== undefined);
        },
        (error) => console.error(error)
      );
      this.newConcert = false;
    } else {
      this.concert = {
        concertName: '',
        concertDetails: '',
        concertDate: '',
        concertTime: '',
        location: '',
        stadiumPrice: 0,
        trackPrice: 0,
        concertImage: false,
        map: '',
        color: '',
        artists: [],
        tickets: []
      };
      this.newConcert = true;
    }
  }

  ngOnInit(): void {
    this.artistService.getArtists().subscribe(
      (artists) => (this.artists = artists),
      (error) => console.error(error)
    );
  }

  public cancel(): void {
    if (this.newConcert) {
      this.router.navigate(['/']);
    } else {
      this.router.navigate(['/', this.concert.id]);
    }
  }

  public save(): void {
    this.concertService.createOrReplaceConcert(this.concert).subscribe(
      (concert: ConcertDTO) => this.uploadImage(concert),
      (error) => {
        this.messageError = 'Error saving concert: ' + error;
        this.modalService.open(this.messageErrorModal, { centered: true });
      }
    );
  }

  private uploadImage(concert: ConcertDTO): void {
    const image = this.file.nativeElement.files[0];

    if (image) {
      const formData = new FormData();
      formData.append('imageFile', image);

      this.concertService.createOrReplaceConcertImage(concert, formData).subscribe(
        (_) => this.afterUploadImage(concert),
        (error) => {
          this.messageError = 'Error uploading concert image: ' + error;
          this.modalService.open(this.messageErrorModal, { centered: true });
        }
      );
    } else if (this.removeImage) {
      this.concertService.deleteConcertImage(concert).subscribe(
        (_) => this.afterUploadImage(concert),
        (error) => {
          this.messageError = 'Error deleting concert image: ' + error;
          this.modalService.open(this.messageErrorModal, { centered: true });
        }
      );
    } else {
      this.afterUploadImage(concert);
    }
  }

  private afterUploadImage(concert: ConcertDTO): void {
    this.router.navigate(['/concerts', concert.id]);
  }

  public concertImage(): string {
    return getConcertImage(this.concert);
  }
}
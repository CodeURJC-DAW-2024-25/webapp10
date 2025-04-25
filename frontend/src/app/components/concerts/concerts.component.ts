import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from "@angular/router";
import { ConcertService } from "./../../services/concert.service";
import { ConcertDTO } from "../../dtos/concert.dto";

@Component({
  selector: 'app-concerts',
  templateUrl: './concerts.component.html',
  styleUrls: ['./concerts.component.css'],
  providers: [ConcertService]
})
export class ConcertsComponent implements OnInit {
  concerts: ConcertDTO[] = [];
  page: number = 0;
  size: number = 10;
  loading: boolean = false;
  successMessage: string | null = null;
  errorMessage: string | null = null;
  hasMore: boolean = true;

  constructor(
    private http: HttpClient,
    private router: Router,
    private concertService: ConcertService
  ) {}

  public ngOnInit() {
    this.loadConcerts();
  }

  loadConcerts() {
    this.loading = true;
    this.concertService.getConcerts(this.page, this.size).subscribe(
      (concerts) => {
        if (concerts.length > 0) {
          this.concerts = this.concerts.concat(concerts); // Añadir los nuevos conciertos
          this.page++; // Incrementar la página para la siguiente carga
        } else {
          this.hasMore = false; // No hay más conciertos para cargar
        }
        this.loading = false;
      },
      (error) => {
        console.log(error);
        this.errorMessage = "Error al cargar conciertos.";
        this.loading = false;
      }
    );
  }

  loadMore() {
    if (this.hasMore) {
      this.loadConcerts(); // Cargar más conciertos
    }
}
}
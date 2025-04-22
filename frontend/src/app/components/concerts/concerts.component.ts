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

  constructor(
    private http: HttpClient,
    private router: Router,
    private concertService: ConcertService
  ) {}

  public ngOnInit() {
    this.concertService.getConcerts().subscribe(
      (concerts) => (this.concerts = concerts),
      (error) => console.log(error)
    );
  }
}
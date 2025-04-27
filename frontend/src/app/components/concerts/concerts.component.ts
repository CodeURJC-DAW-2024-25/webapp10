import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from "@angular/router";
import { ConcertService } from "./../../services/concert.service";
import { ConcertDTO } from "../../dtos/concert.dto";
import { UserService } from '../../services/user.service';
import { UserDTO } from '../../dtos/user.dto';

@Component({
  selector: 'app-concerts',
  templateUrl: './concerts.component.html',
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
  userId: number | null = null;

  constructor(
    private http: HttpClient,
    private router: Router,
    private concertService: ConcertService,
    private userService: UserService
  ) {}

  public ngOnInit() {
    this.getCurrentUserId(); 
  }

  private getCurrentUserId(): void {
    this.userService.getCurrentUser().subscribe({
      next: (user: UserDTO) => {
        this.userId = user.id ?? null; 
        this.loadConcerts(); 
      },
      error: () => {
        this.userId = null; 
        this.loadConcerts(); 
      }
    });
  }

  loadConcerts() {
    this.loading = true;
    this.concertService.getConcerts(this.userId,this.page, this.size).subscribe(
      (concerts) => {
        if (concerts.length > 0) {
          this.concerts = this.concerts.concat(concerts); 
          this.page++; 
        } else {
          this.hasMore = false;
        }
        this.loading = false;
      },
      (error) => {
        console.log(error);
        this.errorMessage = "Error loading concerts.";
        this.loading = false;
      }
    );
  }

  loadMore() {
    if (this.hasMore) {
      this.loadConcerts();
    }
}
}
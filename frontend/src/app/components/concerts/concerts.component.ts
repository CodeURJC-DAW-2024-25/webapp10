import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-concerts',
  templateUrl: './concerts.component.html',
  styleUrls: ['./concerts.component.css']
})
export class ConcertsComponent implements OnInit {
  concerts: any[] = [];
  page: number = 0;
  size: number = 10;
  loading: boolean = false;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadMore();
  }

  loadMore(): void {
    this.loading = true;
    this.http.get<any[]>(`/api/v1/concerts?page=${this.page}&size=${this.size}`).subscribe({
      next: (data) => {
        this.concerts = [...this.concerts, ...data];
        this.page += 1;
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'Failed to load concerts.';
        this.loading = false;
      }
    });
  }
}
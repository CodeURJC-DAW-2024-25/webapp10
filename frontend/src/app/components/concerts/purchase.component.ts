import { Component, ElementRef, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from '../../services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UserDTO } from '../../dtos/user.dto';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConcertDTO } from '../../dtos/concert.dto';
import { ConcertService } from '../../services/concert.service';
import { AuthService } from '../../services/auth.service';
import { TicketDTO } from '../../dtos/ticket.dto';
import { TicketService } from '../../services/ticket.service';

import { getConcertImage } from '../../utils/concert-utils';

@Component({
  selector: 'app-purchase',
  templateUrl: './purchase.component.html'
})
export class PurchaseComponent implements OnInit {

  public user!: UserDTO;
  public users!: UserDTO[];
  public concert!: ConcertDTO;
  public userId!: number;
  public ticket!: TicketDTO;

  @ViewChild("messageErrorModal")
  public messageErrorModal!: TemplateRef<void>;

  public removeImage!: boolean;
  public messageError!: string;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private concertService: ConcertService,
    private ticketService: TicketService,
    private modalService: NgbModal,
    private authService: AuthService,
  ) {}

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params["id"];
 

    if (id) {
      this.ticket = {
    ticketType: "",
    prices: 0,
    userOwnerId: 0,
    numTickets: 0,
    concertId: 0
      };
      this.concertService.getConcert(id).subscribe({
        next: (concert) => {
          this.concert = concert;
        
          this.userService.getCurrentUser().subscribe({
            next: (user: UserDTO) => {
              this.user = user;
          
            },
            error: (error) => console.error(error)
          });
        },
        error: (error) => console.error(error)
      });
    }
  }

  public goBack() {
    this.router.navigate(["/"]);
  }

  public purchaseTickets(): void {

   
    this.ticket.concertId= this.concert.id

    this.ticket.userOwnerId= this.user.id



    this.ticketService.createTicket(this.ticket).subscribe({
      next: (ticket: TicketDTO) =>{ this.ticket = ticket
        
        this.user.tickets.push(ticket)
        this.user.numTicketsBought=ticket.numTickets
 
        this.userService.createOrReplaceUser(this.user).subscribe({
          next: (updatedUser: UserDTO) => {
       
            this.user = updatedUser;
          this.router.navigate(['/user-page/'+this.user.id]);},
          error: (error) => {
            this.messageError = 'Error saving user: ' + error;
            this.modalService.open(this.messageErrorModal, { centered: true });
          }
        });
      },
      error: (error) => {
        this.messageError = 'Error saving ticket: ' + error;
        this.modalService.open(this.messageErrorModal, { centered: true });
      }
    });

    
  }

  public concertImage(): string {
      return getConcertImage(this.concert);
    }


}
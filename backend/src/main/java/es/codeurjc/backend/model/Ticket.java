package es.codeurjc.backend.model;

import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Ticket {

    public enum TicketType{
        Stadium_Stand,
        Concert_Track
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private TicketType ticketType;
    private Integer prices;
    @ManyToOne
    private User userOwner;
    
    private int numTickets;

    @ManyToOne
    private Concert concert;

    public Ticket(){}

    public Ticket(Concert concert, TicketType ticketType, Integer prices, User userOwner) {
        this.concert = concert;
        this.ticketType = ticketType;
        this.prices = prices;
        this.userOwner = userOwner;
    }

    public Concert getConcert() {
        return concert;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Integer getPrices() {
        return prices;
    }

    public void setPrices(Integer prices) {
        this.prices = prices;
    }

   /*  public Integer getPriceForType(TicketType type) {
        return prices.get(type);
    } */

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    public void setNumTickets(int numTickets){
        this.numTickets = numTickets;
    }

    public int getNumTickets(){
        return this.numTickets;
    }
}
package es.codeurjc.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ticketType;
    private Integer prices;
    @ManyToOne
    private User userOwner;
    
    private Integer numTickets;

    @ManyToOne
    private Concert concert;

    public Ticket(){}

    public Ticket(Concert concert, String ticketType, Integer prices, User userOwner, Integer numTickets) {
        this.concert = concert;
        this.ticketType= ticketType;
        this.prices = prices;
        this.userOwner = userOwner;
        this.numTickets = numTickets;
    }

    public Concert getConcert() {
        return concert;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType= ticketType;
    }

    public Integer getPrices() {
        return prices;
    }

    public void setPrices(Integer prices) {
        this.prices = prices;
    }

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
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
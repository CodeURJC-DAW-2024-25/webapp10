package es.codeurjc.model;

import java.util.Map;

public class Ticket {

    public enum TicketType{
        Stadium_Stand,
        Concert_Track
    }

    private Concert concert;
    private TicketType ticketType;
    private Map<TicketType,Integer> prices;
    private User userOwner;
    private int numTickets;

    public Ticket(){}

    public Ticket(Concert concert, TicketType ticketTyp, Map<TicketType,Integer> prices, User userOwner) {
        this.concert = concert;
        this.ticketType = ticketType;
        this.prices=prices;
        this.userOwner=userOwner;
        
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

    public Map<TicketType, Integer> getPrices() {
        return prices;
    }

    public void setPrices(Map<TicketType, Integer> prices) {
        this.prices = prices;
    }

    public Integer getPriceForType(TicketType type) {
        return prices.get(type);
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    public void setNumTickets(int numTickets){
        this.numTickets=numTickets;
    }

    public int getNumTickets(){
        return this.numTickets;
    }

}

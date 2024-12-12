package database.EventsHub;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class EventsHub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private String eventHost;

    @Column(nullable = false)
    private String eventDate;

    @Column
    private String eventLocation;

    @Column(nullable = false)
    private int eventThumbnail;

    @Column(nullable = false)
    private int eventCost;





    // Constructors
    public EventsHub() {}

    public EventsHub(String eventName, String eventHost, String eventDate, String eventLocation, int eventThumbnail, int eventCost) {
        this.eventName = eventName;
        this.eventHost = eventHost;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventThumbnail = eventThumbnail;
        this.eventCost = eventCost;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String title) {
        this.eventName = title;
    }

    public String getEventHost() {
        return eventHost;
    }

    public void setEventHost(String description) {
        this.eventHost = description;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String date) {
        this.eventDate = date;
    }

public String getEventLocation() {return eventLocation;}

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }


    public int getEventThumbnail() {return eventThumbnail;}

    public void setEventThumbnail(int eventThumbnail) {this.eventThumbnail = eventThumbnail;}


    public int getEventCost() {return eventCost;}

    public void setEventCost(int eventCost) {this.eventCost = eventCost;}
}

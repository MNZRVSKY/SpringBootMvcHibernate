package com.epam.learn.storage;

import com.epam.learn.model.TicketModel;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
@DynamicUpdate
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity user;

    @Column(name = "ticket_category")
    private int category;

    @Column(name = "place")
    private int place;

    public TicketEntity() {
        this.id = -1;
        this.category = -1;
        this.place = -1;
        this.event = new EventEntity();
        this.user = new UserEntity();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TicketModel.Category getCategory() { return TicketModel.Category.convert(category); }

    public void setCategory(TicketModel.Category category) {
        this.category = category.getValue();
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

}

package com.epam.learn.model;


public class TicketModel {

    public enum Category {STANDARD(1), PREMIUM(2), BAR(3);

        private final int value;

        private Category(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Category convert(int x) {
            switch(x) {
                case 1:
                    return STANDARD;
                case 2:
                    return PREMIUM;
                case 3:
                    return BAR;
            }
            return STANDARD;
        }
    }

    private int id;
    private int eventId;
    private int userId;
    private Category category;
    private int place;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}

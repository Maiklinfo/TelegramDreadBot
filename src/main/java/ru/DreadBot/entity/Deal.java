package ru.DreadBot.entity;

public class Deal {
    private Integer id;
    private String date;
    private String description;

    public Deal(Integer id, String date, String description) {
        this.id = id;
        this.date = date;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

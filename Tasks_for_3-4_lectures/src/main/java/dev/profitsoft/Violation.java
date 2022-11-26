package dev.profitsoft;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.LocalDateTime;

public class Violation {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSetter("date_time")
    private LocalDateTime dateTime;
    @JsonSetter("first_name")
    private String firstName;
    @JsonSetter("last_name")
    private String lastName;
    private String type;
    @JsonSetter("fine_amount")
    private double fineAmount;

    public Violation() {
    }

    public Violation(LocalDateTime dateTime, String firstName, String lastName, String type, double fineAmount) {
        this.dateTime = dateTime;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.fineAmount = fineAmount;
    }

    public LocalDateTime getDate_time() {
        return dateTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getType() {
        return type;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "dateTime=" + dateTime +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", type='" + type + '\'' +
                ", fineAmount=" + fineAmount +
                '}';
    }
}

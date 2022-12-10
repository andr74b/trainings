package dev.profitsoft.Task2.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class NotAnnotatedClass {
    private String stringProperty;
    private int numberProperty;
    private Instant timeProperty;
}

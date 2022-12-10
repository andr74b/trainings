package dev.profitsoft.Task2.model;

import dev.profitsoft.Task2.annotation.Property;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class AnnotatedClass {

    private String stringProperty;

    @Property(name = "numberProperty")
    private int number;

    @Property(format = "dd.MM.yyyy HH:mm")
    private Instant timeProperty;
}

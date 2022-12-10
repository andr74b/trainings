package dev.profitsoft.Task2.model;

import dev.profitsoft.Task2.annotation.Property;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class MyClass {
    private String stringProperty;

    @Property(name="numberProperty")
    private int myNumber;

    @Property(format="dd.MM.yyyy HH:mm")
    private Instant timeProperty;
}

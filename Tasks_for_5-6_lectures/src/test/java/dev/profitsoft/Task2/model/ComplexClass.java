package dev.profitsoft.Task2.model;

import dev.profitsoft.Task2.annotation.Property;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplexClass {

    @Property(name = "property.int")
    private int property;

    @Property(name = "numberProperty")
    private Integer integer;

    @Property(name = "number")
    private String string;
}

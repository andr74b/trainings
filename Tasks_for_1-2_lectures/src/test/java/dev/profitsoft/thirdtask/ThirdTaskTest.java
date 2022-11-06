package dev.profitsoft.thirdtask;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ThirdTaskTest {

    @Test
    void shouldSortFigures() {
        String expected = "[250.0, 30.0, 25.0]";

        Figure cube = new Cube(25.0);
        Figure sphere = new Sphere(30.0);
        Figure cylinder = new Cylinder(250.0);
        List<Figure> figures = new ArrayList<>();
        figures.add(cube);
        figures.add(sphere);
        figures.add(cylinder);

        ThirdTask.sortFigures(figures);
        assertEquals(expected, figures.toString());
    }
}
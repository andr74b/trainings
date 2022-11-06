package dev.profitsoft.thirdtask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ThirdTask {
    public static void sortFigures(List<Figure> figures) {
        figures.sort(Collections.reverseOrder(Comparator.comparingDouble(Figure::getVolume)));
    }
}

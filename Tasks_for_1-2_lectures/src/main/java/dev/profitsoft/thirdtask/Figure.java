package dev.profitsoft.thirdtask;

public abstract class Figure {

    private final double volume;

    public Figure(double volume) {
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return String.valueOf(volume);
    }
}

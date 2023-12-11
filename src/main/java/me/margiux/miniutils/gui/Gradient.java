package me.margiux.miniutils.gui;

public class Gradient extends Color {
    public enum GradientType {
        Horizontal,
        Vertical
    }
    public GradientType type;
    public int endColor;
    public Gradient(int startColor, int endColor) {
        this(startColor, endColor, GradientType.Horizontal);
    }

    public Gradient(int startColor, int endColor, GradientType type) {
        super(startColor);
        this.endColor = endColor;
        this.type = type;
    }
}

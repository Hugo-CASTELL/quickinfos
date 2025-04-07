package dev.quickinfos.screen;

public class Dimension {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Dimension(int x, int y) {
        this(x, y, 0, 0);
    }

    public Dimension(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}

package zad1;

public class Field {
    private boolean is_empty;
    private int x;
    private int y;

    public Field(boolean is_empty, int x, int y) {
        this.is_empty = is_empty;
        this.x = x;
        this.y = y;
    }

    public boolean is_empty() {
        return is_empty;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

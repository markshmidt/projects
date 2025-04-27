package Board;

public enum Cell {
    BLACK(1),
    WHITE(-1),
    EMPTY(0);

    private final int value;

    Cell(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return switch (this) {
            case BLACK -> "B";
            case WHITE -> "W";
            default -> "";
        };
    }
}

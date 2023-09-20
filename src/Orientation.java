public enum Orientation {
    None(-1),
    Horizontal(0),
    Vertical(1);

    private final int value;

    private Orientation(int value ) {
        this.value = value;
    }
}

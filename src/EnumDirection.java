public enum EnumDirection {
    DOWN,
    LEFT,
    UP,
    RIGHT;

    public EnumDirection getOposedDirection() {
        return (this == DOWN) ? UP : (this == UP) ? DOWN : (this == LEFT) ? RIGHT : LEFT;
    }
}
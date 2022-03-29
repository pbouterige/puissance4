import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum EnumColor {
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    BLUE("\033[0;34m"),
    PURPLE("\033[0;35m"),
    CYAN("\033[0;36m"),
    YELLOW("\033[0;33m"),
    WHITE("\033[0;37m"),
    EMPTY("\033[0m");

    private static final List<EnumColor> VALUES;
    static {
        VALUES = new ArrayList<EnumColor>();
        for (EnumColor jeton : EnumColor.values()) {
            if (jeton != EnumColor.EMPTY) {
                VALUES.add(jeton);
            }
        }
    }
    public static List<EnumColor> getValues() {
        return Collections.unmodifiableList(VALUES);
    }

    private final String color;
    public static final String RESET = "\033[0m";

    private EnumColor(String colorString) {
        this.color = colorString;
    }

    public String getColor() {
        return this.color;
    }

    public static EnumColor intToColor(int a) {
        switch (a) {
            case 1:
                return RED;
            case 2:
                return BLUE;
            case 3:
                return GREEN;
            case 4:
                return YELLOW;
            case 5:
                return WHITE;
            case 6:
                return PURPLE;
            case 7:
                return CYAN;
            default:
                return EMPTY;
        }
    }

    public static int colorToInt(EnumColor a) {
        switch (a) {
            case RED:
                return 1;
            case BLUE:
                return 2;
            case GREEN:
                return 3;
            case YELLOW:
                return 4;
            case WHITE:
                return 5;
            case PURPLE:
                return 6;
            case CYAN:
                return 7;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        return this.getColor();
    }
}

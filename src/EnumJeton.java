import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public enum EnumJeton {
    ROUGE("\033[0;31m"),
    VERT("\033[0;32m"),
    BLEU("\033[0;34m"),
    VIOLET("\033[0;35m"),
    CYAN("\033[0;36m"),
    JAUNE("\033[0;33m"),
    BLANC("\033[0;37m"),
    VIDE("\033[0m");

    private static final List<EnumJeton> VALUES;

    private final String color;
    private final String RESET = "\033[0m";

    private EnumJeton(String colorString) {
        this.color = colorString;
    }

    public String getColor() {
        return this.color;
    }

    public static EnumJeton intToEquipe(int a) {
        switch (a) {
            case 1:
                return ROUGE;
            case 2:
                return BLEU;
            case 3:
                return VERT;
            case 4:
                return JAUNE;
            case 5:
                return BLANC;
            case 6:
                return VIOLET;
            case 7:
                return CYAN;
            default:
                return VIDE;
        }
    }

    public static int equipeToInt(EnumJeton a) {
        switch (a) {
            case ROUGE:
                return 1;
            case BLEU:
                return 2;
            case VERT:
                return 3;
            case JAUNE:
                return 4;
            case BLANC:
                return 5;
            case VIOLET:
                return 6;
            case CYAN:
                return 7;
            default:
                return 0;
        }
    }

    public static EnumJeton stringToEquipe(String a) {
        switch (a) {
            case "\033[0;31m":
                return ROUGE;
            case "\033[0;32m":
                return BLEU;
            case "\033[0;34m":
                return VERT;
            case "\033[0;35m":
                return JAUNE;
            case "\033[0;36m":
                return BLANC;
            case "\033[0;33m":
                return VIOLET;
            case "\033[0;37m":
                return CYAN;
            default:
                System.err.println("Caca");
                return VIDE;
        }
    }

    public static Set<EnumJeton> getColorSet() {
        Set<EnumJeton> colorSet = EnumSet.allOf(EnumJeton.class);
        colorSet.remove(EnumJeton.VIDE);
        return colorSet;
    }

    static {
        VALUES = new ArrayList<EnumJeton>();
        for (EnumJeton jeton : EnumJeton.values()) {
            if (jeton != EnumJeton.VIDE) {
                VALUES.add(jeton);
            }
        }
    }

    public static List<EnumJeton> getValues() {
        return Collections.unmodifiableList(VALUES);
    }

    @Override
    public String toString() {
        if (this == VIDE) {
            return "○";
        }
        return this.getColor() + "●" + this.RESET;
    }
}

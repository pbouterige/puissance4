public enum EnumJeton {
    ROUGE("\033[0;31m"),
    VERT("\033[0;32m"),
    BLEU("\033[0;34m"),
    VIOLET("\033[0;35m"),
    CYAN("\033[0;36m"),
    JAUNE("\033[0;33m"),
    WHITE("\033[0;37m"),
    VIDE("\033[0m");
    
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
                return WHITE;
            case 6:
                return VIOLET;
            case 7:
                return CYAN;
            default:
                return VIDE;
        }
    }

    @Override
    public String toString() {
        if (this == VIDE) {
            return "○";
        }
        return this.getColor() + "●" + this.RESET;
    }
}

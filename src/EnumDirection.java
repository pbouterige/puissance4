public enum EnumDirection {
    BAS,
    GAUCHE,
    HAUT,
    DROITE;

    public EnumDirection getOposeDirection() {
        return (this == BAS) ? HAUT : (this == HAUT) ? BAS : (this == GAUCHE) ? DROITE : GAUCHE;
    }
}
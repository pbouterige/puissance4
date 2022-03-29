
public class Joueur {
    private String name;
    private EnumJeton couleurEquipe;

    Joueur(String name, EnumJeton couleurEquipe) {
        this.name = name;
        this.couleurEquipe = couleurEquipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumJeton getCouleurEquipe() {
        return couleurEquipe;
    }

    public void setCouleurEquipe(EnumJeton couleurEquipe) {
        this.couleurEquipe = couleurEquipe;
    }

    public String getDataToSave() {
        return String.format("%s %s\n", getName(), getCouleurEquipe());
    }
}

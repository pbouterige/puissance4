import java.util.Optional;
import java.util.Arrays;

public class Grille {
    public final int NUMBER_OF_COLUMNS = 7;
    public final int NUMBER_OF_ROWS = 6;
    private Cellule[] indexTab;
    private Cellule firstCell;
    private Cellule upLeft;

    public Cellule[] getIndexTab() {
        return indexTab;
    }

    public Cellule getFirstCell() {
        return firstCell;
    }

    public Cellule getUpLeft() {
        return upLeft;
    }

    public Grille() {
        Cellule[] tmp = new Cellule[NUMBER_OF_COLUMNS];
        this.indexTab = new Cellule[NUMBER_OF_COLUMNS];
        Arrays.fill(tmp, null);

        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            Cellule prevLeft = null;
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                Cellule cell = new Cellule();
                cell.link(tmp[j], EnumDirection.BAS);
                cell.link(prevLeft, EnumDirection.GAUCHE);
                cell.link(null, EnumDirection.HAUT);
                cell.link(null, EnumDirection.DROITE);
                prevLeft = cell;
                tmp[j] = cell;
                if (i == 0) {
                    this.indexTab[j] = cell;
                    if (j == 0) {
                        this.firstCell = cell;
                    }
                }
                if (i == this.NUMBER_OF_ROWS-1 && j == 0) {
                    this.upLeft = cell;
                }
            }
        }
    }

    private String getLineString(Cellule c) {
        String line = new String();
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            line += c.getCouleur() + ((i != NUMBER_OF_COLUMNS - 1) ? " " : "");
            Optional<Cellule> tmp2 = c.getVoisin(EnumDirection.DROITE);
            if (tmp2.isPresent()) {
                c = tmp2.get();
            }
        }
        return line;
    }

    public String getGridString() {
        Cellule tmp = this.upLeft;
        String grid = new String();
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            grid += getLineString(tmp) + "\n";
            Optional<Cellule> tmp2 = tmp.getVoisin(EnumDirection.BAS);
            if (tmp2.isPresent()) {
                tmp = tmp2.get();
            }
        }
        return grid;
    }

    public boolean update(EnumJeton e, int choix) {
        indexTab[choix - 1].setCouleur(e);
        Optional<Cellule> tmp2 = indexTab[choix - 1].getVoisin(EnumDirection.HAUT);
        boolean win = indexTab[choix - 1].check();
        if (tmp2.isPresent())
            indexTab[choix - 1] = tmp2.get();
        return win;
    }

    public boolean column_full(int i) {
        return indexTab[i - 1] == null;
    }

}

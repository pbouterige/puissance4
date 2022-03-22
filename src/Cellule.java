import java.util.EnumMap;
import java.util.Optional;

public class Cellule {
    private EnumJeton couleur;
    private EnumMap<EnumDirection, Cellule> voisins;

    public EnumJeton getCouleur() {
        return this.couleur;
    }

    public void setCouleur(EnumJeton couleur) {
        if (this.couleur == EnumJeton.VIDE) {
            this.couleur = couleur;
        }
    }

    public Cellule() {
        this.couleur = EnumJeton.VIDE;
        this.voisins = new EnumMap<>(EnumDirection.class);
        this.voisins.put(EnumDirection.BAS, null);
        this.voisins.put(EnumDirection.HAUT, null);
        this.voisins.put(EnumDirection.GAUCHE, null);
        this.voisins.put(EnumDirection.DROITE, null);
    }

    public Optional<Cellule> getVoisin(EnumDirection dir) {
        Cellule next = voisins.get(dir);
        if (next == null) {
            return Optional.empty();
        }
        return Optional.of(next);
    }

    public void link(Cellule otherCell, EnumDirection dir) {
        this.voisins.put(dir, otherCell);
        if (otherCell != null) {
            otherCell.voisins.put(dir.getOposeDirection(), this);
        }
    }

    public boolean check() {
        if (countCells(this, EnumDirection.DROITE) + countCells(this, EnumDirection.GAUCHE) > 2) {
            return true;
        }
        else if (countCells(this, EnumDirection.BAS) > 2) {
            return true;
        }
        else if (countCells(this, EnumDirection.HAUT, EnumDirection.DROITE) + countCells(this, EnumDirection.BAS, EnumDirection.GAUCHE) > 2) {
            return true;
        }
        else if (countCells(this, EnumDirection.BAS, EnumDirection.DROITE) + countCells(this, EnumDirection.HAUT, EnumDirection.GAUCHE) > 2) {
            return true;
        }
        return false;
    }

    private int countCells(Cellule cell,  EnumDirection direction) {
        Optional<Cellule> optCell = cell.getVoisin(direction);
        if (optCell.isPresent()) {
            if (optCell.get().getCouleur() == cell.getCouleur()) {
                return countCells(optCell.get(), direction) + 1;
            }
            return 0;
        }
        return 0;
    }

    private int countCells(Cellule cell, EnumDirection direction1, EnumDirection direction2) {
        Optional<Cellule> optCell1 = cell.getVoisin(direction1);
        if (optCell1.isPresent()) {
            Cellule cell1 = optCell1.get();
            Optional<Cellule> optCell2 = cell1.getVoisin(direction2);
            if (optCell2.isPresent() && optCell2.get().getCouleur() == cell.getCouleur()) {
                return countCells(optCell2.get(), direction1, direction2) + 1;
            }
            return 0;
        }
        return 0;
    }
}
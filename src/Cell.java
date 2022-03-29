import java.util.EnumMap;
import java.util.Optional;

public class Cell {
    private EnumColor color;
    private EnumMap<EnumDirection, Cell> neighbor;

    public EnumColor getColor() {
        return this.color;
    }

    public void setColor(EnumColor color) {
        if (this.color == EnumColor.EMPTY) {
            this.color = color;
        }
    }

    public Cell() {
        this.color = EnumColor.EMPTY;
        this.neighbor = new EnumMap<>(EnumDirection.class);
        this.neighbor.put(EnumDirection.DOWN, null);
        this.neighbor.put(EnumDirection.UP, null);
        this.neighbor.put(EnumDirection.LEFT, null);
        this.neighbor.put(EnumDirection.RIGHT, null);
    }

    public Optional<Cell> getNeighbor(EnumDirection dir) {
        Cell next = neighbor.get(dir);
        if (next == null) {
            return Optional.empty();
        }
        return Optional.of(next);
    }

    public void link(Cell otherCell, EnumDirection dir) {
        this.neighbor.put(dir, otherCell);
        if (otherCell != null) {
            otherCell.neighbor.put(dir.getOposedDirection(), this);
        }
    }

    public boolean check() {
        if (countCellsAligned(this, EnumDirection.RIGHT) + countCellsAligned(this, EnumDirection.LEFT) > 2) {
            return true;
        }
        else if (countCellsAligned(this, EnumDirection.DOWN) > 2) {
            return true;
        }
        else if (countCellsAligned(this, EnumDirection.UP, EnumDirection.RIGHT) + countCellsAligned(this, EnumDirection.DOWN, EnumDirection.LEFT) > 2) {
            return true;
        }
        else if (countCellsAligned(this, EnumDirection.DOWN, EnumDirection.RIGHT) + countCellsAligned(this, EnumDirection.UP, EnumDirection.LEFT) > 2) {
            return true;
        }
        return false;
    }

    private int countCellsAligned(Cell cell,  EnumDirection direction) {
        Optional<Cell> optCell = cell.getNeighbor(direction);
        if (optCell.isPresent()) {
            if (optCell.get().getColor() == cell.getColor()) {
                return countCellsAligned(optCell.get(), direction) + 1;
            }
            return 0;
        }
        return 0;
    }

    private int countCellsAligned(Cell cell, EnumDirection direction1, EnumDirection direction2) {
        Optional<Cell> optCell1 = cell.getNeighbor(direction1);
        if (optCell1.isPresent()) {
            Cell cell1 = optCell1.get();
            Optional<Cell> optCell2 = cell1.getNeighbor(direction2);
            if (optCell2.isPresent() && optCell2.get().getColor() == cell.getColor()) {
                return countCellsAligned(optCell2.get(), direction1, direction2) + 1;
            }
            return 0;
        }
        return 0;
    }

    @Override
    public String toString() {
        if (this.getColor() == EnumColor.EMPTY) {
            return "○";
        }
        return this.getColor() + "●" + EnumColor.RESET;
    }
}
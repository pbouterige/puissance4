import java.util.Optional;
import java.util.Arrays;

public class Grid {
    public final int NUMBER_OF_COLUMNS = 7;
    public final int NUMBER_OF_ROWS = 6;
    private Cell[] indexTab;
    private Cell downLeftCell;
    private Cell upLeftCell;

    public Cell[] getIndexTab() {
        return indexTab;
    }

    public Cell getFirstCell() {
        return downLeftCell;
    }

    public Cell getUpLeft() {
        return upLeftCell;
    }

    public Grid() {
        Cell[] tmp = new Cell[NUMBER_OF_COLUMNS];
        this.indexTab = new Cell[NUMBER_OF_COLUMNS];
        Arrays.fill(tmp, null);

        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            Cell prevLeft = null;
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                Cell cell = new Cell();
                cell.link(tmp[j], EnumDirection.DOWN);
                cell.link(prevLeft, EnumDirection.LEFT);
                cell.link(null, EnumDirection.UP);
                cell.link(null, EnumDirection.RIGHT);
                prevLeft = cell;
                tmp[j] = cell;
                if (i == 0) {
                    this.indexTab[j] = cell;
                    if (j == 0) {
                        this.downLeftCell = cell;
                    }
                }
                if (i == this.NUMBER_OF_ROWS-1 && j == 0) {
                    this.upLeftCell = cell;
                }
            }
        }
    }

    public Grid(String[] gridString) {
        Cell[] tmp = new Cell[NUMBER_OF_COLUMNS];
        this.indexTab = new Cell[NUMBER_OF_COLUMNS];
        Arrays.fill(tmp, null);

        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            Cell prevLeft = null;
            String[] line = gridString[i].split(" ");
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                String celluleString = line[j];
                Cell cell = new Cell();
                cell.setColor(EnumColor.intToColor(Integer.parseInt(celluleString)));
                cell.link(null, EnumDirection.DOWN);
                cell.link(prevLeft, EnumDirection.LEFT);
                cell.link(tmp[j], EnumDirection.UP);
                cell.link(null, EnumDirection.RIGHT);
                prevLeft = cell;
                tmp[j] = cell;
                if (cell.getColor() == EnumColor.EMPTY) {
                    this.indexTab[j] = cell;
                }
                if (i == 0) {
                    if (j == 0) {
                        this.upLeftCell = cell;
                    }
                }
                if (i == this.NUMBER_OF_ROWS-1 && j == 0) {
                    this.downLeftCell = cell;
                }
            }
        }
    }

    private String getGridLineAsString(Cell c) {
        String line = new String();
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            line += c + ((i != NUMBER_OF_COLUMNS - 1) ? " " : "");
            Optional<Cell> tmp2 = c.getNeighbor(EnumDirection.RIGHT);
            if (tmp2.isPresent()) {
                c = tmp2.get();
            }
        }
        return line;
    }

    private String getGridLineAsInt(Cell c) {
        String line = new String();
        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            line += EnumColor.colorToInt(c.getColor()) + ((i != NUMBER_OF_COLUMNS - 1) ? " " : "");
            Optional<Cell> tmp2 = c.getNeighbor(EnumDirection.RIGHT);
            if (tmp2.isPresent()) {
                c = tmp2.get();
            }
        }
        return line;
    }

    public String getGridAsString() {
        Cell tmp = this.upLeftCell;
        String grid = new String();
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            grid += getGridLineAsString(tmp) + "\n";
            Optional<Cell> tmp2 = tmp.getNeighbor(EnumDirection.DOWN);
            if (tmp2.isPresent()) {
                tmp = tmp2.get();
            }
        }
        return grid;
    }

    public String getGridAsInt() {
        Cell tmp = this.upLeftCell;
        String grid = new String();
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            grid += getGridLineAsInt(tmp) + "\n";
            Optional<Cell> tmp2 = tmp.getNeighbor(EnumDirection.DOWN);
            if (tmp2.isPresent()) {
                tmp = tmp2.get();
            }
        }
        return grid;
    }

    public boolean updateGrid(EnumColor e, int choix) {
        indexTab[choix - 1].setColor(e);
        Optional<Cell> tmp2 = indexTab[choix - 1].getNeighbor(EnumDirection.UP);
        boolean win = indexTab[choix - 1].check();
        if (tmp2.isPresent())
            indexTab[choix - 1] = tmp2.get();
        return win;
    }

    public boolean isColumnFull(int i) {
        return indexTab[i - 1] == null;
    }

}

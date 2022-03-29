import java.io.FileWriter;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

public class Game {
    private boolean gameOver;
    private Grid grid;
    private Player player1;
    private Player player2;
    private Player playerTurn;

    public Game() {
        this.grid = new Grid();
        int a = askChoice();
        if (a == 2) {
            try {
                loadGame("sauvegarde.txt");
            } catch (Exception e) {
                System.out.println("Pas de sauvegarde disponible");
                a = 1;
            }
        }
        if (a == 1) {
            System.out.println("\033[H\033[2J");
            this.player1 = new Player(askPlayerName(1), askColor());
            this.gameOver = false;

            System.out.println("\033[H\033[2J");
            String playerName2 = askPlayerName(2);
            while (playerName2.equals(getPlayer1().getName())) {
                System.out.printf("Le nom '%s' est déjà utilisé par le joueur 1\n", getPlayer1().getName());
                playerName2 = askPlayerName(2);
            }

            EnumColor playerColor2 = askColor();
            while (playerColor2 == getPlayer1().getColor()) {
                System.out.printf("La couleur '%s' est déjà utilisé par '%s'\n", getPlayer1().getColor(),
                        getPlayer1().getName());
                playerColor2 = askColor();
            }
            this.player2 = new Player(playerName2, playerColor2);
            setPlayerTurn(getPlayer1());
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(Player playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void gameLoop() {
        System.out.println("\033[H\033[2J");
        System.out.printf(grid.getGridAsString());
        while (!isGameOver()) {
            int choix = askPlayedColumn();
            setGameOver(getGrid().updateGrid(getPlayerTurn().getColor(), choix));
            setPlayerTurn((getPlayerTurn() == getPlayer1()) ? getPlayer2() : getPlayer1());

            System.out.println("\033[H\033[2J");
            System.out.printf(grid.getGridAsString());
        }
        System.out.printf("%s a gagné la partie\n",
                (getPlayerTurn() == getPlayer1()) ? getPlayer2().getName() : getPlayer1().getName());
    }

    private int askPlayedColumn() {
        System.out.printf("%s, dans quelle colonne voulez vous jouer?                                (42 to save & quit)\n",
                getPlayerTurn().getName());
        int choix = -1;
        while (true) {
            try {
                choix = Integer.parseInt(System.console().readLine());
                if (choix < 0 || choix > 7) {
                    if (choix == 42) {
                        saveGame("sauvegarde.txt");
                        System.exit(0);
                    } else {
                        System.out.println("Veuillez choisir une colonne entre 1 et 7 compris");
                    }
                } else if (getGrid().isColumnFull(choix)) {
                    System.out.println("Cette colonne est pleine, veuillez en choisir une autre");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Veuillez entrer un entier valide");
            }
        }
        return choix;
    }

    private int askChoice() {
        System.out.println("Voulez vous : \n [1] Creer une nouvelle partie \n [2] Charger une partie ");
        int choix = -1;
        while (true) {
            try {
                choix = Integer.parseInt(System.console().readLine());
                if (choix < 1 || choix > 2) {
                    System.out.println("Veuillez choisir une colonne entre 1 et 2 compris");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Veuillez entrer un entier valide");
            }
        }
        return choix;
    }

    private String askPlayerName(int num) {
        System.out.printf("Veuillez donner le nom du joueur %d: ", num);
        while (true) {
            try {
                String read = System.console().readLine();
                return read;
            } catch (Exception e) {
                System.out.println("Veuillez entrer un nom valide");
            }
        }
    }

    private EnumColor askColor() {
        System.out.println("Veuillez selectionner une couleur :");
        List<EnumColor> colors = EnumColor.getValues();
        for (int i = 0; i < colors.size(); i++) {
            System.out.printf("[%d] %s ", i + 1, colors.get(i) + "●" + EnumColor.RESET);
        }
        System.out.println();
        while (true) {
            try {
                int input = Integer.parseInt(System.console().readLine());
                if (input < 1 || input > colors.size()) {
                    System.out.printf("Veuillez entrer une valeure entre 1 et %d\n", colors.size());
                } else
                    return colors.get(input - 1);
            } catch (Exception e) {
                System.out.println("Veuillez entrer un entier valide");
            }
        }
    }

    private void saveGame(String path) {
        try {
            FileWriter fw = new FileWriter(path);
            fw.write(getPlayer1().getDataToSave());
            fw.write(getPlayer2().getDataToSave());
            fw.write(((getPlayerTurn() == getPlayer1()) ? "1" : "2") + "\n");
            fw.write(grid.getGridAsInt());
            fw.close();
        } catch (Exception e) {
            System.err.println("Sauvegarde impossible!");
        }
    }

    private void loadGame(String path) throws Exception {
        try {
            String file = new String(Files.readAllBytes(Paths.get(path)));
            String[] lines = file.split("\n");

            String[] cols = lines[0].split("&sep&");
            this.player1 = new Player(cols[0], EnumColor.intToColor(Integer.parseInt(cols[1])));
            cols = lines[1].split("&sep&");
            this.player2 = new Player(cols[0], EnumColor.intToColor(Integer.parseInt(cols[1])));
            String pTurn = lines[2];
            this.playerTurn = pTurn.equals("1") ? getPlayer1() : getPlayer2();

            this.grid = new Grid(Arrays.copyOfRange(lines, 3, lines.length));
        } catch (Exception e) {
            throw e;
        }
    }
}
import java.io.FileWriter;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

public class Game {
    private boolean gameOver;
    private Grille grid;
    private Joueur player1;
    private Joueur player2;
    private Joueur playerTurn;

    public Game() {
        this.grid = new Grille();
        if (askChoice() == 2) {
            loadGame("file.txt");
        } else {
            this.player1 = new Joueur(askPlayerName(1), askColor());
            this.gameOver = false;

            String playerName2 = askPlayerName(2);
            while (playerName2.equals(getPlayer1().getName())) {
                System.out.printf("Le nom '%s' est déjà utilisé par le joueur 1\n", getPlayer1().getName());
                playerName2 = askPlayerName(2);
            }

            EnumJeton playerColor2 = askColor();
            while (playerColor2 == getPlayer1().getCouleurEquipe()) {
                System.out.printf("La couleur '%s' est déjà utilisé par '%s'\n", getPlayer1().getCouleurEquipe(),
                        getPlayer1().getName());
                playerColor2 = askColor();
            }
            this.player2 = new Joueur(playerName2, playerColor2);
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Grille getGrid() {
        return grid;
    }

    public void setGrid(Grille grid) {
        this.grid = grid;
    }

    public Joueur getPlayer1() {
        return player1;
    }

    public void setPlayer1(Joueur player1) {
        this.player1 = player1;
    }

    public Joueur getPlayer2() {
        return player2;
    }

    public void setPlayer2(Joueur player2) {
        this.player2 = player2;
    }

    public Joueur getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(Joueur playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void gameLoop() {
        setPlayerTurn(getPlayer1());
        System.out.printf(grid.getGridString());
        while (!isGameOver()) {
            int choix = askPlayedColumn();
            setGameOver(getGrid().update(getPlayerTurn().getCouleurEquipe(), choix));
            setPlayerTurn((getPlayerTurn() == getPlayer1()) ? getPlayer2() : getPlayer1());

            System.out.printf(grid.getGridString());
        }
        System.out.printf("%s a gagné la partie\n",
                (getPlayerTurn() == getPlayer1()) ? getPlayer2().getName() : getPlayer1().getName());
        saveGame("file.txt");
    }

    private int askPlayedColumn() {
        System.out.printf("%s, dans quelle colonne voulez vous jouer?                                (42 to save)\n",
                getPlayerTurn().getName());
        int choix = -1;
        while (true) {
            try {
                choix = Integer.parseInt(System.console().readLine());
                if (choix < 0 || choix > 7) {
                    if (choix == 42) {
                        System.out.println("Le nom de votre sauvegarde ?");
                        String name = new String("");
                        name = "sauvegarde/" + System.console().readLine() + ".txt";
                        saveGame(name);
                        System.out.printf(
                                "%s, dans quelle colonne voulez vous jouer?                                (42 to save)\n",
                                getPlayerTurn().getName());
                    } else {
                        System.out.println("Veuillez choisir une colonne entre 1 et 7 compris");
                    }
                } else if (getGrid().column_full(choix)) {
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

    private EnumJeton askColor() {
        System.out.println("Veuillez selectionner une couleur :");
        List<EnumJeton> colors = EnumJeton.getValues();
        for (int i = 0; i < colors.size(); i++) {
            System.out.printf("[%d] %s ", i + 1, colors.get(i) + "●" + EnumJeton.RESET);
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
            fw.write(grid.getGridInt());
            fw.close();
        } catch (Exception e) {
            System.err.println("Sauvegarde impossible!");
        }
    }

    private void loadGame(String path) {
        try {
            String file = new String(Files.readAllBytes(Paths.get(path)));
            String[] lines = file.split("\n");

            String[] cols = lines[0].split("&sep&");
            this.player1 = new Joueur(cols[0], EnumJeton.intToEquipe(Integer.parseInt(cols[1])));
            cols = lines[1].split("&sep&");
            this.player2 = new Joueur(cols[1], EnumJeton.intToEquipe(Integer.parseInt(cols[1])));
            
            this.grid = new Grille(Arrays.copyOfRange(lines, 2, lines.length));
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}

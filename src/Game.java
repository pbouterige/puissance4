public class Game {
    private boolean gameOver;
    private Grille grid;
    private Joueur player1;
    private Joueur player2;
    private Joueur playerTurn;

    public Game() {
        this.grid = new Grille();
        this.player1 = new Joueur(askPlayerName(1), askColor());
        this.player2 = new Joueur(askPlayerName(2), askColor());
        this.gameOver = false;
    }

    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }

    public Grille getGrid() { return grid; }
    public void setGrid(Grille grid) { this.grid = grid; }

    public Joueur getPlayer1() { return player1; }
    public void setPlayer1(Joueur player1) { this.player1 = player1; }

    public Joueur getPlayer2() { return player2; }
    public void setPlayer2(Joueur player2) { this.player2 = player2; }

    public Joueur getPlayerTurn() { return playerTurn; }
    public void setPlayerTurn(Joueur playerTurn) { this.playerTurn = playerTurn; }

    public void gameLoop() {
        setPlayerTurn(getPlayer1());
        getGrid().afficheGrille();
        while (!isGameOver()) {            
            int choix = askPlayedColumn();
            setGameOver(getGrid().update(getPlayerTurn().getCouleurEquipe(), choix));
            setPlayerTurn((getPlayerTurn() == getPlayer1()) ? getPlayer2() : getPlayer1());

            getGrid().afficheGrille();
        }
        System.out.printf("%s a gagné la partie\n", (getPlayerTurn() == getPlayer1()) ? getPlayer2().getName() : getPlayer1().getName());
    }

    private int askPlayedColumn() {
        System.out.println("quelle colonne " + getPlayerTurn().getName() + " ?");
        int choix = -1;
        while (true) {
            try {
                choix = Integer.parseInt(System.console().readLine());
                if (choix < 0 || choix > 7) {
                    System.out.println("La colonne doit être entre 1 et 7 compris");
                } else if (getGrid().column_full(choix)) {
                    System.out.println("colonne pleine. choississez-en une autre");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("entrez un entier valide svp");
            }
        }
        return choix;
    }

    private String askPlayerName(int num) {
        System.out.printf("Donnez le nom du joueur %d : ", num);
        while (true) {
            try {
                String read = System.console().readLine();
                return read;
            } catch (Exception e) {
                System.out.println("Entrez un nom valide");
            }
        }
    }

    private EnumJeton askColor() {
        System.out.println("couleur jeton : ");
        System.out.printf("[1] %s  [2] %s  [3] %s [4] %s", EnumJeton.ROUGE, EnumJeton.BLEU, EnumJeton.VERT, EnumJeton.JAUNE);
        System.out.printf(" [5] %s  [6] %s  [7] %s ", EnumJeton.WHITE, EnumJeton.VIOLET, EnumJeton.CYAN);
        while (true) {
            try {
                EnumJeton color = EnumJeton.intToEquipe(Integer.parseInt(System.console().readLine()));
                if (color == EnumJeton.VIDE) {
                    System.out.println("Entrez une valeure entre 1 et 7 svp");
                } else
                    return color;
            } catch (Exception e) {
                System.out.println("entrez un entier valide svp");
            }
        }
    }
}

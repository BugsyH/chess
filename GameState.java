public class GameState {
    private Player currentPlayer;
    private boolean isKingInCheck;
    private boolean isGameOver;

    public GameState(Player startingPlayer) {
        this.currentPlayer = startingPlayer;
        this.isKingInCheck = false;
        this.isGameOver = false;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer(Player player1, Player player2) {
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }

    public boolean isKingInCheck() {
        return isKingInCheck;
    }

    public void setKingInCheck(boolean kingInCheck) {
        isKingInCheck = kingInCheck;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}

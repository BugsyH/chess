public class Chess {
    public static void main(String[] args) {
        Board board = new Board();
        Board board2 = new Board();
        Player player1 = new Player("Player 1", 'W');
        Player player2 = new Player("Player 2", 'B');
        GameState gameState = new GameState(player1);
        board.listPositions();
        board.draw();
        

        while (!gameState.isGameOver()) {
            // Current player's turn
            Player currentPlayer = gameState.getCurrentPlayer();
            System.out.println(currentPlayer.getName() + "'s turn");
            if (!gameState.isKingInCheck()) {
                board.move(currentPlayer);
            } else {
                while (gameState.isKingInCheck()) {
                        board2 = board;
                        board2.moveCheck(currentPlayer);
                        gameState.setKingInCheck(board2.isKingInCheck(currentPlayer.getColour()));
                        if (gameState.isKingInCheck()) {
                            System.out.println("Invalid move. Try again. [King is in check]");
                        } else {
                            board = board2;
                        }
                }
            }
            board.listPositions();
            board.draw();
            

            // Check if the king is in check or the game is over
            Player otherPlayer = currentPlayer == player1 ? player2 : player1;
            board2 = board;
            gameState.setKingInCheck(board.isKingInCheck(otherPlayer.getColour()));
            gameState.setGameOver(board.isKingInCheckmate(currentPlayer.getColour()));

            // Switch to the other player
            gameState.switchPlayer(player1, player2);
        }
    }
}
public class Chess {
    public static void main(String[] args) {
        Board board = new Board();
        Player player1 = new Player("Player 1", 'W');
        Player player2 = new Player("Player 2", 'B');
        board.draw();
        board.listPositions();


        while (true) {
            // Player 1's turn
            System.out.println(player1.getName() + "'s turn");
            board.move(player1);
            board.draw();
            board.listPositions();

            // Player 2's turn
            System.out.println(player2.getName() + "'s turn");
            board.move(player2);
            board.draw();
            board.listPositions();
        }

    }
}
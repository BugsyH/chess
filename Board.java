public class Board {
    // Define the box drawing characters
    private static final char HORIZONTAL = '\u2550';
    private static final char VERTICAL = '\u2551';
    private static final char UPPER_LEFT_CORNER = '\u2554';
    private static final char UPPER_RIGHT_CORNER = '\u2557';
    private static final char LOWER_LEFT_CORNER = '\u255A';
    private static final char LOWER_RIGHT_CORNER = '\u255D';
    private static final char UPPER_T = '\u2566';
    private static final char LOWER_T = '\u2569';
    private static final char LEFT_T = '\u2560';
    private static final char RIGHT_T = '\u2563';
    private static final char PLUS = '\u256C';
    private static final char DARK_SQUARE = '\u2593';
    private static final char LIGHT_SQUARE = '\u2591';

    public void draw(){
        String darkSquare = createSquare(DARK_SQUARE);
        String lightSquare = createSquare(LIGHT_SQUARE);
        Piece[][] pieces = new Piece[8][8];
    
        // Initialize the top two rows with white pieces
        Piece[] whitePieces = {
            new Piece('W', 'R'), new Piece('W', 'N'), new Piece('W', 'B'), new Piece('W', 'Q'), 
            new Piece('W', 'K'), new Piece('W', 'B'), new Piece('W', 'N'), new Piece('W', 'R')
        };
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = i == 0 ? whitePieces[j] : new Piece('W', 'P');
            }
        }
    
        // Initialize the bottom two rows with black pieces
        Piece[] blackPieces = {
            new Piece('B', 'R'), new Piece('B', 'N'), new Piece('B', 'B'), new Piece('B', 'Q'), 
            new Piece('B', 'K'), new Piece('B', 'B'), new Piece('B', 'N'), new Piece('B', 'R')
        };
        for (int i = 6; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = i == 7 ? blackPieces[j] : new Piece('B', 'P');
            }
        }
    
        drawBoard(darkSquare, lightSquare, pieces);
    }

    private static String createSquare(char squareType) {
        StringBuilder square = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            square.append(squareType);
        }
        return square.toString();
    }

    public void drawBoard(String darkSquare, String lightSquare, Piece[][] pieces) {
        // Draw the top border
        drawHorizontalBorder(UPPER_LEFT_CORNER, UPPER_T, UPPER_RIGHT_CORNER);
    
        // Draw the squares and pieces
        for (int i = 0; i < 8; i++) {
            // Draw pieces if they exist for this row
            if ((i < 2 || i >= 6) && i < pieces.length) {
                drawRow(i, darkSquare, lightSquare, pieces[i], true);
                drawRow(i, darkSquare, lightSquare, pieces[i], false);
            } else {
                // Draw empty squares
                drawRow(i, darkSquare, lightSquare, null, true);
                drawRow(i, darkSquare, lightSquare, null, false);
            }
    
            // Draw the middle border
            if (i < 7) {
                drawHorizontalBorder(LEFT_T, PLUS, RIGHT_T);
            }
        }
    
        // Draw the bottom border
        drawHorizontalBorder(LOWER_LEFT_CORNER, LOWER_T, LOWER_RIGHT_CORNER);

        // Draw the column letters along the bottom
        System.out.println("  A      B      C      D      E      F      G      H\n");
    }
    
    private static void drawRow(int rowIndex, String darkSquare, String lightSquare, Piece[] pieces, boolean isTop) {
        System.out.print(VERTICAL);
        for (int i = 0; i < 8; i++) {
            String square = (rowIndex + i) % 2 == 0 ? darkSquare : lightSquare;
            if (pieces != null && pieces[i] != null) {
                String pieceRepresentation = isTop ? pieces[i].getTop() : pieces[i].getBottom();
                // Print only one character of the square on either side of the piece
                System.out.print(square.substring(0, 1));
                System.out.print(pieceRepresentation);
                System.out.print(square.substring(5)); // Start at index 5 for the right side of the piece
            } else {
                System.out.print(square);
            }
            System.out.print(VERTICAL);
        }
        // Print the row index on the right on every second line
        if (isTop) {
            System.out.print(" " + (8 - rowIndex));
        }
        System.out.println();
    }

    private static void drawHorizontalBorder(char leftCorner, char t, char rightCorner) {
        System.out.print(leftCorner);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) System.out.print(HORIZONTAL);
            System.out.print(i < 7 ? t : rightCorner);
        }
        System.out.println();
    }
}



/*

╔══════╦══════╦══════╦══════╦══════╦══════╦══════╦══════╗
║▓[##]▓║░[^^]░║▓[&&]▓║░[%%]░║▓[$$]▓║░[&&]░║▓[^^]▓║░[##]░║
║▓-Ro-▓║░-Kn-░║▓-Bi-▓║░-Qu-░║▓-Kk-▓║░-Bi-░║▓-Kn-▓║░-Ro-░║
╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣
║░[**]░║▓[**]▓║░[**]░║▓[**]▓║░[**]░║▓[**]▓║░[**]░║▓[**]▓║
║░-Pa-░║▓-Pa-▓║░-Pa-░║▓-Pa-▓║░-Pa-░║▓-Pa-▓║░-Pa-░║▓-Pa-▓║
╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣
║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║
║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║
╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣
║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║
║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║
╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣
║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║
║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║
╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣
║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║
║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║
╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣
║▓{**}▓║░{**}░║▓{**}▓║░{**}░║▓{**}▓║░{**}░║▓{**}▓║░{**}░║
║▓-Pa-▓║░-Pa-░║▓-Pa-▓║░-Pa-░║▓-Pa-▓║░-Pa-░║▓-Pa-▓║░-Pa-░║
╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣
║░{##}░║▓{^^}▓║░{&&}░║▓{%%}▓║░{$$}░║▓{&&}▓║░{^^}░║▓{##}▓║
║░-Ro-░║▓-Kn-▓║░-Bi-░║▓-Qu-▓║░-Kk-░║▓-Bi-▓║░-Kn-░║▓-Ro-▓║
╚══════╩══════╩══════╩══════╩══════╩══════╩══════╩══════╝


*/
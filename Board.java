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
    private Piece[][] pieces;
    private boolean isKingInCheck = false;

    public Board() {
        pieces = new Piece[8][8];
    
        // Initialize the top two rows with black pieces
        Piece[] blackPieces = {
            new Piece('B', 'R', new int[]{0, 0}), new Piece('B', 'N', new int[]{0, 1}), new Piece('B', 'B', new int[]{0, 2}), new Piece('B', 'Q', new int[]{0, 3}), 
            new Piece('B', 'K', new int[]{0, 4}), new Piece('B', 'B', new int[]{0, 5}), new Piece('B', 'N', new int[]{0, 6}), new Piece('B', 'R', new int[]{0, 7})
        };
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = i == 0 ? blackPieces[j] : new Piece('B', 'P', new int[]{i, j});
            }
        }
    
        // Initialize the middle four rows with null pieces
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = null;
            }
        }
    
        // Initialize the bottom two rows with white pieces
        Piece[] whitePieces = {
            new Piece('W', 'R', new int[]{7, 0}), new Piece('W', 'N', new int[]{7, 1}), new Piece('W', 'B', new int[]{7, 2}), new Piece('W', 'Q', new int[]{7, 3}), 
            new Piece('W', 'K', new int[]{7, 4}), new Piece('W', 'B', new int[]{7, 5}), new Piece('W', 'N', new int[]{7, 6}), new Piece('W', 'R', new int[]{7, 7})
        };
        for (int i = 6; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = i == 7 ? whitePieces[j] : new Piece('W', 'P', new int[]{i, j});
            }
        }
    }

    // Draw the chess board
    public void draw(){
        String darkSquare = createSquare(DARK_SQUARE);
        String lightSquare = createSquare(LIGHT_SQUARE);
        drawBoard(darkSquare, lightSquare, pieces);
    }

    // Create a square of a given type
    private static String createSquare(char squareType) {
        StringBuilder square = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            square.append(squareType);
        }
        return square.toString();
    }

    // Draw the chess board
    public void drawBoard(String darkSquare, String lightSquare, Piece[][] pieces) {
        // Draw the top border
        drawHorizontalBorder(UPPER_LEFT_CORNER, UPPER_T, UPPER_RIGHT_CORNER);
    
        // Draw the squares and pieces
        for (int i = 0; i < 8; i++) {
            // Draw pieces if they exist for this row
            if (i < pieces.length) {
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

    // Draw a row of the board
    private static void drawRow(int rowIndex, String darkSquare, String lightSquare, Piece[] row, boolean isTop) {
        System.out.print(VERTICAL);
        for (int i = 0; i < 8; i++) {
            String square = (rowIndex + i) % 2 == 0 ? darkSquare : lightSquare;
            if (row != null && row[i] != null) {
                String pieceRepresentation = isTop ? row[i].getTop() : row[i].getBottom();
                System.out.print(square.substring(0, 1));
                System.out.print(pieceRepresentation);
                System.out.print(square.substring(5));
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

    public void listPositions() {
        StringBuilder whitePieces = new StringBuilder();
        StringBuilder blackPieces = new StringBuilder();
    
        int whiteCounter = 0;
        int blackCounter = 0;
    
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null) {
                    char color = pieces[i][j].getColour();
                    String type = pieces[i][j].getTypeToString();
                    String position = (char)('A' + j) + Integer.toString(8 - i);
                    String piece = type + ": " + position + ", ";
    
                    if (color == 'W') {
                        whitePieces.append(piece);
                        whiteCounter++;
                        if (whiteCounter == 6) {
                            whitePieces.append("\n");
                            whiteCounter = 0;
                        }
                    } else {
                        blackPieces.append(piece);
                        blackCounter++;
                        if (blackCounter == 6) {
                            blackPieces.append("\n");
                            blackCounter = 0;
                        }
                    }
                }
            }
        }
    
        // Print the white and black pieces
        System.out.println("White pieces: \n" + whitePieces.toString());
        System.out.println("Black pieces: \n" + blackPieces.toString());
    }

    // Move a piece on the board
    public void moveCheck(Player player) {
        while (true) {
            Move move = player.getMove();
            int startRow = move.getStartRow();
            int startColumn = move.getStartColumn();
            int endRow = move.getEndRow();
            int endColumn = move.getEndColumn();
    
            // Check if there is a piece at the start position
            if (pieces[startRow][startColumn] == null) {
                System.out.println("No piece at the start position");
                continue;
            }
    
            // Check if the piece belongs to the player
            if (pieces[startRow][startColumn].getColour() != player.getColour()) {
                System.out.println("The piece at the start position does not belong to you");
                continue;
            }
            
            // Check if the move is valid
            if (isValidMove(startRow, startColumn, endRow, endColumn)) {    
                // Check if there's an opponent's piece at the destination position
                if (pieces[endRow][endColumn] != null && pieces[endRow][endColumn].getColour() != player.getColour()) {
                    // Capture the opponent's piece
                    System.out.println("Captured the opponent's " + pieces[endRow][endColumn].getTypeToString());
                    pieces[endRow][endColumn] = null;
                }
    
                // Move the piece
                pieces[endRow][endColumn] = pieces[startRow][startColumn];
                pieces[startRow][startColumn] = null;
    
                // Set hasMoved to true
                pieces[endRow][endColumn].setHasMoved(true);
    
                break;
            } else {
                System.out.println("Invalid move");
            }
        }
    }

        // Move a piece on the board
        public void move(Player player) {
            while (true) {
                Move move = player.getMove();
                int startRow = move.getStartRow();
                int startColumn = move.getStartColumn();
                int endRow = move.getEndRow();
                int endColumn = move.getEndColumn();
        
                // Check if there is a piece at the start position
                if (pieces[startRow][startColumn] == null) {
                    System.out.println("No piece at the start position");
                    continue;
                }
        
                // Check if the piece belongs to the player
                if (pieces[startRow][startColumn].getColour() != player.getColour()) {
                    System.out.println("The piece at the start position does not belong to you");
                    continue;
                }
                
                // Check if the move is valid
                if (isValidMove(startRow, startColumn, endRow, endColumn)) {    
                    // Check if there's an opponent's piece at the destination position
                    if (pieces[endRow][endColumn] != null && pieces[endRow][endColumn].getColour() != player.getColour()) {
                        // Capture the opponent's piece
                        System.out.println("Captured the opponent's " + pieces[endRow][endColumn].getTypeToString());
                        pieces[endRow][endColumn] = null;
                    }
    
                    if (isKingInCheck) {
                        // Check if the move puts the king in check
                        Piece piece = pieces[startRow][startColumn];
                        Piece[][] tempPieces = new Piece[8][8];
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                tempPieces[i][j] = pieces[i][j];
                            }
                        }
                        tempPieces[endRow][endColumn] = tempPieces[startRow][startColumn];
                        tempPieces[startRow][startColumn] = null;
                        if (isKingInCheck(piece.getColour())) {
                            System.out.println("Invalid move: puts the king in check");
                            continue;
                        }
                    }
        
                    // Move the piece
                    pieces[endRow][endColumn] = pieces[startRow][startColumn];
                    pieces[startRow][startColumn] = null;
        
                    // Set hasMoved to true
                    pieces[endRow][endColumn].setHasMoved(true);
        
                    break;
                } else {
                    System.out.println("Invalid move");
                }
            }
        }

    

    // Check if the move is valid
    private boolean isValidMove(int startRow, int startColumn, int endRow, int endColumn) {
        // Check if the start and end positions are within the board
        if (startRow < 0 || startRow >= 8 || startColumn < 0 || startColumn >= 8 || endRow < 0 || endRow >= 8 || endColumn < 0 || endColumn >= 8) {
            return false;
        } else {
            // Check if the end position is empty or has an opponent's piece
            if (pieces[endRow][endColumn] == null || pieces[startRow][startColumn].getColour() != pieces[endRow][endColumn].getColour()) {
                Piece piece = pieces[startRow][startColumn];
                char type = piece.getType();
                char colour = piece.getColour();
                boolean hasMoved = piece.getHasMoved();

                switch (type) {
                    case 'P':  // Pawn
                        // Pawns can only move forward one square or two squares on their first move, and can capture diagonally
                        if (colour == 'B') {
                            if ((endRow - startRow == 1 || (!hasMoved && endRow - startRow == 2)) && startColumn == endColumn && pieces[endRow][endColumn] == null) {
                                return true;
                            } else if (endRow - startRow == 1 && Math.abs(endColumn - startColumn) == 1 && pieces[endRow][endColumn] != null && pieces[endRow][endColumn].getColour() != colour) {
                                return true;
                            }
                        } else {
                            if ((startRow - endRow == 1 || (!hasMoved && startRow - endRow == 2)) && startColumn == endColumn && pieces[endRow][endColumn] == null) {
                                return true;
                            } else if (startRow - endRow == 1 && Math.abs(endColumn - startColumn) == 1 && pieces[endRow][endColumn] != null && pieces[endRow][endColumn].getColour() != colour) {
                                return true;
                            }
                        }
                        break;
                    case 'R':  // Rook
                        // Rooks can move horizontally or vertically any number of squares
                        if (startRow == endRow) {
                            // Moving horizontally
                            int columnStep = startColumn < endColumn ? 1 : -1;
                            for (int i = startColumn + columnStep; i != endColumn; i += columnStep) {
                                if (pieces[startRow][i] != null) {
                                    return false;
                                }
                            }
                            return true;
                        } else if (startColumn == endColumn) {
                            // Moving vertically
                            int rowStep = startRow < endRow ? 1 : -1;
                            for (int i = startRow + rowStep; i != endRow; i += rowStep) {
                                if (pieces[i][startColumn] != null) {
                                    return false;
                                }
                            }
                            return true;
                        }
                        break;
                    case 'N':  // Knight
                        // Knights move in an L-shape: two squares in one direction and one square in a perpendicular direction
                        return (Math.abs(startRow - endRow) == 2 && Math.abs(startColumn - endColumn) == 1) || (Math.abs(startRow - endRow) == 1 && Math.abs(startColumn - endColumn) == 2);
                    case 'B':  // Bishop
                        // Bishops can move diagonally any number of squares
                        if (Math.abs(startRow - endRow) == Math.abs(startColumn - endColumn)) {
                            int rowStep = startRow < endRow ? 1 : -1;
                            int columnStep = startColumn < endColumn ? 1 : -1;
                            for (int i = startRow + rowStep, j = startColumn + columnStep; i != endRow; i += rowStep, j += columnStep) {
                                if (pieces[i][j] != null) {
                                    return false;
                                }
                            }
                            return true;
                        }
                        break;
                    case 'Q':  // Queen
                        // Queens can move horizontally, vertically, or diagonally any number of squares
                        if (startRow == endRow) {
                            // Moving horizontally
                            int columnStep = startColumn < endColumn ? 1 : -1;
                            for (int i = startColumn + columnStep; i != endColumn; i += columnStep) {
                                if (pieces[startRow][i] != null) {
                                    return false;
                                }
                            }
                            return true;
                        } else if (startColumn == endColumn) {
                            // Moving vertically
                            int rowStep = startRow < endRow ? 1 : -1;
                            for (int i = startRow + rowStep; i != endRow; i += rowStep) {
                                if (pieces[i][startColumn] != null) {
                                    return false;
                                }
                            }
                            return true;
                        } else if (Math.abs(startRow - endRow) == Math.abs(startColumn - endColumn)) {
                            // Moving diagonally
                            int rowStep = startRow < endRow ? 1 : -1;
                            int columnStep = startColumn < endColumn ? 1 : -1;
                            for (int i = startRow + rowStep, j = startColumn + columnStep; i != endRow; i += rowStep, j += columnStep) {
                                if (pieces[i][j] != null) {
                                    return false;
                                }
                            }
                            return true;
                        }
                        break;
                    case 'K':  // King
                        // Kings can move one square in any direction
                        return Math.abs(startRow - endRow) <= 1 && Math.abs(startColumn - endColumn) <= 1;
                    default:
                        System.out.println("Invalid piece type");
                        return false; 
                }
            }
        }
        return false;
    }

    // Check if the king is in check
    public boolean isKingInCheck(char player) {
        int kingRow = -1;
        int kingColumn = -1;
        char opponentColour = player == 'W' ? 'B' : 'W';
    
        // Find the king's position
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null && pieces[i][j].getColour() == player && pieces[i][j].getType() == 'K') {
                    kingRow = i;
                    kingColumn = j;
                    break;
                }
            }
        }
    
        // Check if any of the opponent's pieces can capture the king
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null && pieces[i][j].getColour() == opponentColour) {
                    if (isValidAttack(i, j, kingRow, kingColumn)) {
                        System.out.println("The king is in check");
                        isKingInCheck = true;
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

        // Check if the move is valid while in check
        private boolean isValidAttack(int startRow, int startColumn, int endRow, int endColumn) {
            // Check if the start and end positions are within the board
            if (startRow < 0 || startRow >= 8 || startColumn < 0 || startColumn >= 8 || endRow < 0 || endRow >= 8 || endColumn < 0 || endColumn >= 8) {
                return false;
            } else {
                // Check if the end position is empty or has an opponent's piece
                if (pieces[endRow][endColumn] == null || pieces[startRow][startColumn].getColour() != pieces[endRow][endColumn].getColour()) {
                    Piece piece = pieces[startRow][startColumn];
                    char type = piece.getType();
                    char colour = piece.getColour();
                    boolean hasMoved = piece.getHasMoved();
                
                    switch (type) {
                        case 'P':  // Pawn
                            // Pawns can only move forward one square or two squares on their first move, and can capture diagonally
                            if (colour == 'B') {
                                if ((endRow - startRow == 1 || (!hasMoved && endRow - startRow == 2)) && startColumn == endColumn && pieces[endRow][endColumn] == null) {
                                    return true;
                                } else if (endRow - startRow == 1 && Math.abs(endColumn - startColumn) == 1 && pieces[endRow][endColumn] != null && pieces[endRow][endColumn].getColour() != colour) {
                                    return true;
                                }
                            } else {
                                if ((startRow - endRow == 1 || (!hasMoved && startRow - endRow == 2)) && startColumn == endColumn && pieces[endRow][endColumn] == null) {
                                    return true;
                                } else if (startRow - endRow == 1 && Math.abs(endColumn - startColumn) == 1 && pieces[endRow][endColumn] != null && pieces[endRow][endColumn].getColour() != colour) {
                                    return true;
                                }
                            }
                            break;
                        case 'R':  // Rook
                            // Rooks can move horizontally or vertically any number of squares
                            if (startRow == endRow) {
                                // Moving horizontally
                                int columnStep = startColumn < endColumn ? 1 : -1;
                                for (int i = startColumn + columnStep; i != endColumn; i += columnStep) {
                                    if (pieces[startRow][i] != null) {
                                        return false;
                                    }
                                }
                                return true;
                            } else if (startColumn == endColumn) {
                                // Moving vertically
                                int rowStep = startRow < endRow ? 1 : -1;
                                for (int i = startRow + rowStep; i != endRow; i += rowStep) {
                                    if (pieces[i][startColumn] != null) {
                                        return false;
                                    }
                                }
                                return true;
                            }
                            break;
                        case 'N':  // Knight
                            // Knights move in an L-shape: two squares in one direction and one square in a perpendicular direction
                            return (Math.abs(startRow - endRow) == 2 && Math.abs(startColumn - endColumn) == 1) || (Math.abs(startRow - endRow) == 1 && Math.abs(startColumn - endColumn) == 2);
                        case 'B':  // Bishop
                            // Bishops can move diagonally any number of squares
                            if (Math.abs(startRow - endRow) == Math.abs(startColumn - endColumn)) {
                                int rowStep = startRow < endRow ? 1 : -1;
                                int columnStep = startColumn < endColumn ? 1 : -1;
                                for (int i = startRow + rowStep, j = startColumn + columnStep; i != endRow; i += rowStep, j += columnStep) {
                                    if (pieces[i][j] != null) {
                                        return false;
                                    }
                                }
                                return true;
                            }
                            break;
                        case 'Q':  // Queen
                            // Queens can move horizontally, vertically, or diagonally any number of squares
                            if (startRow == endRow) {
                                // Moving horizontally
                                int columnStep = startColumn < endColumn ? 1 : -1;
                                for (int i = startColumn + columnStep; i != endColumn; i += columnStep) {
                                    if (pieces[startRow][i] != null) {
                                        return false;
                                    }
                                }
                                return true;
                            } else if (startColumn == endColumn) {
                                // Moving vertically
                                int rowStep = startRow < endRow ? 1 : -1;
                                for (int i = startRow + rowStep; i != endRow; i += rowStep) {
                                    if (pieces[i][startColumn] != null) {
                                        return false;
                                    }
                                }
                                return true;
                            } else if (Math.abs(startRow - endRow) == Math.abs(startColumn - endColumn)) {
                                // Moving diagonally
                                int rowStep = startRow < endRow ? 1 : -1;
                                int columnStep = startColumn < endColumn ? 1 : -1;
                                for (int i = startRow + rowStep, j = startColumn + columnStep; i != endRow; i += rowStep, j += columnStep) {
                                    if (pieces[i][j] != null) {
                                        return false;
                                    }
                                }
                                return true;
                            }
                            break;
                        case 'K':  // King
                            // Kings can move one square in any direction
                            return Math.abs(startRow - endRow) <= 1 && Math.abs(startColumn - endColumn) <= 1;
                        default:
                            System.out.println("Invalid piece type");
                            return false; 
                    }
                }
            }
            return false;
        }

    // Check if the king is in checkmate
    public boolean isKingInCheckmate(char player) {
        // Check if the king is in check
        if (!isKingInCheck(player)) {
            return false;
        }
    
        // Check if the king can move to a safe square
        int kingRow = -1;
        int kingColumn = -1;
        char kingColour = ' ';
    
        // Find the king's position
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null && pieces[i][j].getType() == 'K') {
                    kingRow = i;
                    kingColumn = j;
                    kingColour = pieces[i][j].getColour();
                    break;
                }
            }
        }
    
        // Check if the king can move to a safe square
        for (int i = kingRow - 1; i <= kingRow + 1; i++) {
            for (int j = kingColumn - 1; j <= kingColumn + 1; j++) {
                if (isValidMove(kingRow, kingColumn, i, j) && !isKingInCheck(player)) {
                    return false;
                }
            }
        }
    
        // Check if any of the player's pieces can capture the attacking piece
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j] != null && pieces[i][j].getColour() == kingColour) {
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            if (pieces[k][l] != null && pieces[k][l].getColour() != kingColour && isValidMove(k, l, i, j)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        

        return true;
    }
}



/*

╔══════╦══════╦══════╦══════╦══════╦══════╦══════╦══════╗
║▓####▓║░####░║▓####▓║░####░║▓####▓║░####░║▓####▓║░####░║ 8
║▓║Ro║▓║░╠Kn╣░║▓±Bi±▓║░╬Qu╬░║▓■Ki■▓║░±Bi±░║▓╠Kn╣▓║░║Ro║░║
╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣
║░####░║▓####▓║░####░║▓####▓║░####░║▓####▓║░####░║▓####▓║ 7
║░├Pa┤░║▓├Pa┤▓║░├Pa┤░║▓├Pa┤▓║░├Pa┤░║▓├Pa┤▓║░├Pa┤░║▓├Pa┤▓║
╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣
║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║ 6
║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║
╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣
║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║ 5
║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║
╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣
║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║ 4
║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║
╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣
║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║ 3
║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║░░░░░░║▓▓▓▓▓▓║
╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣
║▓@@@@▓║░@@@@░║▓@@@@▓║░@@@@░║▓@@@@▓║░@@@@░║▓@@@@▓║░@@@@░║ 2
║▓├Pa┤▓║░├Pa┤░║▓├Pa┤▓║░├Pa┤░║▓├Pa┤▓║░├Pa┤░║▓├Pa┤▓║░├Pa┤░║
╠══════╬══════╬══════╬══════╬══════╬══════╬══════╬══════╣
║░@@@@░║▓@@@@▓║░@@@@░║▓@@@@▓║░@@@@░║▓@@@@▓║░@@@@░║▓@@@@▓║ 1
║░║Ro║░║▓╠Kn╣▓║░±Bi±░║▓╬Qu╬▓║░■Ki■░║▓±Bi±▓║░╠Kn╣░║▓║Ro║▓║
╚══════╩══════╩══════╩══════╩══════╩══════╩══════╩══════╝
  A      B      C      D      E      F      G      H

*/
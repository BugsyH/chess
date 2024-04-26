import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public class ChessBoard extends GridPane {
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
    private TextFlow textFlow = new TextFlow();
    Piece lastSelectedPiece = null;

    public ChessBoard() {
        textFlow.setPrefSize(1300, 1300);
        textFlow.setStyle("-fx-background-color: black;");
        textFlow.setLineSpacing(-10); // Adjust the line spacing
        textFlow.setClip(new Rectangle(1300, 1300)); // Clip the TextFlow to its preferred size
    
        ScrollPane scrollPane = new ScrollPane(textFlow);
        scrollPane.setFitToWidth(true); // Allow the TextFlow to expand to its natural width

        initializeBoard();
        appendText("woooooo unicode chess in da window\n");
        drawBoard();
        
        textFlow.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            int boardWidth = 1190; // Calculate the width of the board
            int boardHeight = 1170; // Calculate the height of the board
            double squareWidth = boardWidth / 8.0; // Calculate the width of each square
            double squareHeight = boardHeight / 8.0; // Calculate the height of each square
            int row = (int) (y / squareHeight); // Calculate the row number
            int col = (int) (x / squareWidth); // Calculate the column number
            char[] columns = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
            if (row >= 0 && row < 8 && col >= 0 && col < 8) {
                Piece piece = pieces[row][col]; // Get the piece at the cursor's location
                if (piece != null) {
                    System.out.println("Mouse clicked on square: " + columns[col] + (8 - row) + ". Piece: " + piece.getColour() + piece.getTypeToString());
                    updatePieceAndRedraw(row, col, true);
                } else {
                    System.out.println("Mouse clicked on square: " + columns[col] + (8- row) + ". No piece at this location.");
                }
            }
        });

        textFlow.setOnMouseMoved(event -> {
            double x = event.getX();
            double y = event.getY();
            int boardWidth = 1190; // Calculate the width of the board
            int boardHeight = 1170; // Calculate the height of the board
            double squareWidth = boardWidth / 8.0; // Calculate the width of each square
            double squareHeight = boardHeight / 8.0; // Calculate the height of each square
            int row = (int) (y / squareHeight); // Calculate the row number
            int col = (int) (x / squareWidth); // Calculate the column number
            char[] columns = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
            if (row >= 0 && row < 8 && col >= 0 && col < 8) {
                Piece piece = pieces[row][col]; // Get the piece at the cursor's location
                if (piece != null) {
                    System.out.println("Mouse moved to square: " + columns[col] + (8 - row) + ". Piece: " + piece.getColour() + piece.getTypeToString());
                    updatePieceAndRedraw(row, col, false);
                } else {
                    System.out.println("Mouse moved to square: " + columns[col] + (8- row) + ". No piece at this location.");
                    updatePieceAndRedraw(row, col, false);
                }
            }
        });
    }

    private void appendText(String str) {
        TextFlow line = new TextFlow();
        for (char c : str.toCharArray()) {
            Text text = new Text(String.valueOf(c));
            text.setFont(Font.font("Lucida Console", 35));
            text.setStyle("-fx-fill: #3DCB62;"); // Set font color
    
            Region spacer = new Region();
            spacer.setPrefWidth(-20); // Adjust the spacing between characters
            spacer.setPrefHeight(14); // Adjust the spacing between lines
    
            line.getChildren().addAll(text, spacer);
        }
        textFlow.getChildren().add(line);
    }

    private void initializeBoard() {
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

    // Create a square of a given type
    private static String createSquare(char squareType) {
        StringBuilder square = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            square.append(squareType);
        }
        return square.toString();
    }


    // Draw the chess board
    public void drawBoard() {
        String darkSquare = createSquare(DARK_SQUARE);
        String lightSquare = createSquare(LIGHT_SQUARE);
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
        appendText("  A      B      C      D      E      F      G      H\n");
    }

    // Draw a row of the board
    private void drawRow(int rowIndex, String darkSquare, String lightSquare, Piece[] row, boolean isTop) {
        StringBuilder sb = new StringBuilder();
        sb.append(VERTICAL);
        for (int i = 0; i < 8; i++) {
            String square = (rowIndex + i) % 2 == 0 ? darkSquare : lightSquare;
            if (row != null && row[i] != null) {
                String pieceRepresentation = isTop ? row[i].getTop() : row[i].getBottom();
                sb.append(square.substring(0, 1));
                sb.append(pieceRepresentation);
                sb.append(square.substring(5));
            } else {
                sb.append(square);
            }
            sb.append(VERTICAL);
        }
        if (isTop) {
            sb.append(" " + (8 - rowIndex));
        }
        sb.append("\n");
        appendText(sb.toString());;
    }

    // Draw a horizontal border
    private void drawHorizontalBorder(char leftCorner, char t, char rightCorner) {
        StringBuilder sb = new StringBuilder();
        sb.append(leftCorner);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) sb.append(HORIZONTAL);
            sb.append(i < 7 ? t : rightCorner);
        }
        sb.append("\n");
        appendText(sb.toString());;
    }
    public static void main(String[] args) {
        Application.launch(ChessApp.class, args);
    }

    public static class ChessApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            ChessBoard chessBoard = new ChessBoard();
            // Add the TextFlow to the GridPane
            chessBoard.add(chessBoard.textFlow, 0, 0);
            Scene scene = new Scene(chessBoard, 1200, 1200);
    
            // Set minimum window size
            primaryStage.setMinWidth(1300);
            primaryStage.setMinHeight(1300);
    
            // Set maximum window size
            primaryStage.setMaxWidth(1300);
            primaryStage.setMaxHeight(1300);
    
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    // Method to update the piece at a given location and redraw the board
    public void updatePieceAndRedraw(int row, int col, boolean isMouseClicked) {
        char[] columns = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        Piece piece = pieces[row][col]; // Get the piece at the cursor's location
        if (piece != null) {
            System.out.println("Mouse moved to square: " + columns[col] + (8 - row) + ". Piece: " + piece.getColour() + piece.getTypeToString());
            if (lastSelectedPiece != null && lastSelectedPiece != piece) {
                lastSelectedPiece.isMouseOver(false); // Deselect the last selected piece
            }
            piece.isMouseOver(true); // Mark the current piece as being moused over
            lastSelectedPiece = piece; // Update the last selected piece

            if (isMouseClicked) {
                if (piece.getIsSelected()) {
                    piece.isSelected(false); // Deselect the piece
                    lastSelectedPiece = null; // Update the last selected piece
                } else {
                    piece.isSelected(true); // Select the piece
                }
            }
        } else {
            System.out.println("Mouse moved to square: " + columns[col] + (8 - row) + ". No piece at this location.");
            if (lastSelectedPiece != null) {
                lastSelectedPiece.isMouseOver(false); // Deselect the last selected piece
                lastSelectedPiece = null; // Update the last selected piece
            }
        }
        textFlow.getChildren().clear(); // Clear the TextFlow
        appendText("Mouse over Square :"+ columns[col] + (8 - row) +"\n"); // Reprint the text
        drawBoard(); // Redraw the board
    }

}
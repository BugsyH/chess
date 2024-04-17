
public class Piece {
    private char colour;
    private char type;
    private char squareType;
    private int[] position;
    
    public Piece(char colour, char type, int[] position) {
        this.colour = colour;
        this.type = type;
        this.position = position;
    }
    
    public String getTop() {
        StringBuilder pieceTop = new StringBuilder();
        pieceTop.append(squareType);
                pieceTop.append(colour == 'W' ? "####" : "@@@@");
        pieceTop.append(squareType);
        return pieceTop.toString();
    }
    
    public String getBottom() {
        StringBuilder pieceBottom = new StringBuilder();
        pieceBottom.append(squareType);
        switch (type) {
            case 'P':
                pieceBottom.append("├Pa┤");
                break;
            case 'R':
                pieceBottom.append("║Ro║");
                break;
            case 'N':
                pieceBottom.append("╠Kn╣");
                break;
            case 'B':
                pieceBottom.append("±Bi±");
                break;
            case 'Q':
                pieceBottom.append("╬Qu╬");
                break;
            case 'K':
                pieceBottom.append("■Ki■");
                break;
            default:
                pieceBottom.append("    ");
                break;
        }
        pieceBottom.append(squareType);
        return pieceBottom.toString();
    }

    public int[] getPosition() {
        return position;
    }

    public char getColour() {
        return colour;
    }

    public char getType() {
        return type;
    }

    public String getTypeToString() {
        switch (type) {
            case 'P':
                return "Pawn";
            case 'R':
                return "Rook";
            case 'N':
                return "Knight";
            case 'B':
                return "Bishop";
            case 'Q':
                return "Queen";
            case 'K':
                return "King";
            default:
                return "Unknown";
        }
    }
}

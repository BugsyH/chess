public class Piece {
    private char color;
    private char type;
    private char squareType;
    
    public Piece(char color, char type) {
        this.color = color;
        this.type = type;
    }
    
    public String getTop() {
        StringBuilder pieceTop = new StringBuilder();
        pieceTop.append(squareType);
        switch (type) {
            case 'P':
                pieceTop.append(color == 'W' ? "W**W" : "B**B");
                break;
            case 'R':
                pieceTop.append(color == 'W' ? "W##W" : "B##B");
                break;
            case 'N':
                pieceTop.append(color == 'W' ? "W^^W" : "B^^B");
                break;
            case 'B':
                pieceTop.append(color == 'W' ? "W&&W" : "B&&B");
                break;
            case 'Q':
                pieceTop.append(color == 'W' ? "W%%W" : "B%%B");
                break;
            case 'K':
                pieceTop.append(color == 'W' ? "W$$W" : "B$$B");
                break;
            default:
                pieceTop.append("    ");
                break;
        }
        pieceTop.append(squareType);
        return pieceTop.toString();
    }
    
    public String getBottom() {
        StringBuilder pieceBottom = new StringBuilder();
        pieceBottom.append(squareType);
        switch (type) {
            case 'P':
                pieceBottom.append("├Pw┤");
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
                pieceBottom.append("■Kk■");
                break;
            default:
                pieceBottom.append("    ");
                break;
        }
        pieceBottom.append(squareType);
        return pieceBottom.toString();
    }
}

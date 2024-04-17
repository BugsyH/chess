import java.util.Scanner;
public class Player {
    private String name;
    private char colour;
    private Scanner scanner;
    
    public Player(String name, char colour) {
        this.name = name;
        this.colour = colour;
        this.scanner = new Scanner(System.in);
    }

    public String getName() {
        return name;
    }

    public char getColour() {
        return colour;
    }

    public Move getMove() {
        System.out.println("Enter your move (e.g., E2E4): ");
        String input = scanner.nextLine().toUpperCase();
    
        char startColumn = input.charAt(0);
        int startRow = 8 - Character.getNumericValue(input.charAt(1));
        char endColumn = input.charAt(2);
        int endRow = 8 - Character.getNumericValue(input.charAt(3));
    
        // Convert the columns from letters to indices
        int startColumnIndex = startColumn - 'A';
        int endColumnIndex = endColumn - 'A';
    
        return new Move(startRow, startColumnIndex, endRow, endColumnIndex);
    }

}

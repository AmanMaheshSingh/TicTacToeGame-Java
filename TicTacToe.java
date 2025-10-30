import java.util.*;

public class TicTacToe {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //Player Symbols
        String player1 = "X";
        String player2 = "O";

        boolean playAgain = true; // to control game replay

        //Main Game Loop - allows replaying
        while (playAgain) {

            //initialize board for each new game
            String[] board = {"1","2","3","4","5","6","7","8","9"};

            //Track Game State
            boolean gameOver = false; //became true when someone wins

            //Loop to Allow Multiple Turns
            for (int turn = 0; turn < board.length; turn++) {

                //To Know Whose Turn it is
                String currentplayer = (turn % 2 == 0) ? player1 : player2;
                System.out.println("Player " + (turn % 2 + 1) + " (" + currentplayer + ") - choose a position (1-9): ");

                //Take Input from the user
                int position = sc.nextInt();

                //Check if position is valid between 1 -9
                if (position < 1 || position > 9) {
                    System.out.println("Invalid position! Try again.");
                    turn--; //repeat the turn
                    continue;
                }

                //if Position is Available
                if (board[position - 1].equals("X") || board[position - 1].equals("O")) {
                    System.out.println("Position already taken! Try again.");
                    turn--; //to repeat the turn
                    continue;
                }

                //update the board with current player symbol
                board[position - 1] = currentplayer;

                //print updated board
                printBoard(board);

                //Check for winner after each move
                if (checkWinner(board)) {
                    System.out.println("Player " + (turn % 2 + 1) + " (" + currentplayer + ") wins!");
                    gameOver = true;
                    break; // stop further moves
                }

                //If all cells filled and no winner → draw
                if (turn == 8 && !gameOver) {
                    System.out.println("It's a draw!");
                }
            }

            //Ask if players want to play again - and validate input properly
            while (true) {
                System.out.print("Do you want to play again? (yes/no): ");
                String response = sc.next().toLowerCase();

                if (response.equals("yes") || response.equals("y")) {
                    System.out.println("\nStarting a new game...\n");
                    break; //break this inner loop → start new game
                } 
                else if (response.equals("no") || response.equals("n")) {
                    playAgain = false; // exit main loop
                    System.out.println("Thanks for playing Tic Tac Toe!");
                    break; //exit inner loop also
                } 
                else {
                    //if input is invalid, ask again
                    System.out.println("Please enter 'yes' or 'no' only!");
                }
            }
        }
    }

    //Function to print the board
    public static void printBoard(String[] board) {
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.print(board[i]);
            if ((i + 1) % 3 != 0)
                System.out.print(" | ");
            else if (i != board.length - 1) {
                System.out.println();
                System.out.println("---------");
            }
        }
        System.out.println("\n");
    }

    //Function to check if a player has won
    public static boolean checkWinner(String[] b) {
        // Define all 8 winning combinations (indexes)
        int[][] winCombos = {
            {0, 1, 2}, // row 1
            {3, 4, 5}, // row 2
            {6, 7, 8}, // row 3
            {0, 3, 6}, // column 1
            {1, 4, 7}, // column 2
            {2, 5, 8}, // column 3
            {0, 4, 8}, // diagonal 1
            {2, 4, 6}  // diagonal 2
        };

        //Loop through all combinations and check
        for (int[] combo : winCombos) {
            if (b[combo[0]].equals(b[combo[1]]) && b[combo[1]].equals(b[combo[2]])) {
                // if all three same (either X or O), someone wins
                return true;
            }
        }
        return false;
    }
}

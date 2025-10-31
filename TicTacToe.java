import java.util.*;

public class TicTacToe {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Symbols for players
        String player1 = "X";
        String player2 = "O";

        //menu to select the mode
        System.out.println("Welcome to Tic Tac Toe");
        System.out.println("Choose Mode:");
        System.out.println("1. Player vs Player");
        System.out.println("2. Player vs Computer");
        System.out.print("Enter your choice: ");
        int mode = sc.nextInt();

        boolean playAgain;

        //Main game loop for replay
        do {
            String[] board = {"1","2","3","4","5","6","7","8","9"};
            boolean gameOver = false;

            for (int turn = 0; turn < board.length; turn++) {
                printBoard(board);

                String currentPlayer = (turn % 2 == 0) ? player1 : player2;
                int position = -1;

                if (mode == 2 && currentPlayer.equals(player2)) {
                    //Computer’s turn (will add AI logic on Day 6)
                    System.out.println("Computer (" + player2 + ") is thinking...");
                    position = getComputerMove(board); // placeholder
                } else {
                    //Human player's turn
                    System.out.print("Player " + (turn % 2 + 1) + " (" + currentPlayer + ") - choose a position (1-9): ");
                    
                    if (!sc.hasNextInt()) { // handle invalid input
                        System.out.println("Invalid input! Enter a number between 1–9.");
                        sc.next(); // clear invalid input
                        turn--;
                        continue;
                    }

                    position = sc.nextInt();
                }

                // Validate position range
                if (position < 1 || position > 9) {
                    System.out.println("Invalid position! Try again.");
                    turn--;
                    continue;
                }

                // Check if position already taken
                if (board[position - 1].equals("X") || board[position - 1].equals("O")) {
                    System.out.println("Position already taken! Try again.");
                    turn--;
                    continue;
                }

                //Update board
                board[position - 1] = currentPlayer;

                //Check for winner
                if (checkWinner(board)) {
                    printBoard(board);
                    if (mode == 2 && currentPlayer.equals(player2)) {
                        System.out.println("Computer (" + currentPlayer + ") wins!");
                    } else {
                        System.out.println("Player " + (turn % 2 + 1) + " (" + currentPlayer + ") wins!");
                    }
                    gameOver = true;
                    break;
                }

                //Check for draw
                if (turn == 8 && !gameOver) {
                    printBoard(board);
                    System.out.println("It's a draw!");
                }
            }

            System.out.print("Play again? (yes/y to continue): ");
            String again = sc.next().toLowerCase();
            playAgain = again.equals("yes") || again.equals("y");

        } while (playAgain);

        System.out.println("Thanks for playing Tic Tac Toe!");
    }

    // Function to print the board
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

    // Function to check winner
    public static boolean checkWinner(String[] b) {
        int[][] winCombos = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };
        for (int[] combo : winCombos) {
            if (b[combo[0]].equals(b[combo[1]]) && b[combo[1]].equals(b[combo[2]])) {
                return true;
            }
        }
        return false;
    }

    // Placeholder for AI logic (Day 6)
    public static int getComputerMove(String[] board) {
        Random rand = new Random();
        int move;
        do {
            move = rand.nextInt(9) + 1;
        } while (board[move - 1].equals("X") || board[move - 1].equals("O"));
        return move;
    }
}

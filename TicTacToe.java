import java.util.*;

/*
  TicTacToe - Day 6
  - Modes: PvP, PvC Easy (random), PvC Medium (win/block), PvC Hard (Minimax)
  - Cleaner console UI
  - Safe input handling
*/

public class TicTacToe {
    public static final String PLAYER_X = "X";
    public static final String PLAYER_O = "O";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Tic Tac Toe ===");

        int mode = 0;
        while (mode < 1 || mode > 4) {
            System.out.println("Choose mode:");
            System.out.println("1. Player vs Player");
            System.out.println("2. Player vs Computer (Easy)");
            System.out.println("3. Player vs Computer (Medium)");
            System.out.println("4. Player vs Computer (Hard)");
            System.out.print("Enter choice (1-4): ");
            if (!sc.hasNextInt()) { sc.next(); continue; }
            mode = sc.nextInt();
        }

        boolean playAgain = true;
        while (playAgain) {
            String[] board = {"1","2","3","4","5","6","7","8","9"};
            boolean gameOver = false;

            // Game loop (max 9 turns)
            for (int turn = 0; turn < 9 && !gameOver; turn++) {
                printBoardUI(board);
                String currentPlayer = (turn % 2 == 0) ? PLAYER_X : PLAYER_O;
                int position = -1;

                // Computer turn when mode != 1 and it's O's turn (computer plays O)
                boolean computerTurn = (mode != 1) && currentPlayer.equals(PLAYER_O);

                if (computerTurn) {
                    System.out.println("Computer (" + PLAYER_O + ") is thinking...");
                    if (mode == 2) {
                        position = getRandomMove(board);
                    } else if (mode == 3) {
                        position = getSmartComputerMove(board, PLAYER_O, PLAYER_X); // medium
                    } else { // mode == 4 => Hard (Minimax)
                        position = getBestMoveMinimax(board, PLAYER_O, PLAYER_X); // minimax returns 1-9
                    }
                    System.out.println("Computer chooses: " + position);
                } else {
                    // Human input
                    System.out.print("Player " + ((currentPlayer.equals(PLAYER_X)) ? "1 (X)" : "2 (O)") 
                                     + " - choose a position (1-9): ");
                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid input! Enter a number between 1 and 9.");
                        sc.next(); // clear
                        turn--;
                        continue;
                    }
                    position = sc.nextInt();
                }

                // Validate position
                if (position < 1 || position > 9) {
                    System.out.println("Invalid position! Try again.");
                    turn--;
                    continue;
                }
                if (board[position - 1].equals(PLAYER_X) || board[position - 1].equals(PLAYER_O)) {
                    System.out.println("Position already taken! Try again.");
                    turn--;
                    continue;
                }

                // Place symbol
                board[position - 1] = currentPlayer;

                // Check result
                if (checkWinner(board)) {
                    printBoardUI(board);
                    if (computerTurn) System.out.println("Computer (" + currentPlayer + ") wins!");
                    else System.out.println("Player " + ((currentPlayer.equals(PLAYER_X)) ? "1 (X)" : "2 (O)") + " wins!");
                    gameOver = true;
                    break;
                }
                // Draw
                if (turn == 8 && !gameOver) {
                    printBoardUI(board);
                    System.out.println("It's a draw!");
                }
            }

            // Replay prompt (robust)
            while (true) {
                System.out.print("Play again? (yes/no): ");
                String resp = sc.next().trim().toLowerCase();
                if (resp.equals("yes") || resp.equals("y")) {
                    System.out.println("\nStarting new game...\n");
                    break;
                } else if (resp.equals("no") || resp.equals("n")) {
                    playAgain = false;
                    break;
                } else {
                    System.out.println("Please enter 'yes' or 'no'.");
                }
            }
        }

        System.out.println("Thanks for playing!");
        sc.close();
    }

    // ---------- UI ----------
    public static void printBoardUI(String[] b) {
        System.out.println();
        System.out.println(" " + b[0] + " | " + b[1] + " | " + b[2]);
        System.out.println("---+---+---");
        System.out.println(" " + b[3] + " | " + b[4] + " | " + b[5]);
        System.out.println("---+---+---");
        System.out.println(" " + b[6] + " | " + b[7] + " | " + b[8]);
        System.out.println();
    }

    // ---------- Game logic ----------
    public static boolean checkWinner(String[] b) {
        int[][] combos = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
        };
        for (int[] c : combos) {
            if (b[c[0]].equals(b[c[1]]) && b[c[1]].equals(b[c[2]])) return true;
        }
        return false;
    }

    // ---------- Easy AI ----------
    public static int getRandomMove(String[] board) {
        Random r = new Random();
        int move;
        do {
            move = r.nextInt(9) + 1;
        } while (board[move-1].equals(PLAYER_X) || board[move-1].equals(PLAYER_O));
        return move;
    }

    // ---------- Medium AI: try win, then block, else random ----------
    public static int getSmartComputerMove(String[] board, String ai, String human) {
        int[][] winCombos = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
        };

        // 1) Win if possible
        for (int[] combo : winCombos) {
            int move = findWinningMove(board, combo, ai);
            if (move != -1) return move;
        }
        // 2) Block if human about to win
        for (int[] combo : winCombos) {
            int move = findWinningMove(board, combo, human);
            if (move != -1) return move;
        }
        // 3) Else random
        return getRandomMove(board);
    }

    // Helper for medium
    public static int findWinningMove(String[] board, int[] combo, String symbol) {
        int count = 0;
        int empty = -1;
        for (int idx : combo) {
            if (board[idx].equals(symbol)) count++;
            else if (!board[idx].equals(PLAYER_X) && !board[idx].equals(PLAYER_O)) empty = idx;
        }
        if (count == 2 && empty != -1) return empty + 1;
        return -1;
    }

    // ---------- Hard AI (Minimax) ----------
    public static int getBestMoveMinimax(String[] board, String ai, String human) {
        int bestVal = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int i = 0; i < 9; i++) {
            if (!board[i].equals(PLAYER_X) && !board[i].equals(PLAYER_O)) {
                String backup = board[i];
                board[i] = ai; // try
                int moveVal = minimax(board, 0, false, ai, human);
                board[i] = backup; // undo

                if (moveVal > bestVal) {
                    bestVal = moveVal;
                    bestMove = i;
                }
            }
        }
        return bestMove + 1; // return 1-9
    }

    // Minimax returns score for board state from ai perspective
    public static int minimax(String[] board, int depth, boolean isMax, String ai, String human) {
        // Evaluate terminal states
        if (isWinner(board, ai)) return 10 - depth;      // prefer quicker wins
        if (isWinner(board, human)) return depth - 10;   // prefer later losses
        if (isBoardFull(board)) return 0;

        if (isMax) { // AI maximizing
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (!board[i].equals(PLAYER_X) && !board[i].equals(PLAYER_O)) {
                    String backup = board[i];
                    board[i] = ai;
                    best = Math.max(best, minimax(board, depth + 1, false, ai, human));
                    board[i] = backup;
                }
            }
            return best;
        } else { // Human minimizing
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (!board[i].equals(PLAYER_X) && !board[i].equals(PLAYER_O)) {
                    String backup = board[i];
                    board[i] = human;
                    best = Math.min(best, minimax(board, depth + 1, true, ai, human));
                    board[i] = backup;
                }
            }
            return best;
        }
    }

    // Helpers for minimax
    public static boolean isWinner(String[] b, String symbol) {
        int[][] combos = {
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}
        };
        for (int[] c : combos) {
            if (b[c[0]].equals(symbol) && b[c[1]].equals(symbol) && b[c[2]].equals(symbol)) return true;
        }
        return false;
    }

    public static boolean isBoardFull(String[] b) {
        for (int i = 0; i < 9; i++)
            if (!b[i].equals(PLAYER_X) && !b[i].equals(PLAYER_O)) return false;
        return true;
    }
}
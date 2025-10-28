import java.util.*;

public class TicTacToe{
    public static void main(String[] args) {
        String[] board = {"1","2","3","4","5","6","7","8","9"};

        Scanner sc = new Scanner (System.in);

        //Player Symbol
        String player1="X";
        String player2="O";

        //Loop to Allow Multiple Turn
        for(int turn = 0;turn<board.length;turn++){
            
            //To Know Whose Turn it is
            String currentplayer = (turn%2==0)?player1:player2;
            System.out.println("Player "+(turn%2+1)+"(" + currentplayer +") - choose a position (1-9): ");

            //Take Input from the user
            int position = sc.nextInt();

            //Check if position is valid between 1 -9
            if(position<1 || position>9){
                System.out.println("Invalid position! Try again.");
                turn--;//repeat the turn
                continue;
            }

            //if Position is Available
            if(board[position-1].equals("X")||board[position-1].equals("O")){
                System.out.println("Position already taken! Try again.");
                turn--;//to repeat the turn
                continue;
            }

            //update the board with current player symbol
            board[position-1]=currentplayer;

            //print updated board
            System.out.println();
            for (int  i= 0; i < board.length; i++) {

                //to print numbers on the board
                System.out.print(board[i]);

                //to make sepration after each number leaving the last
                if((i+1)%3!=0){
                    System.out.print(" | ");
                }

                //to seprate each line leaving the last one
                else if(i!=board.length-1){
                    System.out.println();
                    System.out.println("---------");
               }
            }
            System.out.println("\n");//Add a line for readbility
        }
    }
}
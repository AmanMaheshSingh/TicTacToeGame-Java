public class TicTacToe{
    public static void main(String[] args) {
        int[] board = {1,2,3,4,5,6,7,8,9};

        //to make the board of tic tak toe
        for (int i = 0; i < board.length; i++) {

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
    }
}
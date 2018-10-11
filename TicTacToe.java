/*
Created by: GREG WOO
Program: Simulate a game of TicTacToe against an AI bot
*/

import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class TicTacToe {


  public static void main(String[] args) {

    play();

  }


  // A.
  // A method that takes an int n as input which represents the dimensions of the board.
  // This method returns an n by n array of characters

  public static char[][] createBoard(int n){
    char[][] board = new char[n][n];
    // we initialize the array with the space character
    for (int i = 0 ; i < board.length ; i++) {
      for (int j = 0 ; j < board[i].length ; j++) {
        board[i][j] = ' ';
      }

    }
    return board;
  }

  //B.
  // This method takes a 2D array of characters as input and prints out the board
  public static void displayBoard(char[][] x) {
    for (int i = 0 ; i < x.length ; i++) {
      // We use the boardBorder method to place borders above our entries
      boardBorder(x.length);
      System.out.println();

      // This for loop is used to print out the characters in the array
      for (int j = 0 ; j < x[i].length ; j++) {
        if (j == x[i].length -1) {
          System.out.print("|" + x[i][j] + "|");
        } else {
          System.out.print("|" + x[i][j]);
        }
      }
      System.out.println();

      //if it's the last row, we want to place a border under it
      if (i == x.length -1) {
        boardBorder(x.length);
        System.out.println();
      }
    }



  }

  // This method is used to create the borders of the boards.
  public static void boardBorder(int side){

    for (int i = 0 ; i <= side ; i++) {

      if (i == side) {
        System.out.print("+");
      } else {
        System.out.print("+-");
      }
    }
  }


  // C.
  // This method adds the character c received as input on the board in position (x,y)

  public static void writeOnBoard(char[][] board, char c, int x, int y) {

    //First, we want to verify that the inputs received are valid
    if ( (x < 0) || (x >= board.length) || (y < 0) || (y >= board.length) ) {
      throw new IllegalArgumentException("Coordinates represent a cell outside of the board");
    } else if ( !(board[x][y] == ' ') ) {
      throw new IllegalArgumentException("The cell already contains a character that is not the space character");
    } else {
      board[x][y] = c;
    }

  }

  // D.
  // A method that takes the board as input and returns no value.
  // The player makes his move on the board
  public static void getUserMove(char[][] board) {


    Scanner read = new Scanner(System.in);
    System.out.println("What is your move?");
    boolean correctEntry = false;

    // We keep asking the user for a new move until they enter a valid one
    while( correctEntry == false) {
    int moveOnRow = read.nextInt();
    int moveOnColumn = read.nextInt();

    if ( (moveOnRow < 0) || (moveOnRow  >= board.length) || (moveOnColumn < 0) || (moveOnColumn >= board.length) ) {
      System.out.println("Such cell does not exist on the board");
      System.out.print("Please enter a new move");
    } else if ( !(board[moveOnRow][moveOnColumn] == ' ')) {
      System.out.println("This cell is already occupied by an 'x' or an 'o'");
      System.out.print("Please enter a new move");

    } else {
      System.out.println("Well done!");
      correctEntry = true;
      writeOnBoard(board, 'x', moveOnRow, moveOnColumn);
    }
    }

  }

  // E.
  // This method checks for an obvious move for the AI

  public static boolean checkForObviousMove(char[][] board) {
    boolean isThereAnObviousMove = false;

    if (checkForAIWinOnRow(board) || checkForAIWinOnColumn(board) || checkForAIWinOnDiagonal(board)) {
      isThereAnObviousMove = true;
      return isThereAnObviousMove;

    } else if (checkForUserWinOnRow(board) || checkForUserWinOnColumn(board) || checkForUserWinOnDiagonal(board)){
      isThereAnObviousMove = true;
      return isThereAnObviousMove;

    } else {
      return isThereAnObviousMove;
    }
  }


  public static boolean checkForAIWinOnRow(char[][] board) {
    boolean canWin = false;
    int counter = 0;
    for (int i = 0 ; i < board.length ; i++) {
      int emptySpaceIndex = board.length - 1;

      for (int j = 0 ; j < board[i].length ; j++) {

        //checking if the AI can win on the next move
        // if statement to check if there are two 'o's on the same row
        if (board[i][j] == 'o'){
          counter++;

        } else {
          emptySpaceIndex = j;
        }
        //checking if there is only one 'o' missing for the AI to win AND that the cell is empty
        if((counter == board.length -1) && (board[i][emptySpaceIndex] == ' ' )) {
          canWin = true;
          // Here we update the array
          writeOnBoard(board,'o',i,emptySpaceIndex);
          return canWin;
        }

      }
      counter = 0;
    }
    return canWin;
  }

  public static boolean checkForAIWinOnColumn(char[][] board) {

    boolean canWin = false;

    for (int j = 0 ; j <board.length ; j++) {
      int counter = 0;
      int emptySpaceIndex = board.length - 1;

      for (int i = 0 ; i <board.length ; i++) {

        if (board[i][j] == 'o') {
          counter++;
        } else {
          emptySpaceIndex = i;
        }
        //if statement to check if there are two 'o's on the same column AND that the cell is empty
        if ( (counter == board.length -1 ) && (board[emptySpaceIndex][j] == ' ' )) {
          canWin = true;
          // Here we update the array
          writeOnBoard(board,'o',emptySpaceIndex,j);
          return canWin;

        }

      }

    }
    return canWin;

  }


  public static boolean checkForAIWinOnDiagonal(char[][] board) {
    boolean canWin = false;
    int counterDiagOne = 0;
    int counterDiagTwo = 0;

    int emptySpaceDiagOneIndexRow = 0;
    int emptySpaceDiagOneIndexColumn = 0;

    int emptySpaceDiagTwoIndexRow = 0;
    int emptySpaceDiagTwoIndexColumn = 0;


    for (int i = 0 ; i < board.length ; i++) {

      for (int j = 0 ; j < board[i].length ; j++) {

        // Here we check if the AI can win on the diagonal going from up right to down left
        if( (i+j == board.length - 1) )  {

          if (board[i][j] == 'o') {
            counterDiagOne++;
          } else {
            emptySpaceDiagOneIndexRow = i;
            emptySpaceDiagOneIndexColumn = j;
          }
          if( counterDiagOne == board.length -1 && (board[emptySpaceDiagOneIndexRow][emptySpaceDiagOneIndexColumn] == ' ' )){
            canWin = true;
            // Here we update the array
            writeOnBoard(board,'o',emptySpaceDiagOneIndexRow,emptySpaceDiagOneIndexColumn);
          }
          // We now do the same thing but for the up left down right diagonal
        } if (i == j) {

          if (board[i][j] == 'o') {
            counterDiagTwo++;
          } else {
            emptySpaceDiagTwoIndexRow = i;
            emptySpaceDiagTwoIndexColumn = j;
          }
          if( counterDiagTwo == board.length -1 && (board[emptySpaceDiagTwoIndexRow][emptySpaceDiagTwoIndexColumn] == ' ' )){
            canWin = true;
            // Here we update the array
             writeOnBoard(board,'o',emptySpaceDiagTwoIndexRow,emptySpaceDiagTwoIndexColumn);
          }
        }

      }
    }
    return canWin;
  }

  // The two following methods are the exact same for checking if the user can win


  // Method that checks if the user is about to win

  public static boolean checkForUserWinOnRow(char[][] board) {
    boolean canLose = false;
    int counter = 0;
    for (int i = 0 ; i < board.length ; i++) {
      int emptySpaceIndex = board.length - 1;

      for (int j = 0 ; j < board[i].length ; j++) {

        //checking if the User can win on the next move
        // if statement to check if there are two 'x's on the same row
        if (board[i][j] == 'x'){
          counter++;

        } else {
          emptySpaceIndex = j;
        }
        //checking if there is only one 'x' missing for the User to win AND that the cell is empty
        if((counter == board.length -1) && (board[i][emptySpaceIndex] == ' ' )) {
          canLose = true;
          // Here we update the array
          writeOnBoard(board,'o',i,emptySpaceIndex);
          return canLose;
        }

      }
      counter = 0;
    }
    return canLose;
  }

  public static boolean checkForUserWinOnColumn(char[][] board) {

    boolean canLose = false;

    for (int j = 0 ; j <board.length ; j++) {
      int counter = 0;
      int emptySpaceIndex = board.length - 1;

      for (int i = 0 ; i <board.length ; i++) {

        if (board[i][j] == 'x') {
          counter++;
        } else {
          emptySpaceIndex = i;
        }
        //if statement to check if there are two 'x's on the same column AND that the cell is empty
        if ( (counter == board.length -1 ) && (board[emptySpaceIndex][j] == ' ' )) {
          canLose = true;
          // Here we update the array
          writeOnBoard(board,'o',emptySpaceIndex,j);
          return canLose;

        }

      }

    }
    return canLose;

  }


  public static boolean checkForUserWinOnDiagonal(char[][] board) {
    boolean canLose = false;
    int counterDiagOne = 0;
    int counterDiagTwo = 0;

    int emptySpaceDiagOneIndexRow = 0;
    int emptySpaceDiagOneIndexColumn = 0;

    int emptySpaceDiagTwoIndexRow = 0;
    int emptySpaceDiagTwoIndexColumn = 0;


    for (int i = 0 ; i < board.length ; i++) {

      for (int j = 0 ; j < board[i].length ; j++) {

        // Here we check if the User can win on the diagonal going from up right to down left
        if( (i+j == board.length - 1) )  {

          if (board[i][j] == 'x') {
            counterDiagOne++;
          } else {
            emptySpaceDiagOneIndexRow = i;
            emptySpaceDiagOneIndexColumn = j;
          }
          if( counterDiagOne == board.length -1 && (board[emptySpaceDiagOneIndexRow][emptySpaceDiagOneIndexColumn] == ' ' )){
            canLose = true;
            // Here we update the array
            writeOnBoard(board,'o',emptySpaceDiagOneIndexRow,emptySpaceDiagOneIndexColumn);
            return canLose;
          }
          // We now do the same thing but for the up left down right diagonal
        } if (i == j) {

          if (board[i][j] == 'x') {
            counterDiagTwo++;
          } else {
            emptySpaceDiagTwoIndexRow = i;
            emptySpaceDiagTwoIndexColumn = j;
          }
          if( counterDiagTwo == board.length -1 && (board[emptySpaceDiagTwoIndexRow][emptySpaceDiagTwoIndexColumn] == ' ' )){
            canLose = true;
            // Here we update the array
            writeOnBoard(board,'o',emptySpaceDiagTwoIndexRow,emptySpaceDiagTwoIndexColumn);
            return canLose;
          }
        }

      }
    }
    return canLose;
  }


  // F.
  // This method is for the AI's move.
  // We first check if there is an obvious move.
  // If not, a random move is generated.

  public static void getAIMove(char[][] board) {

    if (checkForObviousMove(board)) {
      // The move is already done
    } else {
      Random randomAIMove = new Random();
      int emptySpaceChecker = 0;
      int x, y;

      //In this while loop we generate a random move and check if the cell is occupied
      while ( emptySpaceChecker  == 0 ) {
        x = randomAIMove.nextInt(board.length - 1);
        y = randomAIMove.nextInt(board.length - 1);

        if (board[x][y] == ' ') {
          emptySpaceChecker = 1;
          // Here we update the array which corresponds to the AI's move
          writeOnBoard(board, 'o', x, y);
        }
      }
    }
  }


  // G.
  // This method checks if someone won the game

  public static char checkForWinner(char[][] board){

    if (winnerChecker(board, 'o') == true) {
     return 'o';
    } else if (winnerChecker(board, 'x') == true) {
     return 'x';
    } else {
     return ' ';
    }
  }


// This method is checking if the character put as input won the game
  public static boolean winnerChecker(char[][]board, char c){
    boolean isWinner = false;
    //char winnerIs = ' ';
    int counterRow = 0;
    int counterColumn = 0;
    int counterDiagOne = 0;
    int counterDiagTwo = 0;


    // Here we check for win on diagonal
    for (int i = 0 ; i < board.length ; i++) {
      for (int j = 0 ; j < board[i].length ; j++) {

        if ( (i == j) && (board[i][j] == c) ) {
          counterDiagOne ++ ;
          if (counterDiagOne == board.length ) {
          isWinner = true;
          return isWinner;
          }

        } if ( ( i + j == board.length - 1 ) && (board[i][j] == c) ) {
          counterDiagTwo ++ ;
          if (counterDiagTwo == board.length ) {
          isWinner = true;
          return isWinner;
          }
        }
     }
    }
    // Here we check for win on row
    for (int i = 0 ; i < board.length ; i++) {
      for (int j = 0 ; j < board[i].length ; j++) {
        if(board[i][j] == c) {
          counterRow ++;
          if(counterRow == board.length) {
           isWinner = true;
           return isWinner;
          }
        }
      }
      counterRow = 0;
    }


    //Here we check for win on column
    for (int j = 0 ; j < board.length ; j++) {
      for(int i = 0 ; i < board.length ; i++) {

        if (board[i][j] == c) {
          counterColumn ++ ;
          if (counterColumn == board.length) {
          isWinner = true;
          return isWinner;
          }
        }

      }
      counterColumn = 0;
    }

    // The method returns false if there isn't a winner
    return isWinner;
  }


  // H.
  // This method plays a game of tic tac toe

  public static void play() {

    Scanner read = new Scanner(System.in);
    String name;

    //We ask for the name of the user
    System.out.println("What's your name?");
    name = read.nextLine();
    System.out.println("Hello " + name + " !");

    //We ask for the dimension of the board
    int keepAsking = 0;
    int dimension = 0;



    while (keepAsking < 1 ) {
      System.out.println("Please choose the dimension of your board: ");

      if ( !(read.hasNextInt())) {
        System.out.println("Please enter a valid integer type! ");
        read.next();

      } else {
       dimension = read.nextInt();
        if (dimension > 0 ) {
          keepAsking = 1 ;
          System.out.println("You chose a board with " + dimension + "x" + dimension + " dimensions.");
        }
      }
    }

    // We now can carry out a game of Tic Tac Toe
    // Creates board with correct dimensions
    char[][] board = createBoard(dimension);

    // Flip a coin to decide who goes first
    // Prints out the result of the coin toss --> we know who is starting
    int coinToss = (int) ( Math.random() * 2);

    // 1 is heads and 0 is tails
    if (coinToss == 1) {
      System.out.println(" The result of the coin toss is heads (1).");
      System.out.println(" The user goes first!");

    } else {
      System.out.println(" The result of the coin toss is tails (0).");
      System.out.println(" The AI goes first!");
    }

    // The players alternate each other in making a move


    int moveCounter = 0;

    if (coinToss == 1) {

      while (moveCounter <= (dimension * dimension)) {
        if(checkForWinner(board) == ' ') {

          getUserMove(board);
          displayBoard(board);
          moveCounter++;

        } if((checkForWinner(board) == ' ') && (moveCounter < (dimension * dimension))) {
        System.out.println("The AI made its move:");
        getAIMove(board);
        displayBoard(board);
        moveCounter++;
        } else {
          break;
        }
      }

    } else if (coinToss == 0) {

      while (moveCounter <= (dimension * dimension)) {
        if(checkForWinner(board) == ' ') {

          System.out.println("The AI made its move:");
          getAIMove(board);
          displayBoard(board);
          moveCounter++;

        } if((checkForWinner(board) == ' ') && (moveCounter < (dimension * dimension))) {

        getUserMove(board);
        displayBoard(board);
        moveCounter++;
        } else {
          break;
        }
      }
    }

    // Print a message indicating who won the game
    if((moveCounter == dimension * dimension) && (checkForWinner(board) == ' ')){
      System.out.println("It's a tie!");
    } else {
      char winner;
      winner = checkForWinner(board);
      System.out.println("We have a winner!");
      System.out.println("The winner is " + winner + " !");
    }




  }







}

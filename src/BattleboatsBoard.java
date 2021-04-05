public class BattleboatsBoard{

  /* written by
    John Nguyen
  */

  private int row;
  private int column;
  private int[][] publicBoard; //board used for actual game
  private int[][] board; //board that gets changed and used for debug
  private int onesCount = 0; //all parts of ships afloat
  private int sunkenShips = 0; //all parts of ships hit

  public BattleboatsBoard(int m, int n){
    row = m;
    column = n;
  }

  public int getOnesCount(){
    return onesCount;
  }

  public int[][] getPrivateBoard(){
    return board;
  }

  public int[][] getPublicBoard(){
    return publicBoard;
  }

  public int getRowLength(){
    return board.length;
  }

  public int getColumnLength(int r){
    return board[r].length;
  }

  public int getSunkenShips(){
    return sunkenShips;
  }

  public int getCoordinate(int r, int c){
    return board[r][c];
  }

  /*
  changes the coordinate based on what it already is
  case 2 is 1(ship) to 2(hit)
  case 3 is 0(open water) to 3(miss)
  case 4 reveals the area for the drone
  case 5 changes the center point(or drone coordinate) so that it can not be
  selected again
  */
  public void setCoordinate(int r, int c, int n){
    switch (n) {
      case 2:
        board[r][c] = 2;
        publicBoard[r][c] = board[r][c];
        sunkenShips++;
        break;
      case 3:
        board[r][c] = 3;
        publicBoard[r][c] = board[r][c];
        break;
      case 4: //drone
        if(board[r][c] == 0){
          publicBoard[r][c] = board[r][c];
        } else if (board[r][c] == 1){
          publicBoard[r][c] = board[r][c];
        } else if (board[r][c] == 2){
          publicBoard[r][c] = board[r][c];
        } else if (board[r][c] == 3){
          publicBoard[r][c] = board[r][c];
        }
        break;
      case 5: //drone center point
        if(board[r][c] == 0){
          publicBoard[r][c] = board[r][c];
          board[r][c] = 4; //drone on open water
        } else if (board[r][c] == 1){
          publicBoard[r][c] = board[r][c];
          board[r][c] = 5; //drone on ship
        } else if (board[r][c] == 2){
          publicBoard[r][c] = board[r][c];
          board[r][c] = 6; //drone on sunk ship
        } else if (board[r][c] == 3){
          publicBoard[r][c] = board[r][c];
          board[r][c] = 7; //drone on miss
        }
        break;
      default:
        System.out.println("something went wrong");
        break;
    }
  }

  // creates a blank board full of zeros
  public int[][] newBoard(){
    publicBoard = new int[row][column];
    board = new int[row][column];
    for (int x = 0; x != row; x++){
      for (int y = 0; y != column; y++){
        publicBoard[x][y] = 0;
        board[x][y] = 0;
      }
    }
    return board;
  }

  /*
  places the baots randomly on the board, by
  finding a random coordinate then,
  choosing a direction,
  checks to see if the coordinate and direction work,
  if not a new direction is tried
  if no direction works then a new random coordinate is found
  this repeats until all boards have been placed
  */
  public int[][] addBoats(int n){
    int rowNumber = (int) Math.floor(Math.random() * row);
    int columnNumber = (int) Math.floor(Math.random() * column);
    int direction = (int) Math.floor(Math.random() * (4));
    onesCount = n * 3;
    int re1 = 0; //the next two spots "extensions" for the boat in rows
    int re2 = 0;
    int ce1 = 0; //the next two spots "extensions" for the boat in columns
    int ce2 = 0;
    int boatCount = 0;
    int counter = 0;
    boolean fact = true;
    String theString = "";
    while (boatCount != n){
      fact = true;
      counter = 0;
      if (board[rowNumber][columnNumber] == 1){ // checks to see if a boit is at the coordinate
        rowNumber = (int) Math.floor(Math.random() * row);
        columnNumber = (int) Math.floor(Math.random() * column);
      } else {
      while (counter < 5){
        if (board[rowNumber][columnNumber] == 1){
          rowNumber = (int) Math.floor(Math.random() * row);
          columnNumber = (int) Math.floor(Math.random() * column);
        }
        switch(direction){
          case 0: // up direction
            re1 = rowNumber - 1;
            re2 = rowNumber - 2;
            ce1 = 0;
            ce2 = 0;
            if (re2 < 0 || re2 >= board.length){ //checks if the boat will be out of bounds
              direction++;
              fact = false;
              break;
            } else if (board[re1][columnNumber] == 1 || board[re2][columnNumber] == 1){ //checks if boats will over lap
                direction++;
                fact = false;
            }
            break;
          case 1: //right direction
            ce1 = columnNumber + 1;
            ce2 = columnNumber + 2;
            re1 = 0;
            re2 = 0;
            if (ce2 < 0 || ce2 >= board[rowNumber].length){ //checks if the boat will be out of bounds
              direction++;
              fact = false;
              break;
            } else if (board[rowNumber][ce1] == 1 || board[rowNumber][ce2] == 1){ //checks if boats will over lap
                direction++;
                fact = false;
            }
            break;
          case 2:
            re1 = rowNumber + 1;
            re2 = rowNumber + 2;
            ce1 = 0;
            ce2 = 0;
            if (re2 < 0 || re2 >= board.length){ //checks if the boat will be out of bounds
              direction++;
              fact = false;
              break;
            } else if (board[re1][columnNumber] == 1 || board[re2][columnNumber] == 1){ //checks if boats will over lap
                direction++;
                fact = false;
            }
            break;
          case 3:
            ce1 = columnNumber - 1;
            ce2 = columnNumber -2;
            re1 = 0;
            re2 = 0;
            if (ce2 < 0 || ce2 >= board[rowNumber].length){ //checks if the boat will be out of bounds
              direction = 0;
              fact = false;
              break;
            } else if (board[rowNumber][ce1] == 1 || board[rowNumber][ce2] == 1){ //checks if boats will over lap
                direction = 0;
                fact = false;
            }
            break;
          default:
            System.out.println("something went wrong");
            break;
        }
        if (fact == true){ // places boat if it is able to be placed
          board[rowNumber][columnNumber] = 1;
          if (direction == 0 || direction == 2){
            board[re1][columnNumber] = 1;
            board[re2][columnNumber] = 1;
          } else if (direction == 1 || direction == 3){
            board[rowNumber][ce1] = 1;
            board[rowNumber][ce2] = 1;
          }
          counter = 4;
          boatCount++;
        }
        counter++;
      }
    }
      if(fact == false) { // if it cant be placed a new coordinate is found
        rowNumber = (int) Math.floor(Math.random() * row);
        columnNumber = (int) Math.floor(Math.random() * column);
      }
    }
    return board;
  }

  // prints out the entire board useful for debug
  public String toString(int[][] aBoard){
    String theString = "";
    int rowCount = 0;
    int colCount = 0;
    while(rowCount < board.length){
      colCount = 0;
      theString += "[";
      while(colCount < aBoard[rowCount].length){
        if(colCount == aBoard[rowCount].length - 1){
          theString += String.valueOf(aBoard[rowCount][colCount]);
        } else{
          theString += String.valueOf(aBoard[rowCount][colCount]) + " ";
        }
        colCount++;
      }
      theString += "] \n";
      rowCount++;
    }
    return theString;
  }



  public static void main(String[] args){
    BattleboatsBoard board = new BattleboatsBoard(4,9);
    board.newBoard();
    board.addBoats(3);
    System.out.println(board.toString(board.getPrivateBoard()));
    System.out.println(board.toString(board.getPublicBoard()));

  }
}

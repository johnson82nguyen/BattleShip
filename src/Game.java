import java.util.Scanner;

public class Game{

  /* written by
    John Nguyen
  */

  /*PLEASE COMPILE BOTH Game AND BattleboatsBoard TOGETHER.
  RUN THE MAIN METHOD IN THE Game CLASS PLASE AND THANK YOU!!*/


  private BattleboatsBoard board;

  public Game(BattleboatsBoard theBoard){
    board = theBoard;
  }

  /*checks to see if the coordinate can be attacked
    if it can it changes the coordinate accordingly and prints the correct message
  */
  public String attack(int r, int c, BattleboatsBoard board){
    if (r < 0 || r > board.getRowLength()){
      return null;
    }
    if (c < 0 || c > board.getColumnLength(r)){
      return null;
    }
    if (board.getCoordinate(r,c) == 1 || board.getCoordinate(r,c) == 5){
      System.out.println("2");
      board.setCoordinate(r,c,2);
      return "Hit!";
    } else if(board.getCoordinate(r,c) == 2){
      return "Penalty";
    } else if (board.getCoordinate(r,c) == 3) {
      return "Penalty";
    } else{
      board.setCoordinate(r,c,3);
      System.out.println("3");
      return "Miss";
    }
  }

  /*checks to see if the drone is playable
  then reveals the board and
  changes coordinates accordingly
  */
  public String drone(int r, int c, BattleboatsBoard board){
    if (r >= 0 && r < board.getRowLength() && c >= 0 && c < board.getColumnLength(r)){
      int reu = r - 1; //extension upward
      int red = r + 1; // extension downward
      int cel = r - 1; //extension to the left
      int cer = r + 1; //extension to the right;
      if(board.getCoordinate(r,c) == 4 || board.getCoordinate(r,c) == 5 || board.getCoordinate(r,c) == 6 || board.getCoordinate(r,c) == 7){
        return "Penalty";
      } else { //sees if the upper extension is out of bounds, if not then sees the rest, displays what is available
        board.setCoordinate(r,c,5);
        if (reu >= 0){
          board.setCoordinate(reu,c,4);
          if (cel >= 0){
            board.setCoordinate(reu,cel,4);
            board.setCoordinate(r,cel,4);
            if(red < board.getRowLength()){
              board.setCoordinate(red,c,4);
              board.setCoordinate(red,cel,4);
            }
          }
          if (cer < board.getColumnLength(r)){
            board.setCoordinate(reu,cer,4);
            board.setCoordinate(r,cer,4);
            if(red < board.getRowLength()){
              board.setCoordinate(red,cer,4);
            }
          }
        } else if (red < board.getRowLength()){ //sees if the lower extension is out of bounds, if not then sees the rest, displays what is available
          board.setCoordinate(red,c,4);
          if (cel >= 0){
            board.setCoordinate(red,cel,4);
            board.setCoordinate(r,cel,4);
            if(reu >= 0){
              board.setCoordinate(reu,c,4);
              board.setCoordinate(reu,cel,4);
            }
          }
          if (cer < board.getColumnLength(r)){
            board.setCoordinate(red,cer,4);
            board.setCoordinate(r,cer,4);
            if(reu >= 0){
              board.setCoordinate(reu,cer,4);
            }
          }
        }
      }
      return "Recon";
    } else {
      return null;
    }
  }

  public boolean debug(String answer){
    if (answer.equals("Y")){
      return true;
    } else{
      return false;
    }
  }

  public static void main(String[] args){
    int turnCounter = 0;
    int shotCounter = 0;
    String output = "";
    String stringAnswer;
    int firstNum;
    int secondNum;
    Scanner s = new Scanner(System.in);
    System.out.println("Enter a row number: ");
    firstNum = s.nextInt();
    while (firstNum < 3 || firstNum > 12){
      System.out.println("\ntry again! Enter a new row number: ");
      s = new Scanner(System.in);
      firstNum = s.nextInt();
    }
    s = new Scanner(System.in);
    System.out.println("\nEnter a row column number: ");
    secondNum = s.nextInt();
    while (secondNum < 3 || secondNum > 12){
      System.out.println("\ntry again! Enter a new row number: ");
      s = new Scanner(System.in);
      secondNum = s.nextInt();
    }
    BattleboatsBoard board = new BattleboatsBoard(firstNum, secondNum);
    board.newBoard();
    if(firstNum == 3 || secondNum == 3){
      board.addBoats(1);
    } else if ((firstNum > 3 && firstNum <= 5) || (secondNum > 3 && secondNum <= 5)){
      board.addBoats(2);
    } else if ((firstNum > 5 && firstNum <= 7) || (secondNum > 5 && secondNum <= 7)){
      board.addBoats(3);
    } else if ((firstNum > 7 && firstNum <= 9) || (secondNum > 7 && secondNum <= 9)){
      board.addBoats(4);
    } else if ((firstNum > 9 && firstNum <= 12) || (secondNum > 9 && secondNum <= 12)){
      board.addBoats(6);
    }
    Game theGame = new Game(board);
    System.out.println("\nWould you like to enter debug mode? Y/N: ");
    s = new Scanner(System.in);
    stringAnswer = s.next();

    //debug mode
    if(theGame.debug(stringAnswer) == true){
      System.out.println("\nWelcome to debug mode!");
      while (board.getSunkenShips() != board.getOnesCount()){
        turnCounter++;
        System.out.println("\nKey: \n0 = open water \n1 = ship \n2 = hit ship \n3 = miss \n4 = drone on open water \n5 = drone on ship \n6 = drone on hit ship \n7 = drone on miss");
        System.out.println("\n" + board.toString(board.getPrivateBoard()));
        System.out.println("\nWhat would you like to do? \n Attack \n or \n Drone");
        s = new Scanner(System.in);
        stringAnswer = s.next();
        while(!stringAnswer.equals("Attack") && !stringAnswer.equals("attack") && !stringAnswer.equals("Drone") && !stringAnswer.equals("drone")){ //loops until you pick one
          System.out.println("\nWhat would you like to do? \n Attack \n or \n Drone");
          s = new Scanner(System.in);
          stringAnswer = s.next();
        }
        if(stringAnswer.equals("Attack") || stringAnswer.equals("attack")){ //if attack is chosen
          shotCounter++;
          output += " " + String.valueOf(turnCounter);
          System.out.println("\nEnter your first coordinate: ");
          s = new Scanner(System.in);
          firstNum = s.nextInt();
          System.out.println("\nEnter your second coordinate: ");
          s = new Scanner(System.in);
          secondNum = s.nextInt();
          String attackResponse = theGame.attack(firstNum, secondNum, board);
          if (attackResponse == null){
            turnCounter++;
            System.out.println("\nPenalty");
          } else if (attackResponse.equals("Penalty")){
            turnCounter++;
            System.out.println("\n" + attackResponse);
          } else {
            System.out.println("\n" + attackResponse);
          }
        }
        if (stringAnswer.equals("Drone") || stringAnswer.equals("drone")){ //if drone is chosen
          turnCounter += 4;
          System.out.println("\nEnter your first coordinate: ");
          s = new Scanner(System.in);
          firstNum = s.nextInt();
          System.out.println("\nEnter your second coordinate: ");
          s = new Scanner(System.in);
          secondNum = s.nextInt();
          String droneResponse = theGame.drone(firstNum, secondNum, board);
          if (droneResponse == null){
            turnCounter++;
            System.out.println("\nPenalty");
          }else if (droneResponse.equals("Penalty")){
            turnCounter++;;
            System.out.println("\n" + droneResponse);
          } else {
            System.out.println("\n" + droneResponse);
          }
        }
      }
      System.out.println("This game took " + turnCounter + " turns and you took " + shotCounter + " shots and shot on turns" + output);
    } else {

      //normal game mode
      while (board.getSunkenShips() != board.getOnesCount()){
        turnCounter++;
        System.out.println("\nKey: \n0 = open water \n1 = ship \n2 = hit ship \n3 = miss");
        System.out.println("\n" + board.toString(board.getPublicBoard()));
        System.out.println("\nWhat would you like to do? \n Attack \n or \n Drone");
        s = new Scanner(System.in);
        stringAnswer = s.next();
        while(!stringAnswer.equals("Attack") && !stringAnswer.equals("attack") && !stringAnswer.equals("Drone") && !stringAnswer.equals("drone")){ //loops until you pick one
          System.out.println("\nWhat would you like to do? \n Attack \n or \n Drone");
          s = new Scanner(System.in);
          stringAnswer = s.next();
        }
        if(stringAnswer.equals("Attack") || stringAnswer.equals("attack")){ //if attack is chosen
          shotCounter++;
          output += " " + String.valueOf(turnCounter);
          System.out.println("\nEnter your first coordinate: ");
          s = new Scanner(System.in);
          firstNum = s.nextInt();
          System.out.println("\nEnter your second coordinate: ");
          s = new Scanner(System.in);
          secondNum = s.nextInt();
          String attackResponse = theGame.attack(firstNum, secondNum, board);
          if (attackResponse == null){
            turnCounter++;
            System.out.println("\nPenalty");
          } else if (attackResponse.equals("Penalty")){
            turnCounter++;
            System.out.println("\n" + attackResponse);
          } else {
            System.out.println("\n" + attackResponse);
          }
        }
        if (stringAnswer.equals("Drone") || stringAnswer.equals("drone")){ //if drone is chosen
          turnCounter += 4;
          System.out.println("\nEnter your first coordinate: ");
          s = new Scanner(System.in);
          firstNum = s.nextInt();
          System.out.println("\nEnter your second coordinate: ");
          s = new Scanner(System.in);
          secondNum = s.nextInt();
          String droneResponse = theGame.drone(firstNum, secondNum, board);
          if (droneResponse == null){
            turnCounter++;
            System.out.println("\nPenalty");
          }
          if (droneResponse.equals("Penalty")){
            turnCounter++;;
            System.out.println("\n" + droneResponse);
          } else {
            System.out.println("\n" + droneResponse);
          }
        }
      }
      System.out.println("This game took " + turnCounter + " turns and you took " + shotCounter + " shots and shot on turns" + output);
    }
  }
}

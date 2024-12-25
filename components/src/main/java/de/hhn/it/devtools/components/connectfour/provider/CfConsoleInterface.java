package de.hhn.it.devtools.components.connectfour.provider;

import de.hhn.it.devtools.apis.connectfour.ConnectFourBoard;
import de.hhn.it.devtools.apis.connectfour.ConnectFourColor;
import de.hhn.it.devtools.apis.connectfour.ConnectFourListener;
import de.hhn.it.devtools.apis.connectfour.ConnectFourPlayer;
import de.hhn.it.devtools.apis.connectfour.ConnectFourService;
import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class to print the ConnectFour board to the console.
 */
public class CfConsoleInterface implements ConnectFourListener {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(CfConsoleInterface.class);
  private final ConnectFourService service;
  private final String reset = "\u001B[0m";
  private final String red = "\u001B[31m";
  private final String yellow = "\u001B[33m";
  private final String blue = "\u001B[34m";
  private boolean locked = false;

  /**
   * Create a new CfBoardPrinter and set the listener.
   * The listener is used to handle the input from the console.
   */
  public CfConsoleInterface(ConnectFourService service) {
    logger.debug("CfConsoleInterface(ConnectFourService service)");
    this.service = service;
  }

  /**
   * Print the board to the console.
   *
   * @param board ConnectFourColor[][]
   */
  public void printBoard(ConnectFourColor[][] board) {
    logger.debug("printBoard(ConnectFourColor[][] board): void");
    System.out.println();
    for (ConnectFourColor[] connectFourColors : board) {
      printRow(connectFourColors);
      printHorizontalLine(board[0].length);
    }
    System.out.println(blue + "[1 2 3 4 5 6 7]\n" + reset);
  }

  private void printRow(ConnectFourColor[] row) {
    logger.debug("printRow(ConnectFourColor[] row): void");
    String pipe = blue + "|" + reset;
    for (ConnectFourColor element : row) {
      System.out.print(pipe + getElementSymbol(element));
    }
    System.out.println(pipe);
  }

  private void printHorizontalLine(int length) {
    logger.debug("printHorizontalLine(int length): void");
    for (int i = 0; i < length; i++) {
      System.out.print(blue + "+-" + reset);
    }
    System.out.println(blue + "+" + reset);
  }

  private String getElementSymbol(ConnectFourColor element) {
    logger.debug("getElementSymbol(ConnectFourColor element): String");
    return switch (element) {
      case RED -> red + "o" + reset;
      case YELLOW -> yellow + "o" + reset;
      case EMPTY -> " " + reset;
      default -> "?";
    };
  }

  /**
   * Print the current Board and
   * read the input from the console.
   */
  public void runOnce() {
    logger.debug("runOnce(): void");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    try {
      String prompt = locked ? "Input: " : service.getCurrentPlayer().getColor() + ": ";
      System.out.print(prompt);
      String input = reader.readLine();
      this.handleInput(input);
    } catch (IOException | IllegalParameterException e) {
      logger.error(e.getMessage());
    }
  }

  /**
   * Informs the listener that the board has changed.
   * This method does not need to be implemented.
   *
   * @param board ConnectFourBoard
   */
  @Override
  public void updateBoard(ConnectFourBoard board) {
    logger.debug("updateBoard(ConnectFourBoard board): void");
    this.printBoard(board.getBoard());
  }

  /**
   * Informs the listener that the player has changed.
   * This method does not need to be implemented.
   *
   * @param player ConnectFourPlayer
   */
  @Override
  public void switchPlayer(ConnectFourPlayer player) {
    logger.debug("switchPlayer(ConnectFourPlayer player): void");
  }

  /**
   * Handle the input from the console.
   * Maybe the implementation needs to be adjusted
   *
   * @param input String
   */
  @Override
  public void handleInput(String input)
      throws IllegalParameterException {
    if ((input.equals("resume") || input.equals("help")) && locked) {
      this.service.resume();
    }

    if (locked) {
      logger.error("Game is over. Please start a new game.");
      return;
    }

    logger.debug("handleInput(String input) throws IllegalParameterException: void");
    try {
      int i = Integer.parseInt(input);
      this.service.placeDisc(i - 1);
    } catch (NumberFormatException e) {
      switch (input) {
        case "help" -> this.printHelp();
        case "save" -> this.service.saveGame();
        case "load" -> this.service.loadGame();
        case "pause" -> this.service.pause();
        case "resume" -> this.service.resume();
        case "stop" -> this.service.stop();
        case "rotate" -> this.service.rotateBoard();
        default -> logger.error("Invalid input: {}", input);
      }
    }
  }

  /**
   * Informs the listener that the game is over.
   *
   * @param color ConnectFourColor
   */
  @Override
  public void gameIsOver(ConnectFourColor color) {
    logger.debug("gameIsOver(ConnectFourColor color): void");
    if (color == ConnectFourColor.EMPTY) {
      System.out.println("It's a draw!");
      return;
    }
    System.out.println(color + " wins!" + reset);
    locked = true;
  }

  /**
   * Informs the listener that the board has been rotated.
   */
  @Override
  public void boardRotated() {
    logger.debug("boardRotated(): void");
  }

  /**
   * Print the help message.
   */
  public void printHelp() {
    logger.debug("printHelp(): void");
    String headline = "\u001B[4m\u001B[1m";
    this.println(blue + headline, "\nCommands:", "");
    String green = "\u001B[32m";
    this.println(green, "\t<number>", " - Place a disc in the selected column");
    this.println(green, "\thelp", " - Print this help message");
    this.println(yellow, "\tsave", " - Save the current game (Unsupported)");
    this.println(yellow, "\tload", " - Load a saved game (Unsupported)");
    this.println(green, "\trotate", " - Rotate the board if permitted");
    this.println(green, "\tpause", " - Pause the game");
    this.println(green, "\tresume", " - Resume the game");
    this.println(red, "\tstop", " - Interrupts the game\n");
  }

  private void println(String color, String message1, String message2) {
    logger.debug("println(String color, String message1, String message2): void");
    System.out.println(color + message1 + reset + message2);
  }

  /**
   * Unlock the game.
   */
  public void unlock() {
    locked = false;
  }
}

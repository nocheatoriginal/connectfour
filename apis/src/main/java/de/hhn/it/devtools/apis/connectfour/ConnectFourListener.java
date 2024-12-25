package de.hhn.it.devtools.apis.connectfour;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;

/**
 * Interface for listening to changes in the ConnectFourBoard.
 * TODO: Implement the following methods.
 *  Add to all implementations of this interface.
 *  ConnectFourColor gameIsOver(...);
 *  boolean playerCanRotate(...);
 *
 * @author Noah
 */
public interface ConnectFourListener {
  /**
   * Informs the listener that the board has changed.
   *
   * @param board ConnectFourBoard
   */
  void updateBoard(ConnectFourBoard board);

  /**
   * Informs the listener that the player has changed.
   *
   * @param player ConnectFourPlayer
   */
  void switchPlayer(ConnectFourPlayer player);

  /**
   * Handle the input from the console.
   *
   * @param input String
   * @throws IllegalParameterException if the input is invalid
   */
  void handleInput(String input) throws IllegalParameterException;

  /**
   * Informs the listener that the game is over.
   *
   * @param color ConnectFourColor
   */
  void gameIsOver(ConnectFourColor color);

  /**
   * Informs the listener that the board has been rotated.
   */
  void boardRotated();
}

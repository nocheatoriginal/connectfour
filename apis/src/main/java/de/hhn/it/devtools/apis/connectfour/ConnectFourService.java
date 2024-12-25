package de.hhn.it.devtools.apis.connectfour;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;

/**
 * ConnectFourService interface.
 */
public interface ConnectFourService {
  /**
   * Resets the game.
   */
  void reset();

  /**
   * Pauses the game.
   */
  void pause();

  /**
   * Resumes the game.
   */
  void resume();

  /**
   * Stops the game.
   */
  void stop();

  /**
   * Adds a listener for game updates. Each listener can only be added once.
   *
   * @param listener listener to be added
   * @return true if the listener could be added. Otherwise, false.
   * @throws IllegalArgumentException if the listener is null.
   */
  boolean addListener(ConnectFourListener listener);

  /**
   * Removes a listener from the list of listeners.
   *
   * @param listener listener to be removed.
   * @return true if the listener could be removed. Otherwise, false.
   */
  boolean removeListener(ConnectFourListener listener);

  /**
   * Returns the board.
   *
   * @return ConnectFourBoard
   */
  ConnectFourBoard getBoard();

  /**
   * Handle the input from the console.
   */
  void handleInput();

  /**
   * Update the game.
   */
  void updateGame();

  /**
   * Rotate the board.
   *
   * @throws IllegalParameterException if the operation is not supported
   */
  void rotateBoard() throws  IllegalParameterException;


  /**
   * Switch current player.
   */
  void switchPlayer();

  /**
   * Returns the current player.
   *
   * @return ConnectFourPlayer current player
   */
  ConnectFourPlayer getCurrentPlayer();

  /**
   * Place a disc in the selected column.
   *
   * @param column selected column
   * @throws IllegalParameterException if the column is invalid or full
   */
  void placeDisc(int column)
      throws IllegalParameterException;

  /**
   * Save the game.
   *
   * @throws UnsupportedOperationException if the operation is not supported
   */
  void saveGame() throws
      UnsupportedOperationException;

  /**
   * Load the game.
   *
   * @throws UnsupportedOperationException if the operation is not supported
   */
  void loadGame() throws
      UnsupportedOperationException;

}
package de.hhn.it.devtools.apis.connectfour;

import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;

import java.util.ArrayList;
import java.util.List;

public class ConnectFourUsageDemo implements ConnectFourService {
  private List<ConnectFourListener> listeners;
  private ConnectFourGameLoop gameLoop;
  private ConnectFourBoard board;
  private ConnectFourPlayer player1;
  private ConnectFourPlayer player2;
  private ConnectFourPlayer currentPlayer;
  public ConnectFourUsageDemo() {
    reset();
  }

  public static void main(String[] args) {
    ConnectFourUsageDemo connectFourUsageDemo = new ConnectFourUsageDemo();
  }

  /**
   * Resets the game.
   */
  @Override
  public void reset() {
    listeners = new ArrayList<>();
    gameLoop = null;
    board = null; // SimpleConnectFourBoard()
    player1 = null; // SimpleConnectFourPLayer(ConnectFourColor.RED)
    player2 = null; // SimpleConnectFourPLayer(ConnectFourColor.YELLOW)
    currentPlayer = player1;
  }

  /**
   * Pauses the game.
   */
  @Override
  public void pause() {
    gameLoop.pauseGame();
  }

  /**
   * Resumes the game.
   */
  @Override
  public void resume() {
    gameLoop.resumeGame();
  }

  @Override
  public void stop() {
    gameLoop.endGame();
  }

  /**
   * Adds a listener for game updates. Each listener can only be added once.
   *
   * @param listener listener to be added
   * @return true if the listener could be added. Otherwise, false.
   */
  @Override
  public boolean addListener(ConnectFourListener listener) {
    if (!listeners.contains(listener)) {
      listeners.add(listener);
      return true;
    }
    return false;
  }

  /**
   * Removes a listener from the list of listeners.
   *
   * @param listener listener to be removed.
   * @return true if the listener could be removed. Otherwise, false.
   */
  @Override
  public boolean removeListener(ConnectFourListener listener) {
    return listeners.remove(listener);
  }

  /**
   * Returns the board.
   *
   * @return ConnectFourBoard
   */
  @Override
  public ConnectFourBoard getBoard() {
    return board;
  }

  /**
   * Handle the input from the console.
   */
  @Override
  public void handleInput() {

  }

  /**
   * Update the game.
   */
  @Override
  public void updateGame() {
    for (ConnectFourListener listener : listeners) {
      listener.updateBoard(board);
    }
  }

  /**
   * Rotate the board.
   */
  @Override
  public void rotateBoard() {
    board.rotateBoard();
  }

  /**
   * Switch current player.
   */
  @Override
  public void switchPlayer() {
    currentPlayer = currentPlayer == player1 ? player2 : player1;
  }

  /**
   * Returns the current player.
   *
   * @return ConnectFourPlayer current player
   */
  @Override
  public ConnectFourPlayer getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Place a disc in the selected column.
   *
   * @param column selected column
   * @throws IllegalParameterException if the column is invalid or full
   */
  @Override
  public void placeDisc(int column) throws IllegalParameterException {
    if (board.isValidMove(column)) {
      board.selectColumn(column, currentPlayer.getColor());
      updateGame();
      switchPlayer();
    } else {
      throw new IllegalParameterException("Invalid move");
    }
  }

  /**
   * Save the game.
   *
   * @throws UnsupportedOperationException if the operation is not supported
   */
  @Override
  public void saveGame() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Cannot save the game.");
  }

  /**
   * Load the game.
   *
   * @throws UnsupportedOperationException if the operation is not supported
   */
  @Override
  public void loadGame() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Cannot load the game.");
  }
}

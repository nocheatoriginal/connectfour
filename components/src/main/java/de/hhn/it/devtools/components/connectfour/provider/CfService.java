package de.hhn.it.devtools.components.connectfour.provider;

import de.hhn.it.devtools.apis.connectfour.ConnectFourBoard;
import de.hhn.it.devtools.apis.connectfour.ConnectFourColor;
import de.hhn.it.devtools.apis.connectfour.ConnectFourGameLoop;
import de.hhn.it.devtools.apis.connectfour.ConnectFourListener;
import de.hhn.it.devtools.apis.connectfour.ConnectFourPlayer;
import de.hhn.it.devtools.apis.connectfour.ConnectFourService;
import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.function.Consumer;

/**
 * Implementation of the ConnectFourService interface.
 * TODO: Implement the ConnectFourService interface.
 *
 * @author Noah
 */
public class CfService implements ConnectFourService {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(CfService.class);
  private ConnectFourGameLoop gameLoop;
  private ConnectFourPlayer player1;
  private ConnectFourPlayer player2;
  private ConnectFourPlayer currentPlayer;
  private final CfConsoleInterface consoleUi;
  private final ConnectFourBoard board;
  private List<ConnectFourListener> listeners = new ArrayList<ConnectFourListener>();

  /**
   * Constructor for the ConnectFourService.
   */
  public CfService() {
    logger.debug("CfService()");
    consoleUi = new CfConsoleInterface(this);
    board = new CfBoard();
    listeners.clear();
    this.reset();
  }

  /**
   * Resets the game.
   */
  @Override
  public void reset() {
    logger.debug("reset(): void");
    player1 = new CfPlayer(ConnectFourColor.RED);
    player2 = new CfPlayer(ConnectFourColor.YELLOW);
    currentPlayer = player2;
    board.reset();
    addListener(consoleUi);
    consoleUi.unlock();
    start();
    switchPlayer();
    updateGame();
  }

  /**
   * Starts the game.
   */
  private void start() {
    logger.debug("start(): void");
    consoleUi.printHelp();
    // Die gameLoop wird automatisch gestartet beim instanziieren.
    if (gameLoop == null) {
      gameLoop = new CfGameLoop(this, 500);
    }

    if (gameLoop.isStopped()) {
      gameLoop.resumeGame();
    }
  }

  /**
   * Pauses the game.
   */
  @Override
  public void pause() {
    logger.debug("pause(): void");
    gameLoop.pauseGame();
  }

  /**
   * Resumes the game.
   */
  @Override
  public void resume() {
    logger.debug("resume(): void");
    gameLoop.resumeGame();
  }

  /**
   * Stops the game.
   */
  @Override
  public void stop() {
    logger.debug("stop(): void");
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
    logger.debug("addListener(ConnectFourListener listener): boolean");
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null.");
    }

    try {
      logger.debug("Adding listener: {}", listener);
      listeners.add(listener);
      return listeners.contains(listener);
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Removes a listener from the list of listeners.
   *
   * @param listener listener to be removed.
   * @return true if the listener could be removed. Otherwise, false.
   */
  @Override
  public boolean removeListener(ConnectFourListener listener) {
    logger.debug("removeListener(ConnectFourListener listener): boolean");
    try {
      listeners.remove(listener);
      return !listeners.contains(listener);
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Returns the board.
   *
   * @return ConnectFourBoard
   */
  @Override
  public ConnectFourBoard getBoard() {
    logger.debug("getBoard(): ConnectFourBoard");
    return board;
  }

  /**
   * Handle the input from the console.
   */
  @Override
  public void handleInput() {
    logger.debug("handleInput(): void");
    consoleUi.runOnce();
  }

  /**
   * Update the game.
   */
  @Override
  public void updateGame() {
    logger.debug("updateGame(): void");
    notifyListeners(listener -> listener.updateBoard(board));
    this.update();
  }

  private void update() {
    logger.debug("update(): void");
    ConnectFourColor winner = board.checkWin();
    if (winner != ConnectFourColor.EMPTY) {
      logger.info("Player {} wins!", winner);
      notifyListeners(listener -> listener.gameIsOver(winner));
      this.stop();
    } else if (checkForDraw()) {
      logger.info("The game is a draw!");
      notifyListeners(listener -> listener.gameIsOver(ConnectFourColor.EMPTY));
      this.stop();
    } else {
      this.rotationConditionCheck();
    }
  }

  private void rotationConditionCheck() {
    logger.debug("rotationConditionCheck(): void");
    if (board.checkSquare(player1.getColor()) == player1.getColor()) {
      logger.info("Player {} can now rotate the board!", player1.getColor());
      player1.enableRotation();
    }
    if (board.checkSquare(player2.getColor()) == player2.getColor()) {
      logger.info("Player {} can now rotate the board!", player2.getColor());
      player2.enableRotation();
    }
  }

  private boolean checkForDraw() {
    logger.debug("checkForDraw(): boolean");
    return (player2.getNumberOfRemainingDiscs() <= 0 && player1.getNumberOfRemainingDiscs() <= 0);
  }

  /**
   * Rotate the board.
   */
  @Override
  public void rotateBoard() throws IllegalParameterException {
    logger.debug("rotateBoard() throws IllegalParameterException: void");
    this.rotationConditionCheck();
    if (!currentPlayer.canRotate()) {
      throw new IllegalParameterException("Player cannot rotate the board.");
    }
    notifyListeners(listener -> listener.boardRotated());
    board.rotateBoard();
    this.switchPlayer();

    this.updateGame();
  }

  /**
   * Switch current player.
   */
  @Override
  public void switchPlayer() {
    logger.debug("switchPlayer(): void");
    currentPlayer = currentPlayer == player1 ? player2 : player1;
    notifyListeners(listener -> listener.switchPlayer(currentPlayer));
  }

  /**
   * Returns the current player.
   *
   * @return ConnectFourPlayer current player
   */
  @Override
  public ConnectFourPlayer getCurrentPlayer() {
    logger.debug("getCurrentPlayer(): ConnectFourPlayer");
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
    logger.debug("placeDisc(int column) throws IllegalParameterException: void");
    if (board.isValidMove(column)) {
      board.selectColumn(column, currentPlayer.getColor());
      try {
        currentPlayer.removeDisc();
      } catch (IllegalArgumentException e) {
        throw new IllegalParameterException("No discs left.");
      }
      switchPlayer();
    } else {
      throw new IllegalParameterException("Invalid move");
    }

    this.updateGame();
  }

  /**
   * Throws an Error because it is not implemented yet.
   * Should save the game.
   *
   * @throws UnsupportedOperationException if the operation is not supported
   */
  @Override
  public void saveGame() throws UnsupportedOperationException {
    logger.debug("saveGame() throws UnsupportedOperationException: void");
    throw new UnsupportedOperationException("Cannot save game yet.");
  }

  /**
   * Throws an Error because it is not implemented yet.
   * Should load the game.
   *
   * @throws UnsupportedOperationException if the operation is not supported
   */
  @Override
  public void loadGame() throws UnsupportedOperationException {
    logger.debug("loadGame() throws UnsupportedOperationException: void");
    throw new UnsupportedOperationException("Cannot load game yet.");
  }

  /**
   * Notifies all listeners by executing the consumer accept method. The accept method
   * implementation is in our case a lambda expression.
   *
   * @param consumer consumer to be executed.
   */
  private void notifyListeners(Consumer<ConnectFourListener> consumer) {
    logger.debug("notifyListeners(Consumer<ConnectFourListener> consumer): void");
    try {
      for (ConnectFourListener listener : listeners) {
        consumer.accept(listener);
      }
    } catch (ConcurrentModificationException e) {
      logger.error("Error while notifying listeners: {}", e.getMessage());
    }
  }
}

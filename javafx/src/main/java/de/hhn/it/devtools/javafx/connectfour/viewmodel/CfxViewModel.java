package de.hhn.it.devtools.javafx.connectfour.viewmodel;

import de.hhn.it.devtools.apis.connectfour.ConnectFourBoard;
import de.hhn.it.devtools.apis.connectfour.ConnectFourColor;
import de.hhn.it.devtools.apis.connectfour.ConnectFourListener;
import de.hhn.it.devtools.apis.connectfour.ConnectFourPlayer;
import de.hhn.it.devtools.apis.connectfour.ConnectFourService;
import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.components.connectfour.provider.CfBoard;
import de.hhn.it.devtools.javafx.connectfour.view.CfxBoard;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * ViewModel for the ConnectFour game.
 */
public class CfxViewModel implements ConnectFourListener {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(CfxViewModel.class);
  private final ConnectFourService service;
  private final ObjectProperty<ConnectFourBoard> board;
  private final ObjectProperty<ConnectFourPlayer> currentPlayer;
  private CfxBoard cfxBoard;

  /**
   * Constructor for the CfxViewModel class.
   *
   * @param service ConnectFourService
   */
  public CfxViewModel(final ConnectFourService service) {
    this.service = service;
    this.service.addListener(this);
    this.board = new SimpleObjectProperty<>(service.getBoard());
    this.currentPlayer = new SimpleObjectProperty<>(service.getCurrentPlayer());
  }

  /**
   * Informs the listener that the board has changed.
   *
   * @param board ConnectFourBoard
   */
  @Override
  public void updateBoard(final ConnectFourBoard board) {
    Platform.runLater(() -> {
      logger.debug("updateBoard(ConnectFourBoard board): void");
      this.board.set(this.getNewBoard(board));
    });
  }

  private ConnectFourBoard getNewBoard(ConnectFourBoard oldBoard) {
    ConnectFourBoard newBoard = new CfBoard();
    for (int i = 0; i < oldBoard.SIZE; i++) {
      for (int j = 0; j < oldBoard.SIZE; j++) {
        newBoard.setCell(i, j, oldBoard.getCell(i, j));
      }
    }
    return newBoard;
  }

  /**
   * Informs the listener that the player has changed.
   *
   * @param player ConnectFourPlayer
   */
  @Override
  public void switchPlayer(ConnectFourPlayer player) {
    Platform.runLater(() -> this.currentPlayer.set(player));
  }

  /**
   * Handle the input from the console.
   *
   * @param input String
   */
  @Override
  public void handleInput(String input) throws IllegalParameterException {
    if (input.equals("error")) {
      throw new IllegalParameterException("Input is invalid.");
    }
  }

  /**
   * Informs the listener that the game is over.
   *
   * @param color ConnectFourColor
   */
  @Override
  public void gameIsOver(ConnectFourColor color) {}

  /**
   * Informs the listener that the board has been rotated.
   */
  @Override
  public void boardRotated() {
    if (this.cfxBoard == null) {
      return;
    }

    Platform.runLater(() -> {
      cfxBoard.rotateGridPane();
    });
  }

  public ObjectProperty<ConnectFourBoard> boardProperty() {
    return this.board;
  }

  public ObjectProperty<ConnectFourPlayer> currentPlayerProperty() {
    return this.currentPlayer;
  }

  public void setCfxBoard(CfxBoard cfxBoard) {
    if (this.cfxBoard != null) {
      return;
    }

    this.cfxBoard = cfxBoard;
  }
}
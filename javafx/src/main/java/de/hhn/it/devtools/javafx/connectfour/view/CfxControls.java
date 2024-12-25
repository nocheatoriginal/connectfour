package de.hhn.it.devtools.javafx.connectfour.view;

import de.hhn.it.devtools.apis.connectfour.ConnectFourBoard;
import de.hhn.it.devtools.apis.connectfour.ConnectFourColor;
import de.hhn.it.devtools.apis.connectfour.ConnectFourListener;
import de.hhn.it.devtools.apis.connectfour.ConnectFourPlayer;
import de.hhn.it.devtools.apis.connectfour.ConnectFourService;
import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


/**
 * Class for the controls of the ConnectFour game.
 *
 * @author Fabian
 */
public class CfxControls extends HBox implements ConnectFourListener {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(CfxControls.class);
  private final ConnectFourService service;
  private GridPane gridPane;
  private final int size;
  private CfxCell[][] cells;
  private int index;

  /**
   * Create a new CfxBoard and set the listener.
   * The listener is used to handle the input from the console.
   *
   * @param service ConnectFourService
   */
  public CfxControls(final ConnectFourService service) {
    logger.debug("CfxBoard(CfxViewModel viewModel): void");
    this.service = service;
    service.addListener(this);
    this.size = service.getBoard().getSize();
    this.init();

    // Steuerung mit den Pfeiltasten und Enter
    this.setOnKeyPressed(event -> {
      switch (event.getCode()) {
        case A:
        case LEFT:
          Platform.runLater(this::moveControlsLeft);
          break;
        case D:
        case RIGHT:
          Platform.runLater(this::moveControlsRight);
          break;
        case SPACE:
        case ENTER:
          try {
            service.placeDisc(index);
          } catch (IllegalParameterException e) {
            logger.error("Invalid input", e);
          }
          break;
        case R:
          this.performRotation();
          break;
        default:
          break;
      }
    });

    Platform.runLater(this::requestFocus);
    this.setFocusTraversable(true);
  }

  private void performRotation() {
    try {
      service.rotateBoard();
    } catch (IllegalParameterException e) {
      logger.error("Not allowed: ", e);
    }
  }


  private void init() {
    if (gridPane != null) {
      getChildren().remove(gridPane);
    }


    gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setPadding(new Insets(60, 20, 20, 60));
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    this.cells = new CfxCell[size][1];
    for (int column = 0; column < size; column++) {
      CfxCell cell = new CfxCell(ConnectFourColor.TRANSPARENT);
      gridPane.add(cell, column, 0);
      this.cells[column][0] = cell;

      final int col = column;
      cell.setOnMouseClicked(event -> handleCellClick(event, col));
    }

    index = size / 2;

    this.cells[index - 1][0].setShape(CfxShape.TRIANGLE_L, ConnectFourColor.SPECIAL);
    this.cells[size / 2][0].setColor(ConnectFourColor.RED);
    this.cells[index + 1][0].setShape(CfxShape.TRIANGLE_R, ConnectFourColor.SPECIAL);

    //this.updateControls(ConnectFourColor.RED);
    getChildren().add(gridPane);
  }

  private void handleCellClick(MouseEvent event, int column) {
    if (column == index) {
      try {
        service.placeDisc(index);
      } catch (IllegalParameterException e) {
        logger.error("Invalid input", e);
      }
    } else if (column == index + 1) {
      if (Platform.isFxApplicationThread()) {
        moveControlsRight();
      } else {
        Platform.runLater(this::moveControlsRight);
      }
    } else if (column == index - 1) {
      if (Platform.isFxApplicationThread()) {
        moveControlsLeft();
      } else {
        Platform.runLater(this::moveControlsLeft);
      }
    }
  }

  private void moveControlsRight() {
    if (index == size - 1) {
      return;
    }

    for (int i = 0; i < size; i++) {
      cells[i][0].setColor(ConnectFourColor.TRANSPARENT);
    }

    if (index < size - 2) {
      cells[index + 2][0].setShape(CfxShape.TRIANGLE_R, ConnectFourColor.SPECIAL);
    }
    cells[index + 1][0].setShape(CfxShape.CIRCLE, service.getCurrentPlayer().getColor());
    cells[index][0].setShape(CfxShape.TRIANGLE_L, ConnectFourColor.SPECIAL);

    index++;
  }

  private void moveControlsLeft() {
    if (index == 0) {
      return;
    }

    for (int i = 0; i < size; i++) {
      cells[i][0].setColor(ConnectFourColor.TRANSPARENT);
    }

    if (index > 1) {
      cells[index - 2][0].setShape(CfxShape.TRIANGLE_L, ConnectFourColor.SPECIAL);
    }
    cells[index - 1][0].setShape(CfxShape.CIRCLE, service.getCurrentPlayer().getColor());
    cells[index][0].setShape(CfxShape.TRIANGLE_R, ConnectFourColor.SPECIAL);

    index--;
  }

  private void updateControls(final ConnectFourColor color) {
    cells[index][0].setColor(color);
  }

  /**
   * Informs the listener that the board has changed.
   *
   * @param board ConnectFourBoard
   */
  @Override
  public void updateBoard(ConnectFourBoard board) {}

  /**
   * Informs the listener that the player has changed.
   *
   * @param player ConnectFourPlayer
   */
  @Override
  public void switchPlayer(ConnectFourPlayer player) {
    if (Platform.isFxApplicationThread()) {
      updateControls(player.getColor());
    } else {
      Platform.runLater(() -> updateControls(player.getColor()));
    }
  }

  /**
   * Handle the input from the console.
   *
   * @param input String
   * @throws IllegalParameterException if the input is invalid
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
  public void gameIsOver(ConnectFourColor color) {
    this.init();
  }

  /**
   * Informs the listener that the board has been rotated.
   */
  @Override
  public void boardRotated() {

  }
}

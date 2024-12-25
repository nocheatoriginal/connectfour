package de.hhn.it.devtools.javafx.connectfour.view;

import de.hhn.it.devtools.apis.connectfour.ConnectFourBoard;
import de.hhn.it.devtools.apis.connectfour.ConnectFourColor;
import de.hhn.it.devtools.javafx.connectfour.viewmodel.CfxViewModel;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * The CfxBoard class represents the board in the Connect Four game.
 *
 * @author Nils
 */
public class CfxBoard extends HBox implements ChangeListener<ConnectFourBoard> {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(CfxBoard.class);
  private CfxViewModel viewModel;
  private GridPane gridPane;
  private CfxCell[][] cells;
  private final int size;

  /**
   * Initialize the grid pane.
   *
   * @param viewModel CfxViewModel
   */
  public CfxBoard(final CfxViewModel viewModel) {
    super();
    logger.debug("CfxBoard(CfxViewModel viewModel): void");
    this.viewModel = viewModel;
    viewModel.setCfxBoard(this);
    this.viewModel.boardProperty().addListener(this);
    this.size = viewModel.boardProperty().get().getSize();
    this.init();
    setAlignment(Pos.CENTER);
  }

  /**
   * Called when the value of an {@link ObservableValue} changes.
   * In general, it is considered bad practice to modify the observed value in
   * this method.
   *
   * @param observable The {@code ObservableValue} which value changed
   * @param oldValue   The old value
   * @param newValue   The new value
   */
  @Override
  public void changed(final ObservableValue<? extends ConnectFourBoard> observable,
                      final ConnectFourBoard oldValue, final ConnectFourBoard newValue) {
    if (Platform.isFxApplicationThread()) {
      updateBoard(newValue);
    } else {
      Platform.runLater(() -> updateBoard(newValue));
    }
  }

  /**
   * Update the board.
   *
   * @param newBoard ConnectFourBoard
   */
  private void updateBoard(final ConnectFourBoard newBoard) {
    logger.debug("updateBoard(ConnectFourBoard board): void");
    for (int column = 0; column < newBoard.getSize(); column++) {
      for (int row = 0; row < newBoard.getSize(); row++) {
        updateCell(newBoard.getCell(row, column), row, column);
      }
    }
  }

  private void updateCell(ConnectFourColor color, int row, int column) {
    CfxCell cell = cells[row][column];
    if (!cell.getColor().equals(color)) {
      logger.debug("Cell [{}][{}] has new color: {}", row, column, color);
      cell.setColor(color);
    }
  }

  private void init() {
    gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setStyle("-fx-background-color: blue;");
    gridPane.setPadding(new Insets(60, 20, 20, 60));
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    this.cells = new CfxCell[size][size];

    for (int column = 0; column < size; column++) {
      for (int row = 0; row < size; row++) {
        CfxCell cell = new CfxCell(ConnectFourColor.EMPTY);
        gridPane.add(cell, column, row);
        this.cells[row][column] = cell;
      }
    }
    getChildren().add(gridPane);
  }

  /**
   * Animation for board rotation.
   */
  public void rotateGridPane() {
    int speed = 300;

    RotateTransition rotateTransition = new RotateTransition(Duration.millis(speed), gridPane);
    rotateTransition.setByAngle(360);
    rotateTransition.setCycleCount(1);
    rotateTransition.setAutoReverse(false);
    rotateTransition.setOnFinished(event -> gridPane.setRotate(0));
    rotateTransition.play();
  }
}

package de.hhn.it.devtools.javafx.connectfour.view;

import de.hhn.it.devtools.apis.connectfour.ConnectFourBoard;
import de.hhn.it.devtools.apis.connectfour.ConnectFourColor;
import de.hhn.it.devtools.apis.connectfour.ConnectFourListener;
import de.hhn.it.devtools.apis.connectfour.ConnectFourPlayer;
import de.hhn.it.devtools.apis.connectfour.ConnectFourService;
import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Class for the buttons of the ConnectFour game.
 *
 * @author Johannes
 */
public class CfxButtons extends HBox implements ConnectFourListener {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(CfxButtons.class);

  private final ConnectFourService service;
  private final Button rotateButton;
  private final Button infoButton;


  /**
   * Creates a new CfxButtons object.
   *
   * @param service ConnectFourService
   */
  public CfxButtons(final ConnectFourService service) {
    this.service = service;
    this.service.addListener(this);
    rotateButton = new Button();
    //Increase the Font size of the button
    rotateButton.setStyle("-fx-font-size: 1.5em;");
    rotateButton.setText("Rotate ⟳");
    rotateButton.setFocusTraversable(false);
    rotateButton.setDisable(true);
    rotateButton.setOnAction(event -> {
      try {
        this.service.rotateBoard();
        logger.info("Rotate button clicked");
      } catch (IllegalParameterException e) {
        logger.error("Invalid input", e);
      }
    });
    HBox.setHgrow(rotateButton, Priority.ALWAYS);
    rotateButton.setMaxWidth(Double.MAX_VALUE);
    rotateButton.setAlignment(Pos.CENTER);

    infoButton = new Button();
    // Increase the Font size of the button
    infoButton.setStyle("-fx-font-size: 1.5em;");
    infoButton.setText("?");
    infoButton.setFocusTraversable(false);
    infoButton.setStyle("-fx-background-radius: 50%; -fx-min-width: 30px; -fx-min-height: 30px; "
        + "-fx-max-width: 30px; -fx-max-height: 30px;");
    infoButton.setOnAction(event -> {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Game Information");
      alert.setHeaderText(null);
      alert.setContentText("""
          GOAL OF THE GAME:
          Connect four discs of the same color vertically, horizontally or \
          diagonally to win the game.
          Rotate button: Rotates the board by 90 degrees clockwise.
          Rotate option unlocks after a block of four discs of the same color is created.
          Rotate option stays available after the first unlock and skips the players turn.
          CONTROLS:
          Move disc: Arrow keys or A D or mouseclick on arrow.
          Place disc: Enter or Space or mouseclick on disk.
          """);
      alert.showAndWait();
      logger.info("Info button clicked");
    });

    Region spacerLeft = new Region();
    HBox.setHgrow(spacerLeft, Priority.ALWAYS);

    Region counterSpacer = new Region();
    counterSpacer.setPrefWidth(15);

    Region spacerRight = new Region();
    HBox.setHgrow(spacerRight, Priority.ALWAYS);

    Region smallSpacer = new Region();
    smallSpacer.setPrefWidth(15);

    this.getChildren().addAll(spacerLeft, counterSpacer, rotateButton, spacerRight, infoButton,
        smallSpacer);
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
      rotateButton.setDisable(!player.canRotate());
    } else {
      Platform.runLater(() -> rotateButton.setDisable(!player.canRotate()));
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
    if (Platform.isFxApplicationThread()) {
      rotateButton.setDisable(true);
    } else {
      Platform.runLater(() -> rotateButton.setDisable(true));
    }
  }

  /**
   * Informs the listener that the board has been rotated.
   */
  @Override
  public void boardRotated() {}
}
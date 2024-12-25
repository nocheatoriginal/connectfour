package de.hhn.it.devtools.javafx.connectfour.view;

import de.hhn.it.devtools.apis.connectfour.ConnectFourService;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * The CfxDisplay class represents the upper display of the Connect Four game.
 *
 * @author Noah
 */
public class CfxDisplay extends HBox {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(CfxDisplay.class);
  private final ConnectFourService service;
  private Button reset;

  /**
   * Constructor for the CfxDisplay class.
   *
   * @param service ConnectFourService
   */
  public CfxDisplay(ConnectFourService service) {
    super();
    this.service = service;
    this.setup();
  }

  private void setup() {
    reset = new Button("Restart");
    reset.setStyle("-fx-font-size: 1.5em;");
    reset.setFocusTraversable(false);
    reset.setOnAction(event -> this.reset());
    setAlignment(Pos.CENTER);
    getChildren().addAll(reset);
  }

  private void reset() {
    service.reset();
  }
}
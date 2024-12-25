package de.hhn.it.devtools.javafx.connectfour.screens;

import de.hhn.it.devtools.javafx.connectfour.CfxLauncher;
import de.hhn.it.devtools.javafx.controllers.template.SingletonAttributeStore;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The homescreen of the Connect Four game.
 *
 * @author Nils
 */
public class CfxHomescreen extends VBox {
  private SingletonAttributeStore attributeStore = SingletonAttributeStore.getReference();
  public static final String SCREEN = "cfx-homescreen";
  private boolean running = false;
  private Label status;
  private Button start;
  private Stage stage;

  /**
   * Creates a new homescreen.
   */
  public CfxHomescreen() {
    super();
    this.setup();
    attributeStore.setAttribute(SCREEN, this);
  }

  /**
   * Sets up the homescreen.
   */
  private void setup() {
    running = false;
    status = new Label("Welcome to Connect Four!");
    status.setStyle("-fx-font-size: 2em;");
    start = new Button("Start");
    start.setStyle("-fx-font-size: 2em;");
    start.setOnAction(event -> this.start());
    setAlignment(Pos.CENTER);
    setSpacing(10);
    setPadding(new Insets(10, 10, 10, 10));
    getChildren().addAll(status, start);
  }

  /**
   * Starts the game in a separate window.
   */
  private void start() {
    if (running) {
      this.stage.toFront();
      return;
    }

    try {
      running = true;
      start.setText("To Front");
      this.stage = newStage();
      new CfxLauncher().start(this.stage);
    } catch (Exception e) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error: " + e.getMessage());
      alert.setContentText("An error occurred while starting the game.");
      running = false;
    }
  }

  /**
   * Creates a new stage for the game.
   *
   * @return the new stage
   */
  private Stage newStage() {
    Stage newStage = new Stage();
    newStage.setOnCloseRequest(e -> {
      running = false;
      start.setText("Start");
    });
    return newStage;
  }

  public void stop() {
    running = false;
    start.setText("Start");
  }
}
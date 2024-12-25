package de.hhn.it.devtools.javafx.connectfour.screens;

import de.hhn.it.devtools.apis.connectfour.ConnectFourService;
import de.hhn.it.devtools.javafx.connectfour.CfxLauncher;
import de.hhn.it.devtools.javafx.connectfour.CfxScreenManager;
import de.hhn.it.devtools.javafx.connectfour.view.CfxBoard;
import de.hhn.it.devtools.javafx.connectfour.viewmodel.CfxResetViewmodel;
import de.hhn.it.devtools.javafx.controllers.template.SingletonAttributeStore;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * The reset screen of the Connect Four game.
 *
 * @author Noah
 */
public class CfxResetScreen extends VBox implements ChangeListener<String> {
  private final SingletonAttributeStore attributeStore = SingletonAttributeStore.getReference();
  public static final String SCREEN = "cfx-reset-screen";
  private Label status;
  private Button reset;
  private Button exit;
  private String text;


  /**
   * Creates a new reset screen.
   *
   * @param viewModel the view model for the reset screen
   */
  public CfxResetScreen(final CfxResetViewmodel viewModel) {
    super();
    viewModel.statusProperty().addListener(this);
    setup();
  }

  @Override
  public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
    text = t1;
    Platform.runLater(() -> status.setText(text));
  }

  private void setup() {
    CfxBoard board = (CfxBoard) attributeStore.getAttribute("cfx-board");
    status = new Label("Welcome to Connect Four!");
    status.setStyle("-fx-font-size: 2em;");
    reset = new Button("Reset");
    reset.setStyle("-fx-font-size: 2em;");
    reset.setOnAction(event -> {
      ConnectFourService service = (ConnectFourService) attributeStore.getAttribute("cfx-service");
      attributeStore.setAttribute("cfx-screen", CfxMain.SCREEN);
      CfxScreenManager.switchScreen();
      service.reset();
    });
    exit = new Button("Exit");
    exit.setStyle("-fx-font-size: 2em;");
    exit.setOnAction(event -> {
      CfxLauncher launcher = (CfxLauncher) attributeStore.getAttribute("cfx-launcher");
      CfxHomescreen homescreen = (CfxHomescreen) attributeStore.getAttribute(CfxHomescreen.SCREEN);
      try {
        homescreen.stop();
        launcher.exit();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
    setAlignment(Pos.CENTER);
    setSpacing(10);
    getChildren().addAll(status, board, reset, exit);
  }
}

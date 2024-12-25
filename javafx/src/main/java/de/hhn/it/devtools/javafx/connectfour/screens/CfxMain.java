package de.hhn.it.devtools.javafx.connectfour.screens;

import de.hhn.it.devtools.apis.connectfour.ConnectFourService;
import de.hhn.it.devtools.components.connectfour.provider.CfService;
import de.hhn.it.devtools.javafx.connectfour.view.CfxBoard;
import de.hhn.it.devtools.javafx.connectfour.view.CfxButtons;
import de.hhn.it.devtools.javafx.connectfour.view.CfxControls;
import de.hhn.it.devtools.javafx.connectfour.view.CfxDisplay;
import de.hhn.it.devtools.javafx.connectfour.viewmodel.CfxResetViewmodel;
import de.hhn.it.devtools.javafx.connectfour.viewmodel.CfxViewModel;
import de.hhn.it.devtools.javafx.controllers.template.SingletonAttributeStore;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Controller for the ConnectFour game.
 *
 * @author Noah
 */
public class CfxMain extends HBox {
  public static final String SCREEN = "cfx-main";
  private SingletonAttributeStore attributeStore = SingletonAttributeStore.getReference();
  private CfxViewModel viewModel;
  @FXML
  VBox vbox;
  private CfxBoard board;
  private CfxControls controls;
  private CfxResetViewmodel resetViewmodel;

  /**
   * Constructor for the CfxController class.
   */
  public CfxMain() {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/connectfour"
        + "/ConnectFour.fxml"));
    loader.setRoot(this);
    loader.setController(this);

    try {
      loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }


    ConnectFourService service = new CfService();
    viewModel = new CfxViewModel(service);
    board = new CfxBoard(viewModel);
    CfxBoard copy = new CfxBoard(viewModel);
    attributeStore.setAttribute("cfx-board", copy);
    resetViewmodel = new CfxResetViewmodel(service);
    final CfxResetScreen resetScreen = new CfxResetScreen(resetViewmodel);
    controls = new CfxControls(service);
    CfxDisplay display = new CfxDisplay(service);
    CfxButtons buttons = new CfxButtons(service);
    Region spacer = new Region();
    VBox.setVgrow(spacer, Priority.ALWAYS);
    vbox.getChildren().addAll(display, controls, board, buttons, spacer);
    setAlignment(Pos.CENTER);

    Platform.runLater(() -> {
      if (!controls.isFocused()) {
        controls.requestFocus();
      }
    });


    attributeStore.setAttribute("cfx-service", service);
    attributeStore.setAttribute("cfx-screen", SCREEN);
    attributeStore.setAttribute(SCREEN, new Scene(this));

    attributeStore.setAttribute("cfx-width", super.getWidth());
    attributeStore.setAttribute("cfx-height", super.getHeight());
    attributeStore.setAttribute(CfxResetScreen.SCREEN, new Scene(resetScreen));
  }
}

package de.hhn.it.devtools.javafx.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import de.hhn.it.devtools.javafx.connectfour.screens.CfxHomescreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

public class ConnectFourController extends Controller implements Initializable {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(ConnectFourController.class);
  public static final String SCREEN_CONTROLLER = "screen.controller";

  @FXML
  HBox controlHBox;

  public ConnectFourController() {
    logger.debug("Template Controller created. Hey, if you have copied me, update this message!");
  }

  @Override
  public void initialize(final URL location, final ResourceBundle resources) {
    CfxHomescreen homescreen = new CfxHomescreen();
    controlHBox.getChildren().add(homescreen);
  }
}

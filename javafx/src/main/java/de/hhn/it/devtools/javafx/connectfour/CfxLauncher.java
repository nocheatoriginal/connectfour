package de.hhn.it.devtools.javafx.connectfour;

import de.hhn.it.devtools.javafx.connectfour.screens.CfxMain;
import de.hhn.it.devtools.javafx.controllers.template.SingletonAttributeStore;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The launcher for the Connect Four game.
 *
 * @author Nils
 */
public class CfxLauncher extends Application {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(CfxLauncher.class);
  private Scene scene;
  private Stage stage;

  /**
   * Creates a new launcher for the Connect Four game.
   */
  public CfxLauncher() {
    super();
    SingletonAttributeStore attributeStore = SingletonAttributeStore.getReference();
    attributeStore.setAttribute("cfx-launcher", this);
  }

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(final Stage stage) {
    this.stage = stage;
    CfxMain main = new CfxMain();
    CfxScreenManager.switchScreen();
  }

  /**
   * Set the scene of the launcher.
   *
   * @param scene Scene
   */
  public void setScene(Scene scene) {
    this.scene = scene;
    stage.setScene(scene);
    stage.show();
    stage.setMinWidth(scene.getWidth());
    stage.setMinHeight(scene.getHeight());
  }

  public void exit() {
    stage.close();
  }
}

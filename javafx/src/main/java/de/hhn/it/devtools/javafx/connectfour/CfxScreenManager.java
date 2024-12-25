package de.hhn.it.devtools.javafx.connectfour;

import de.hhn.it.devtools.javafx.connectfour.screens.CfxMain;
import de.hhn.it.devtools.javafx.connectfour.screens.CfxResetScreen;
import de.hhn.it.devtools.javafx.controllers.template.SingletonAttributeStore;
import javafx.scene.Scene;

/**
 * Manages the screens of the Connect Four game.
 *
 * @author Nils
 */
public class CfxScreenManager {
  private static final SingletonAttributeStore attributeStore
      = SingletonAttributeStore.getReference();

  /**
   * Switches the screen of the game.
   */
  public static void switchScreen() {
    Scene mainScene = (Scene) attributeStore.getAttribute(CfxMain.SCREEN);
    Scene resetScene = (Scene) attributeStore.getAttribute(CfxResetScreen.SCREEN);
    CfxLauncher launcher = (CfxLauncher) attributeStore.getAttribute("cfx-launcher");

    String screen = (String) attributeStore.getAttribute("cfx-screen");
    switch (screen) {
      case CfxResetScreen.SCREEN:
        launcher.setScene(resetScene);
        break;
      case CfxMain.SCREEN:
        launcher.setScene(mainScene);
        break;
      default:
        break;
    }
  }
}

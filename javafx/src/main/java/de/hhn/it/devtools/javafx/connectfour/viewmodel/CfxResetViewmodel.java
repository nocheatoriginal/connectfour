package de.hhn.it.devtools.javafx.connectfour.viewmodel;

import de.hhn.it.devtools.apis.connectfour.ConnectFourBoard;
import de.hhn.it.devtools.apis.connectfour.ConnectFourColor;
import de.hhn.it.devtools.apis.connectfour.ConnectFourListener;
import de.hhn.it.devtools.apis.connectfour.ConnectFourPlayer;
import de.hhn.it.devtools.apis.connectfour.ConnectFourService;
import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.javafx.connectfour.CfxScreenManager;
import de.hhn.it.devtools.javafx.controllers.template.SingletonAttributeStore;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * The view model for the reset screen of the Connect Four game.
 */
public class CfxResetViewmodel implements ConnectFourListener {
  private final ObjectProperty<String> status;

  public CfxResetViewmodel(ConnectFourService service) {
    service.addListener(this);
    status = new SimpleObjectProperty<String>("Welcome to Connect Four!");
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
  public void switchPlayer(ConnectFourPlayer player) {}

  /**
   * Handle the input from the console.
   *
   * @param input String
   * @throws IllegalParameterException if the input is invalid
   */
  @Override
  public void handleInput(String input) throws IllegalParameterException {}

  /**
   * Informs the listener that the game is over.
   *
   * @param color ConnectFourColor
   */
  @Override
  public void gameIsOver(ConnectFourColor color) {
    String text = "Game Over!\n";
    if (color == ConnectFourColor.EMPTY) {
      text += "It's a draw!";
    } else {
      text += "The winner is: " + color.toString();
    }

    status.set(text);

    SingletonAttributeStore attributeStore = SingletonAttributeStore.getReference();
    attributeStore.setAttribute("cfx-screen", "cfx-reset-screen");
    CfxScreenManager.switchScreen();
  }

  /**
   * Informs the listener that the board has been rotated.
   */
  @Override
  public void boardRotated() {}

  public ObjectProperty<String> statusProperty() {
    return status;
  }
}

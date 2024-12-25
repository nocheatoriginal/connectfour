package de.hhn.it.devtools.components.connectfour.provider;

import de.hhn.it.devtools.apis.connectfour.ConnectFourColor;
import de.hhn.it.devtools.apis.connectfour.ConnectFourPlayer;

/**
 * Implementation of the ConnectFourPlayer interface.
 * TODO: Implement the ConnectFourPlayer interface.
 *
 * @author Fabian
 */
public class CfPlayer implements ConnectFourPlayer {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(CfPlayer.class);
  private final ConnectFourColor color;
  private int currentDiscs = DISCS;
  private boolean canRotate;

  /**
   * Constructor for the ConnectFourPlayer.
   *
   * @param color ConnectFourColor
   */
  public CfPlayer(ConnectFourColor color) {
    logger.debug("CfPlayer(ConnectFourColor color)");
    if (color != ConnectFourColor.RED && color != ConnectFourColor.YELLOW) {
      throw new IllegalArgumentException("Invalid color. Must be RED or YELLOW.");
    }
    this.color = color;
  }

  @Override
  public int getNumberOfRemainingDiscs() {
    logger.debug("getNumberOfRemainingDiscs(): int");
    return currentDiscs;
  }

  @Override
  public ConnectFourColor getColor() {
    return color;
  }

  @Override
  public void removeDisc() throws IllegalArgumentException {
    logger.debug("removeDisc() throws IllegalArgumentException: void");
    if (currentDiscs <= 0) {
      throw new IllegalArgumentException();
    }
    currentDiscs--;
  }

  /**
   * Set the player's ability to rotate the board to true.
   */
  public void enableRotation() {
    logger.debug("enableRotation(): void");
    this.canRotate = true;
  }

  /**
   * Return the player's ability to rotate the board.
   *
   * @return boolean canRotate
   */
  public boolean canRotate() {
    logger.debug("canRotate(): boolean");
    return canRotate;
  }
}

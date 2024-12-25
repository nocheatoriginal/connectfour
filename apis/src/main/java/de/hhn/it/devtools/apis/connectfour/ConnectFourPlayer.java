package de.hhn.it.devtools.apis.connectfour;

/**
 * ConnectFourPlayer interface.
 * Implement the constructor so that the
 * player color is RED or YELLOW otherwise
 * throw an IllegalArgumentException.
 * EMPTY or null is not allowed.
 */
public interface ConnectFourPlayer {
  /**
   * Constant for the maximum number of discs.
   */
  int DISCS = 7 * 7 / 2;

  /**
   * Returns the color of the player.
   *
   * @return color ConnectFourColor
   */
  ConnectFourColor getColor();

  /**
   * Returns the number of remaining discs.
   *
   * @return int number of DISCS
   */
  int getNumberOfRemainingDiscs();

  /**
   * Remove a disc from the player.
   *
   * @throws IllegalArgumentException if remaining discs is 0
   */
  void removeDisc() throws IllegalArgumentException;

  /**
   * Set the player's ability to rotate the board to true;
   */
  void enableRotation();

  /**
   * Return the player's ability to rotate the board.
   *
   * @return boolean canRotate
   */
  boolean canRotate();
}

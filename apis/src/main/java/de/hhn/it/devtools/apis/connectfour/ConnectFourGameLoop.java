package de.hhn.it.devtools.apis.connectfour;

/**
 * ConnectFourGameLoop interface.
 */
public interface ConnectFourGameLoop extends Runnable {
  /**
   * interrupt the game.
   */
  void interrupt();

  @Override
  void run();

  /**
   * Ends the game.
   */
  void endGame();

  /**
   * Pauses the game.
   */
  void pauseGame();

  /**
   * Resumes the game.
   */
  void resumeGame();

  /**
   * Returns if the game is running.
   *
   * @return boolean isRunning
   */
  boolean isRunning();

  /**
   * Returns if the game is paused.
   *
   * @return boolean isPaused
   */
  boolean isPaused();

  /**
   * Returns if the game is stopped.
   *
   * @return boolean isStopped
   */
  boolean isStopped();

  /**
   * Get Thread Name.
   *
   * @return String name
   */
  String getName();
}

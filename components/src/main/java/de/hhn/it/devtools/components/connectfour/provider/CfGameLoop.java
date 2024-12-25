package de.hhn.it.devtools.components.connectfour.provider;

import de.hhn.it.devtools.apis.connectfour.ConnectFourGameLoop;
import de.hhn.it.devtools.apis.connectfour.ConnectFourService;

/**
 * This class implements the ConnectFourGameLoop interface.
 *
 * @author Nils
 */
public class CfGameLoop extends Thread implements ConnectFourGameLoop {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(CfGameLoop.class);
  private final ConnectFourService service;
  private final int sleepTime;
  private Loop state;

  /**
   * Constructor for the CfGameLoop class.
   *
   * @param service   The ConnectFourService object.
   * @param sleepTime The sleep time.
   */
  public CfGameLoop(final ConnectFourService service,
                    final int sleepTime) {
    super("Connect Four Game Loop");
    logger.debug("CfGameLoop(ConnectFourService service, int sleepTime)");
    this.service = service;
    this.sleepTime = sleepTime;
    this.state = Loop.RUNNING;
    this.start();
  }

  @Override
  public void interrupt() {
    super.interrupt();
  }

  @Override
  public void run() {
    logger.debug("run(): void");
    while (true) {
      switch (state) {
        case STOPPED:
          logger.debug("[STOPPED] Game loop stopped");
          this.idle();
          break;
        case RUNNING:
          /*
           * Unnecessary logging statement.
           * logger.info("[RUNNING] Update Game");x
           */
          this.update();
          break;
        case PAUSED:
          logger.info("[PAUSED] Game loop is paused");
          this.idle();
          break;
        default:
          logger.error("[STOPPED] Unknown Loop state: {}", state);
          throw new RuntimeException("Unknown Loop state: " + state);
      }
    }
  }

  private void update() {
    logger.debug("update(): void");
    service.updateGame();
    this.idle();
  }

  private void idle() {
    try {
      service.handleInput();
      Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
      logger.error("Game loop interrupted");
      throw new RuntimeException("Game loop interrupted", e);
    }
  }

  @Override
  public void pauseGame() {
    logger.debug("pauseGame(): void");
    this.state = Loop.PAUSED;
  }

  @Override
  public void resumeGame() {
    logger.debug("resumeGame(): void");
    this.state = Loop.RUNNING;
  }

  @Override
  public void endGame() {
    logger.debug("endGame(): void");
    this.state = Loop.STOPPED;
  }

  @Override
  public boolean isRunning() {
    logger.debug("isRunning(): boolean");
    return state == Loop.RUNNING;
  }

  @Override
  public boolean isPaused() {
    logger.debug("isPaused(): boolean");
    return state == Loop.PAUSED;
  }

  @Override
  public boolean isStopped() {
    logger.debug("isStopped(): boolean");
    return state == Loop.STOPPED;
  }

  private enum Loop {
    RUNNING, PAUSED, STOPPED
  }
}

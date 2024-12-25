package de.hhn.it.devtools.components.connectfour;

import de.hhn.it.devtools.apis.connectfour.ConnectFourBoard;
import de.hhn.it.devtools.apis.connectfour.ConnectFourColor;
import de.hhn.it.devtools.apis.connectfour.ConnectFourGameLoop;
import de.hhn.it.devtools.apis.connectfour.ConnectFourService;
import de.hhn.it.devtools.components.connectfour.provider.CfGameLoop;
import de.hhn.it.devtools.components.connectfour.provider.CfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TestCfGameLoop {
  private ConnectFourService service;
  private ConnectFourGameLoop gameLoop;

  @BeforeEach
  public void setup() {
    service = new CfService();
  }

  /**
   * Test the run method of the CfGameLoop class.
   * A new game loop is created and automatically started.
   * The test checks if the game loop is running, paused and stopped correctly.
   * The test also checks if the thread name is correct.
   * Fails if an exception is thrown.
   *
   * @see CfGameLoop#run()
   */
  @Test
  public void testRun() {
    try {
      gameLoop = new CfGameLoop(service, 500);
      assertEquals(gameLoop.getName(), "Connect Four Game Loop");
      assertTrue(gameLoop.isRunning());
      gameLoop.pauseGame();
      assertTrue(gameLoop.isPaused());
      gameLoop.resumeGame();
      assertTrue(gameLoop.isRunning());
      gameLoop.endGame();
      assertTrue(gameLoop.isStopped());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test the update method of the CfGameLoop class.
   * The test checks if the CfBoard is updated correctly
   * after a disc is inserted into the board.
   * Fails if an exception is thrown.
   * The test is disabled because the GameBoard gravity is not implemented yet.
   */
  @Test
  public void testUpdate() {
    gameLoop = new CfGameLoop(service, 500);
    ConnectFourBoard board = service.getBoard();
    try {
      ConnectFourColor playerColor = service.getCurrentPlayer().getColor();
      assertEquals(ConnectFourColor.EMPTY, board.getCell(board.getSize() -1, 0));
      service.placeDisc(0);
      gameLoop.pauseGame();
      assertTrue(gameLoop.isPaused());
      assertEquals(playerColor, board.getCell(board.getSize() -1, 0));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }
}

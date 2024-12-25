package de.hhn.it.devtools.components.connectfour;

import de.hhn.it.devtools.apis.connectfour.ConnectFourBoard;
import de.hhn.it.devtools.apis.connectfour.ConnectFourColor;
import de.hhn.it.devtools.apis.connectfour.ConnectFourListener;
import de.hhn.it.devtools.apis.connectfour.ConnectFourPlayer;
import de.hhn.it.devtools.apis.connectfour.ConnectFourService;
import de.hhn.it.devtools.apis.exceptions.IllegalParameterException;
import de.hhn.it.devtools.components.connectfour.provider.CfService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestCfService {
  ConnectFourService service;

  @BeforeEach
  public void setup() {
    service = new CfService();
  }

  /**
   * Test if Player switches correctly.
   * TODO: Implement testSwitchPlayer
   */
  @Test
  public void testSwitchPlayer() {
    ConnectFourPlayer tmp = service.getCurrentPlayer();
    service.switchPlayer();
    assertNotEquals(tmp, service.getCurrentPlayer());
  }

  /**
   * Test if the board rotates and updates correctly.
   * TODO: Implement testRotateBoard
   */
  @Test
  public void testRotateBoard() {
    ConnectFourBoard tmp = service.getBoard();
    tmp.setCell(0, 0, ConnectFourColor.RED);
    tmp.setCell(0, 1, ConnectFourColor.RED);
    tmp.setCell(1, 0, ConnectFourColor.RED);
    tmp.setCell(1, 1, ConnectFourColor.RED);
    tmp.setCell(0, 4, ConnectFourColor.YELLOW);
    tmp.setCell(0, 5, ConnectFourColor.YELLOW);
    tmp.setCell(0, 6, ConnectFourColor.YELLOW);
    try {
      service.rotateBoard();
    } catch (Exception e) {
      fail(e.getMessage());
    }
    ConnectFourColor[][] rotatedBoard = tmp.getBoard();
    assertEquals(rotatedBoard[5][5], ConnectFourColor.RED);
    assertEquals(rotatedBoard[6][5], ConnectFourColor.RED);
    assertEquals(rotatedBoard[2][6], ConnectFourColor.RED);
    assertEquals(rotatedBoard[3][6], ConnectFourColor.RED);
    assertEquals(rotatedBoard[4][6], ConnectFourColor.YELLOW);
    assertEquals(rotatedBoard[5][6], ConnectFourColor.YELLOW);
    assertEquals(rotatedBoard[6][6], ConnectFourColor.YELLOW);
  }

  /**
   * Test if the board updates correctly
   * after placing a disc.
   * TODO: Implement testUpdateBoard
   */
  @Test
  public void testUpdateBoard() {
    try {
      ConnectFourBoard board = service.getBoard();
      ConnectFourColor playerColor = service.getCurrentPlayer().getColor();
      service.placeDisc(0);
      assertEquals(playerColor, board.getCell( board.getSize() - 1, 0));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test if reset works correctly.
   * TODO: Implement testReset
   */
  @Test
  public void testReset() {
    try {
      service.placeDisc(0);
      service.reset();
      assertEquals(ConnectFourColor.EMPTY, service.getBoard().getCell(0, 0));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test if save throws an UnsupportedOperationException.
   * TODO: Implement testSaveGame
   *
   * @see CfService#saveGame()
   */
  @Test
  public void testSaveGame() {
    try {
      service.saveGame();
      fail("No exception thrown");
    } catch (Exception e) {
      assertInstanceOf(UnsupportedOperationException.class, e);
    }
  }

  /**
   * Test if load throws an UnsupportedOperationException.
   * TODO: Implement testLoadGame
   *
   * @see CfService#loadGame()
   */
  @Test
  public void testLoadGame() {
    try {
      service.loadGame();
      fail("No exception thrown");
    } catch (Exception e) {
      assertInstanceOf(UnsupportedOperationException.class, e);
    }
  }

  /**
   * Test addListener and removeListener.
   */
  @Test
  public void testAddListenerGood() {
    try {
      service.addListener(new ConnectFourListener() {
        /**
         * Informs the listener that the board has changed.
         *
         * @param board   ConnectFourBoard
         */
        @Override
        public void updateBoard(ConnectFourBoard board) {}
        @Override
        public void switchPlayer(ConnectFourPlayer player) {}
        @Override
        public void handleInput(String input) {}
        @Override
        public void gameIsOver(ConnectFourColor color) {}
        @Override
        public void boardRotated() {}
      });
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test addListener with null.
   */
  @Test
  public void testAddListenerBad() {
    try {
      service.addListener(null);
      fail("No exception thrown");
    } catch (Exception e) {
      assertInstanceOf(IllegalArgumentException.class, e);
    }
  }

  /**
   * Test removeListener.
   */
  @Test
  public void testRemoveListener() {
    try {
      ConnectFourListener listener = new ConnectFourListener() {
        /**
         * Informs the listener that the board has changed.
         *
         * @param board   ConnectFourBoard
         */
        @Override
        public void updateBoard(ConnectFourBoard board) {}
        @Override
        public void switchPlayer(ConnectFourPlayer player) {}
        @Override
        public void handleInput(String input) {}
        @Override
        public void gameIsOver(ConnectFourColor color) {}
        @Override
        public void boardRotated() {}
      };
      service.addListener(listener);
      service.removeListener(listener);
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test starting the game.
   */
  @Test
  public void testStartGame() {
    try {
      service = new CfService();
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test if win is detected correctly.
   */
  @Test
  public void testWin() {
    try {
      ConnectFourBoard board = service.getBoard();
      for (int i = 0; i <= 4; i++) {
        board.setCell(0, i, ConnectFourColor.RED);
      }
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test if draw is detected correctly.
   */
  @Test
  public void testDraw() {
    int count = 0;
    try {
      service = new CfService();
      for (int j = 0; j < 7; j++) {
        service.placeDisc(j);
        count++;
      }
      service.placeDisc(5);
      count++;

      for (int j = 0; j < 5; j++) {
        service.placeDisc(j);
        count++;
      }
      service.placeDisc(0);
      service.placeDisc(6);
      count += 2;

      for (int j = 2; j < 7; j++) {
        service.placeDisc(j);
        count++;
      }
      service.placeDisc(1);
      service.placeDisc(2);
      count += 2;

      for (int j = 3; j < 7; j++) {
        service.placeDisc(j);
        count++;
      }

      service.placeDisc(1);
      service.placeDisc(0);
      count += 2;
      //
      for (int j = 0; j < 7; j++) {
        service.placeDisc(j);
        count++;
      }
      service.placeDisc(5);
      count++;

      for (int j = 0; j < 5; j++) {
        service.placeDisc(j);
        count++;
      }
      service.placeDisc(0);
      service.placeDisc(6);
      count += 2;

      for (int j = 2; j < 7; j++) {
        service.placeDisc(j);
        count++;
      }
      service.placeDisc(1);
      service.placeDisc(2);
      count += 2;

      for (int j = 3; j < 7; j++) {
        service.placeDisc(j);
        count++;
      }

      service.placeDisc(1);
      service.placeDisc(0);
      count += 2;
      //
      for (int j = 2; j < 7; j++) {
        service.placeDisc(j);
        count++;
      }
    }
    catch (IllegalParameterException e) {
      System.out.println(e.getMessage());
      assertInstanceOf(IllegalParameterException.class, e);
      assertEquals(48, count);
    }
  }
}
package de.hhn.it.devtools.components.connectfour;

import de.hhn.it.devtools.apis.connectfour.ConnectFourColor;
import de.hhn.it.devtools.apis.connectfour.ConnectFourPlayer;
import de.hhn.it.devtools.components.connectfour.provider.CfPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.fail;

public class TestCfPlayer {
  private ConnectFourPlayer player1;
  private ConnectFourPlayer player2;

  @BeforeEach
  public void setup() {
    player1 = new CfPlayer(ConnectFourColor.RED);
    player2 = new CfPlayer(ConnectFourColor.YELLOW);
  }

  /**
   * Test the getColor method of the CfPlayer class.
   * The test checks if the color of the player is correct.
   * Fails if an exception is thrown.
   *
   * @see CfPlayer#getColor()
   */
  @Test
  public void testGetColor() {
    assertEquals(ConnectFourColor.RED, player1.getColor());
    assertEquals(ConnectFourColor.YELLOW, player2.getColor());
    assertNotSame(player1.getColor(), player2.getColor());
  }

  /**
   * Test the getNumberOfRemainingDiscs method of the CfPlayer class.
   * The test checks if the number of remaining discs is correct.
   * Fails if an exception is thrown.
   *
   * @see CfPlayer#getNumberOfRemainingDiscs()
   */
  @Test
  public void testGetNumberOfRemainingDiscs() {
    assertEquals(ConnectFourPlayer.DISCS, player1.getNumberOfRemainingDiscs());
    assertEquals(ConnectFourPlayer.DISCS, player2.getNumberOfRemainingDiscs());
  }

  /**
   * Test the removeDisc method of the CfPlayer class.
   * The test checks if a disc is removed from the player.
   * Fails if an exception is thrown.
   *
   * @see CfPlayer#removeDisc()
   */
  @Test
  public void testRemoveDisc() {
    try {
      player1.removeDisc();
      assertEquals(ConnectFourPlayer.DISCS - 1, player1.getNumberOfRemainingDiscs());
      player2.removeDisc();
      assertEquals(ConnectFourPlayer.DISCS - 1, player2.getNumberOfRemainingDiscs());
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test the removeDisc method of the CfPlayer class.
   * The test checks if an exception is thrown if the player has no discs left.
   * Fails if an exception is not thrown.
   *
   * @see CfPlayer#removeDisc()
   */
  @Test
  public void testRemoveDiscException() {
    try {
      for (int i = 0; i < ConnectFourPlayer.DISCS; i++) {
        player1.removeDisc();
      }
      player1.removeDisc();
      fail("No exception thrown");
    } catch (Exception e) {
      assertInstanceOf(IllegalArgumentException.class, e);
    }
  }

  /**
   * Test the constructor of the CfPlayer class.
   * The test checks if an exception is thrown if the color is not RED or YELLOW.
   * Fails if an exception is not thrown.
   *
   * @see CfPlayer#CfPlayer(ConnectFourColor)
   */
  @Test
  public void testConstructorException() {
    try {
      new CfPlayer(ConnectFourColor.EMPTY);
      fail("No exception thrown");
    } catch (Exception e) {
      assertInstanceOf(IllegalArgumentException.class, e);
    }
  }
}

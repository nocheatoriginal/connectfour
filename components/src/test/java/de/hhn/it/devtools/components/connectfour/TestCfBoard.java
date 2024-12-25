package de.hhn.it.devtools.components.connectfour;


import de.hhn.it.devtools.apis.connectfour.ConnectFourBoard;
import de.hhn.it.devtools.apis.connectfour.ConnectFourColor;
import de.hhn.it.devtools.components.connectfour.provider.CfBoard;
import de.hhn.it.devtools.components.connectfour.provider.CfConsoleInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCfBoard {
  ConnectFourBoard board;

  @BeforeEach
  public void setup() {
    board = new CfBoard();
  }

  /**
   * Test if the board is initialized correctly (every Tile is EMPTY).
   * The test checks if the board is initialized with the correct size.
   *
   * @see CfBoard#CfBoard()
   */
  @Test
  public void testBoardInitialization() {
    for (int i = 0; i < board.getSize(); i++) {
      for (int j = 0; j < board.getSize(); j++) {
        assertEquals(board.getCell(i, j), ConnectFourColor.EMPTY);
      }
    }
  }

  /**
   * Test if the board is updated correctly.
   * The test checks if the board is updated correctly after setting a cell.
   */
  @Test
  public void testUpdateBoard() {
    board.setCell(0, 0, ConnectFourColor.RED);
    assertEquals(board.getCell(0, 0), ConnectFourColor.RED);
  }

  /**
   * Test the gravity after selecting a column.
   */
  @Test
  public void testGravity() {
    // Call the selectColumn method
    board.selectColumn(0, ConnectFourColor.RED);

    // Check if the cell at the bottom of the column has the color you set
    assertEquals(ConnectFourColor.RED, board.getCell(board.getSize() - 1, 0));

    // Check if the original cell is now empty
    assertEquals(ConnectFourColor.EMPTY, board.getCell(0, 0));
  }

  /**
   * Test if invalid move is detected correctly.
   */
  // @Disabled
  @Test
  public void testInvalidMove() {
    // Test with valid colum
    assertTrue(board.isValidMove(0));

    // Test with invalid column
    assertFalse(board.isValidMove(-1));

    // Test with full column
    for (int i = 0; i < board.getSize(); i++) {
      board.setCell(0, i, ConnectFourColor.RED);
    }
    assertFalse(board.isValidMove(0));

    // Test with already occupied cell
    board.setCell(4, 0, ConnectFourColor.RED);
    try {
      board.setCell(4, 0, ConnectFourColor.YELLOW);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Cell is already occupied");
    }
  }

  /**
   * Test if invalid column is detected correctly.
   */
  @Test
  public void testInvalidColumn() {
    try {
      board.selectColumn(-1, ConnectFourColor.RED);
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Invalid column");
    }
  }

  /**
   * Test if board is rotated correctly.
   */
  // @Disabled
  @Test
  public void testRotateBoard() {
    board.setCell(0, 0, ConnectFourColor.RED);
    board.setCell(0, 1, ConnectFourColor.YELLOW);
    board.setCell(0, 2, ConnectFourColor.RED);
    board.setCell(0, 3, ConnectFourColor.YELLOW);
    board.setCell(0, 4, ConnectFourColor.RED);
    board.setCell(0, 5, ConnectFourColor.YELLOW);
    board.setCell(0, 6, ConnectFourColor.RED);
    board.rotateBoard();

    ConnectFourColor[][] rotatedBoard = board.getBoard();
    assertEquals(rotatedBoard[0][6], ConnectFourColor.RED);
    assertEquals(rotatedBoard[1][6], ConnectFourColor.YELLOW);
    assertEquals(rotatedBoard[2][6], ConnectFourColor.RED);
    assertEquals(rotatedBoard[3][6], ConnectFourColor.YELLOW);
    assertEquals(rotatedBoard[4][6], ConnectFourColor.RED);
    assertEquals(rotatedBoard[5][6], ConnectFourColor.YELLOW);
    assertEquals(rotatedBoard[6][6], ConnectFourColor.RED);
  }

  /**
   * Test if a horizontal win is detected correctly.
   */
  @Test
  public void testHorizontalWin() {
    for (int i = 0; i <= 4; i++) {
      board.setCell(0, i, ConnectFourColor.RED);
    }
    assertEquals(ConnectFourColor.RED, board.checkWin());
  }

  /**
   * Test if a vertical win is detected correctly.
   */
  @Test
  public void testVerticalWin() {
    for (int i = 0; i <= 4; i++) {
      board.setCell(i, 0, ConnectFourColor.RED);
    }
    assertEquals(ConnectFourColor.RED, board.checkWin());
  }

  /**
   * Test if a diagonal win is detected correctly.
   */
  @Test
  public void testDiagonalWin() {
    for (int i = 0; i <= 4; i++) {
      board.setCell(i, i, ConnectFourColor.RED);
    }
    assertEquals(ConnectFourColor.RED, board.checkWin());
  }

  /**
   * Test any possible diagonal win from left to right.
   * The board gets printed to the console.
   */
  @Test
  public void testDiagonalWinLeftToRight() {
    CfConsoleInterface ui = new CfConsoleInterface(null);
    int count = 0;
    for (int row = 0; row <= board.SIZE - 4; row++) {
      for (int col = 0; col <= board.SIZE - 4; col++) {
        board.reset();
        board.setCell(row, col, ConnectFourColor.RED);
        board.setCell(row + 1, col + 1, ConnectFourColor.RED);
        board.setCell(row + 2, col + 2, ConnectFourColor.RED);
        board.setCell(row + 3, col + 3, ConnectFourColor.RED);
        ui.printBoard(board.getBoard());
        assertEquals(ConnectFourColor.RED, board.checkWin());
        count++;
      }
    }
    assertEquals(16, count);
  }

  /**
   * Test any possible diagonal win from right to left.
   * The board gets printed to the console.
   */
  @Test
  public void testDiagonalWinRightToLeft() {
    CfConsoleInterface ui = new CfConsoleInterface(null);
    int count = 0;
    for (int row = 3; row < board.SIZE; row++) {
      for (int col = 0; col <= board.SIZE - 4; col++) {
        board.reset();
        board.setCell(row, col, ConnectFourColor.RED);
        board.setCell(row - 1, col + 1, ConnectFourColor.RED);
        board.setCell(row - 2, col + 2, ConnectFourColor.RED);
        board.setCell(row - 3, col + 3, ConnectFourColor.RED);
        ui.printBoard(board.getBoard());
        assertEquals(ConnectFourColor.RED, board.checkWin());
        count++;
      }
    }
    assertEquals(16, count);
  }

  /**
   * Test if EMPTY winner is detected correctly.
   */
  @Test
  public void testNoWinner() {
    assertEquals(ConnectFourColor.EMPTY, board.checkWin());
  }

  /**
   * Test valid move and gravity.
   */
  @Test
  public void testValidMove() {
    board.selectColumn(0, ConnectFourColor.RED);
    assertEquals(ConnectFourColor.RED, board.getCell(board.getSize() - 1, 0));
  }

  /**
   * Test if square of 4 discs is detected correctly.
   * The board gets printed to the console.
   */
  //@Disabled
  @Test
  public void testSquare() {
    CfConsoleInterface ui = new CfConsoleInterface(null);
    int count = 0;
    for (int row = 0; row <= board.SIZE - 2; row++) {
      for (int col = 0; col <= board.SIZE - 2; col++) {
        board.reset();
        board.setCell(row, col, ConnectFourColor.RED);
        board.setCell(row, col + 1, ConnectFourColor.RED);
        board.setCell(row + 1, col, ConnectFourColor.RED);
        board.setCell(row + 1, col + 1, ConnectFourColor.RED);
        ui.printBoard(board.getBoard());
        assertEquals(ConnectFourColor.RED, board.checkSquare(ConnectFourColor.RED));
        count++;
      }
    }
    assertEquals(36, count);
  }

  /**
   * Test invalid square.
   * Check empty board and then invalid square.
   */
  @Test
  public void testNoSquare() {
    assertNotEquals(ConnectFourColor.RED, board.checkSquare(ConnectFourColor.RED));
    board.setCell(0, 0, ConnectFourColor.RED);
    board.setCell(0, 1, ConnectFourColor.RED);
    board.setCell(1, 0, ConnectFourColor.RED);
    board.setCell(1, 1, ConnectFourColor.YELLOW);
    assertNotEquals(ConnectFourColor.RED, board.checkSquare(ConnectFourColor.RED));
  }
}
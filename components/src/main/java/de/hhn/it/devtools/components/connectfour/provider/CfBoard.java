package de.hhn.it.devtools.components.connectfour.provider;

import de.hhn.it.devtools.apis.connectfour.ConnectFourBoard;
import de.hhn.it.devtools.apis.connectfour.ConnectFourColor;

/**
 * Implementation of the ConnectFourBoard interface.
 *
 * @author Johannes
 */
public class CfBoard implements ConnectFourBoard {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(CfBoard.class);
  private ConnectFourColor[][] board;

  /**
   * Constructor for the ConnectFourBoard.
   */
  public CfBoard() {
    logger.debug("CfBoard()");
    board = new ConnectFourColor[SIZE][SIZE];
    this.reset();
  }

  /**
   * Resets the board.
   */
  public void reset() {
    logger.debug("reset(): void");
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        board[i][j] = ConnectFourColor.EMPTY;
      }
    }
  }

  /**
   * Returns the game board.
   *
   * @return ConnectFourColor[][] game board
   */
  @Override
  public ConnectFourColor[][] getBoard() {
    logger.debug("getBoard(): ConnectFourColor[][]");
    return this.board;
  }

  /**
   * Returns the value of a cell.
   *
   * @param row    row of the cell
   * @param column column of the cell
   * @return value of the cell
   */
  @Override
  public ConnectFourColor getCell(int row, int column) {
    logger.debug("getCell(int row, int column): ConnectFourColor");
    return board[row][column];
  }

  /**
   * Set value of a cell.
   *
   * @param row    row of the cell
   * @param column column of the cell
   * @param value  value of the cell
   * @throws IllegalArgumentException if the cell is already occupied
   */
  @Override
  public void setCell(int row, int column, ConnectFourColor value) throws IllegalArgumentException {
    logger.debug("setCell(int row, int column, ConnectFourColor value) "
        + "throws IllegalArgumentException: void");
    if (board[row][column] != ConnectFourColor.EMPTY) {
      throw new IllegalArgumentException("Cell is already occupied");
    }
    board[row][column] = value;
  }

  /**
   * Select a column.
   *
   * @param column selected column
   */
  @Override
  public void selectColumn(int column, ConnectFourColor value) {
    logger.debug("selectColumn(int column, ConnectFourColor value): void");
    // Check if the move is valid
    if (!isValidMove(column)) {
      throw new IllegalArgumentException("Invalid column");
    }

    // Find the lowest empty cell in the column
    int row = SIZE - 1;
    while (row >= 0 && getCell(row, column) != ConnectFourColor.EMPTY) {
      row--;
    }

    setCell(row, column, value);
  }

  /**
   * Check if move is valid.
   *
   * @param column selected column
   * @return true if the move is valid. Otherwise, false.
   */
  @Override
  public boolean isValidMove(int column) {
    logger.debug("isValidMove(int column): boolean");
    // Check if the column is within the range of the board size
    if (column < 0 || column >= SIZE) {
      return false;
    }

    // Check if the top cell of the column is empty
    return getCell(0, column) == ConnectFourColor.EMPTY;
  }

  /**
   * Rotate the board by 90 degrees clockwise.
   */
  @Override
  public void rotateBoard() {
    logger.debug("rotateBoard(): void");
    board = rotate90Clockwise(board);

    // Apply gravity to the rotated board
    for (int i = 0; i < SIZE; i++) {
      for (int j = SIZE - 2; j >= 0; j--) {
        if (board[j][i] != ConnectFourColor.EMPTY) {
          int k = j;
          while (k + 1 < SIZE && board[k + 1][i] == ConnectFourColor.EMPTY) {
            board[k + 1][i] = board[k][i];
            board[k][i] = ConnectFourColor.EMPTY;
            k++;
          }
        }
      }
    }
  }

  /**
   * Rotate the board by 90 degrees clockwise.
   *
   * @param board the board to rotate
   * @return the rotated board
   */
  private ConnectFourColor[][] rotate90Clockwise(ConnectFourColor[][] board) {
    logger.debug("rotate90Clockwise(ConnectFourColor[][] board): ConnectFourColor[][]");
    ConnectFourColor[][] rotatedBoard = new ConnectFourColor[SIZE][SIZE];
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        rotatedBoard[j][SIZE - i - 1] = board[i][j];
      }
    }
    return rotatedBoard;
  }

  /**
   * Check if the game is won.
   *
   * @return the color of the winner. Otherwise, EMPTY.
   */
  @Override
  public ConnectFourColor checkWin() {
    logger.debug("checkWin(): ConnectFourColor");
    // Überprüfen horizontal
    for (ConnectFourColor[] connectFourColors : board) {
      for (int col = 0; col <= connectFourColors.length - 4; col++) {
        if (connectFourColors[col] != ConnectFourColor.EMPTY
            && connectFourColors[col] == connectFourColors[col + 1]
            && connectFourColors[col] == connectFourColors[col + 2]
            && connectFourColors[col] == connectFourColors[col + 3]) {
          return connectFourColors[col];
        }
      }
    }

    // Überprüfen vertikal
    for (int col = 0; col < board[0].length; col++) {
      for (int row = 0; row <= board.length - 4; row++) {
        if (board[row][col] != ConnectFourColor.EMPTY
            && board[row][col] == board[row + 1][col]
            && board[row][col] == board[row + 2][col]
            && board[row][col] == board[row + 3][col]) {
          return board[row][col];
        }
      }
    }

    // Überprüfen diagonal (von links oben nach rechts unten)
    for (int row = 0; row <= board.length - 4; row++) {
      for (int col = 0; col <= board[row].length - 4; col++) {
        if (board[row][col] != ConnectFourColor.EMPTY
            && board[row][col] == board[row + 1][col + 1]
            && board[row][col] == board[row + 2][col + 2]
            && board[row][col] == board[row + 3][col + 3]) {
          return board[row][col];
        }
      }
    }

    // Überprüfen diagonal (von links unten nach rechts oben)
    for (int row = 3; row < board.length; row++) {
      for (int col = 0; col <= board[row].length - 4; col++) {
        if (board[row][col] != ConnectFourColor.EMPTY
            && board[row][col] == board[row - 1][col + 1]
            && board[row][col] == board[row - 2][col + 2]
            && board[row][col] == board[row - 3][col + 3]) {
          return board[row][col];
        }
      }
    }

    // Wenn kein Gewinner gefunden wurde
    return ConnectFourColor.EMPTY;
  }

  /**
   * Check if the board contains a square of four discs of the same color.
   *
   * @return color if square found. Otherwise, EMPTY.
   */
  @Override
  public ConnectFourColor checkSquare(ConnectFourColor color) {
    logger.debug("checkSquare(ConnectFourColor color): ConnectFourColor");
    for (int row = 0; row <= SIZE - 2; row++) {
      for (int col = 0; col <= SIZE - 2; col++) {
        if (board[row][col] == color
            && board[row][col] == board[row][col + 1]
            && board[row][col] == board[row + 1][col]
            && board[row][col] == board[row + 1][col + 1]) {
          return board[row][col];
        }
      }
    }
    return ConnectFourColor.EMPTY;
  }
}
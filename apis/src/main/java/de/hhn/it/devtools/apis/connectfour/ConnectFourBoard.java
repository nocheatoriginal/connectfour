package de.hhn.it.devtools.apis.connectfour;

/**
 * Interface for the ConnectFourBoard.
 */
public interface ConnectFourBoard {
  /**
   * Constants for the game board.
   */
  int SIZE = 7;

  /**
   * Resets the board.
   */
  void reset();

  /**
   * Returns the SIZE.
   *
   * @return int SIZE
   */
  default int getSize() {
    return SIZE;
  }

  /**
   * Returns the game board.
   *
   * @return ConnectFourColor[][] game board
   */
  ConnectFourColor[][] getBoard();

  /**
   * Returns the value of a cell.
   *
   * @param row    row of the cell
   * @param column column of the cell
   * @return int value of the cell
   */
  ConnectFourColor getCell(int row, int column);

  /**
   * Set value of a cell.
   *
   * @param row    row of the cell
   * @param column column of the cell
   * @param value  value of the cell
   * @throws IllegalArgumentException if the cell is already occupied
   */
  void setCell(int row, int column, ConnectFourColor value)
      throws IllegalArgumentException;


  /**
   * Select a column.
   *
   * @param column selected column
   * @param color  color of the player
   */
  void selectColumn(int column, ConnectFourColor color);

  /**
   * Checks if the move is valid.
   *
   * @param column selected column
   * @return true if the move is valid. Otherwise, false.
   */
  boolean isValidMove(int column);

  /**
   * Rotate board by 90 degrees to make
   * the game more interesting.
   */
  void rotateBoard();

  /**
   * Check win.
   *
   * @return the color of the winner
   */
  ConnectFourColor checkWin();

  /**
   * Check if the board contains a square of four discs of the same color.
   *
   * @return color if square found. Otherwise, EMPTY.
   * @param color color of the player
   */
  ConnectFourColor checkSquare(ConnectFourColor color);
}
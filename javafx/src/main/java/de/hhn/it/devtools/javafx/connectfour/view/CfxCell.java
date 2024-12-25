package de.hhn.it.devtools.javafx.connectfour.view;

import de.hhn.it.devtools.apis.connectfour.ConnectFourColor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * The CfxCell class represents a cell in the Connect Four game.
 *
 * @author Nils
 */
public class CfxCell extends Pane {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(CfxCell.class);
  private ConnectFourColor color;
  private Shape shape;

  /**
   * Constructor for the CfxCell class.
   *
   * @param color ConnectFourColor
   */
  public CfxCell(ConnectFourColor color) {
    this.setShape(CfxShape.CIRCLE, color);
  }

  /**
   * Return the fx color.
   *
   * @return fxColor Color
   */
  private Color getFxColor() {
    return switch (this.color) {
      case RED -> Color.RED;
      case YELLOW -> Color.YELLOW;
      case TRANSPARENT -> Color.TRANSPARENT;
      case SPECIAL -> Color.BLUE;
      default -> Color.WHITE;
    };
  }

  /**
   * Set the color of the cell.
   *
   * @param color ConnectFourColor
   */
  public void setColor(ConnectFourColor color) {
    this.color = color;
    this.shape.setFill(this.getFxColor());
  }

  /**
   * Return the color of the cell.
   *
   * @return color ConnectFourColor
   */
  public ConnectFourColor getColor() {
    return this.color;
  }

  /**
   * Set the shape of the cell.
   */
  public void setShape(CfxShape shape, ConnectFourColor color) {
    this.color = color;
    switch (shape) {
      case CIRCLE -> this.shape = new Circle(18, this.getFxColor());
      case TRIANGLE_L -> {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
            22.0, -22.0,
            22.0, 22.0,
            -12.0, 2.0
        );
        triangle.setFill(this.getFxColor());
        this.shape = triangle;
      }
      case TRIANGLE_R -> {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
            -22.0, -22.0,
            -22.0, 22.0,
            12.0, 2.0
        );
        triangle.setFill(this.getFxColor());
        this.shape = triangle;
      }
      default -> {
        logger.error("Invalid shape: {}", shape);
      }
    }
    getChildren().add(this.shape);
  }
}

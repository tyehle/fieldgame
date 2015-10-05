package ui

import java.awt.{Graphics2D, Color}

import state.{Line, Position}

/**
 * Represents a camera that can draw lines on the screen
 * @author Tobin Yehle
 */
trait Camera {
  /**
   * This should be called before every render frame so the camera can keep track of debug information about the frame.
   */
  def beginFrame(): Unit

  /**
   * Gets debug information about the previous frame for a UI to display
   * @return Whatever debug information is appropriate.
   */
  def frameInformation: String


  /**
   * Transforms a color at a point before displaying it.
   *
   * If the output is None, then this point should not be rendered. This allows for effects like fog.
   * @param point The point the color exists at in the world
   * @param color The true color of the point in the world
   * @return The color that should be rendered at this point.
   */
  def transformColor(point: Position, color: Color): Option[Color]

  /**
   * Transforms a point in the world to a point on the screen.
   * @param position The position to transform
   * @return The coordinates on the screen
   */
  def screenSpace(position: Position): (Int, Int)

  /**
   * Draws a line on the screen.
   * @param g The graphics object to draw on
   * @param line The line to render
   * @param color The color of the line to be rendered
   */
  def drawLine(g: Graphics2D, line: Line, color: Color): Unit
}

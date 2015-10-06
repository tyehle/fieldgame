package ui

import java.awt.Color

import state.Position

/**
 * Does preprocessing on colors before rendering them to the screen.
 * @author Tobin Yehle
 */
trait Medium {
  /**
   * Gets the color that should be rendered, or None if the object should not be rendered.
   * @param camera The position of the observer
   * @param point The object being viewed
   * @param color The true color of the object being viewed
   * @return The perceived color of the object, or None if it should not be rendered
   */
  def getColor(camera: Position, point: Position, color: Color): Option[Color]
}

package ui

import java.awt.{Color, Graphics2D}

import state.{Line, Position}

/**
 * Represents an object that can be drawn on the screen.
 *
 * This implementation only allows rendering wireframes, so the list of edges fully defines the object.
 * @author Tobin Yehle
 */
trait Renderable {
  /** The edges defining the object */
  def edges: Set[Line]
  /** The location of the object */
  def location: Position
  /** The true color of the object */
  def color: Color

  /**
   * Renders the object by drawing every line in its set of edges.
   * @param g The graphics object to draw on
   * @param camera The camera viewing the object
   */
  def render(g:Graphics2D, camera: Camera) = {
    camera.transformColor(location, color) match {
      case None =>
      case Some(c) => edges.foreach(line => camera.drawLine(g, line, c))
    }
  }
}

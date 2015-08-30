package ui

import java.awt.{Color, Graphics2D}

import state.{Line, Position}

/**
 * @author Tobin Yehle
 */
trait Renderable {
  def edges: Set[Line]
  def location: Position
  def color: Color

  /**
   *
   * @param g The graphics object to draw on
   * @param camera
   */
  def render(g:Graphics2D, camera: LogarithmicCamera) = {
    camera.transformColor(location, color) match {
      case None =>
      case Some(c) => edges.foreach(line => camera.drawLine(g, line, c))
    }
  }
}

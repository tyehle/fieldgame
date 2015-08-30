package ui

import java.awt.{Color, Graphics2D}

import state.Position

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
   * @param center The position of the center of the screen
   * @param camera
   * @param scale Pixels per radian
   */
  def render(g:Graphics2D, center:(Int, Int), camera: LogarithmicCamera, scale: Double):Boolean = {
    camera.transformColor(location, color) match {
      case None => false
      case Some(c) =>
        g.setColor(c)
        edges.foreach(line => camera.drawLine(g, line, center, scale))
        true
    }
  }
}

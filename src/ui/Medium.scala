package ui

import java.awt.Color

import state.Position

/**
 * @author Tobin Yehle
 */
trait Medium {
  def getColor(camera: Position, point: Position, color: Color): Option[Color]
}

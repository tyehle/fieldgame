package state

import java.awt.Color

import ui.Renderable

/**
 * @author Tobin Yehle
 */
case class Image(edges: Set[Line], location: Position, color: Color) extends Renderable

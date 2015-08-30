package ui

import java.awt.Color

import state.Position

/**
 * @author Tobin Yehle
 */
case class Image(edges: Set[Line], location: Position, color: Color) extends Renderable

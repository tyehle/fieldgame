package state

import ui.Renderable

/**
 * @author Tobin Yehle
 */
case class Image(master: Renderable, offset: Position) extends Renderable {
  override def edges:Set[Line] = master.edges.map(_.offset(offset))
  override def location: Position = master.location + offset
  override def color = master.color
}

package state

/**
 *
 * @author Tobin Yehle
 */

import java.awt.Color

import ui.{Line, Renderable}

class Block(var location:Position, var size:Position, val color: Color) extends Renderable {
  def isLethal: Boolean = true

  def contains(point: Position): Boolean = {
    point.x >= location.x && point.y >= location.y && point.z >= location.z &&
      point.x <= (location.x + size.x) && point.y <= (location.y + size.y) && point.z <= (location.z + size.z)
  }

  def edges: Set[Line] = {
    Set((false, false, false),
        (true, true, false),
        (true, false, true),
        (false, true, true)).flatMap{case (x, y, z) =>
      val start = location + size.component(x, y, z)
      Set(Line(start, start + size.component(x=true, y=false, z=false)*(if(x) -1 else 1), color),
          Line(start, start + size.component(x=false, y=true, z=false)*(if(y) -1 else 1), color),
          Line(start, start + size.component(x=false, y=false, z=true)*(if(z) -1 else 1), color))
    }
  }
}

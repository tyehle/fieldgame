package state

/**
 *
 * @author Tobin Yehle
 */

import java.awt.{Graphics2D, Color}

import ui.{LogarithmicCamera, Renderable}

class Block(var location:Position, var size:Position, val color: Color) extends Renderable {
  val isLethal: Boolean = false

  def updateState(elapsed: Long) = {  }

  def contains(point: Position): Boolean = {
    point.x >= location.x && point.y >= location.y && point.z >= location.z &&
      point.x <= (location.x + size.x) && point.y <= (location.y + size.y) && point.z <= (location.z + size.z)
  }

  private val mesh: Set[Line] = {
    Set((false, false, false),
        (true, true, false),
        (true, false, true),
        (false, true, true)).flatMap
    {
      case (x, y, z) =>
        val start = size.component(x, y, z)
        Set(Line(start, start + size.component(x=true, y=false, z=false)*(if(x) -1 else 1)),
            Line(start, start + size.component(x=false, y=true, z=false)*(if(y) -1 else 1)),
            Line(start, start + size.component(x=false, y=false, z=true)*(if(z) -1 else 1)))
    }
  }
  def edges: Set[Line] = mesh.map(_.offset(location))
  
  val images: Set[Image] = {
    val offsets = (-1 to 1) flatMap (x => (-1 to 1) flatMap (y => (-1 to 1) collect
      {
        case z if (x,y,z) != (0,0,0) => Position(GameState.bounds.x * x,
                                                 GameState.bounds.y * y,
                                                 GameState.bounds.z * z)
      }))

    offsets.toSet map { offset:Position => Image(this, offset) }
  }

  override def render(g: Graphics2D, camera: LogarithmicCamera) = {
    super.render(g, camera)
    images.foreach(_.render(g, camera))
  }
}

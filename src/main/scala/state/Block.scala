package state

import java.awt.{Graphics2D, Color}

import ui.{Camera, Renderable}

/**
 * Represents a block in the game world.
 * @author Tobin Yehle
 */
class Block(var location:Position, var size:Position, val color: Color) extends Renderable {
  val isLethal: Boolean = false

  def updateState(elapsed: Long) = {  }

  def contains(point: Position): Boolean = {
    point.x >= location.x && point.y >= location.y && point.z >= location.z &&
      point.x <= (location.x + size.x) && point.y <= (location.y + size.y) && point.z <= (location.z + size.z)
  }

  /** The wireframe of this box. The edges of this box at any time will be a translation of these edges. */
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
  /** The edges that define this box at this time */
  def edges: Set[Line] = mesh.map(_.offset(location))

  /** The images of this block that appear outside the bounds of the world. */
  val images: Set[Image] = {
    val offsets = (-1 to 1) flatMap (x => (-1 to 1) flatMap (y => (-1 to 1) collect
      {
        case z if (x,y,z) != (0,0,0) => Position(GameState.bounds.x * x,
                                                 GameState.bounds.y * y,
                                                 GameState.bounds.z * z)
      }))

    offsets.toSet map { offset:Position => Image(this, offset) }
  }

  /**
   * Renders this block on the given graphics object.
   * @param g The graphics object to draw on
   * @param camera The camera to use during rendering
   */
  override def render(g: Graphics2D, camera: Camera) = {
    super.render(g, camera)
    images.foreach(_.render(g, camera))
  }
}

package state

import java.awt.{Color, Graphics2D}

import ui.{Camera, Renderable}

import scala.util.Random

/**
 * Represents a block in the game world.
 * @author Tobin Yehle
 */

class Block(var location:Position, var size:Position, var velocity:Position, val color: Color) extends Renderable {
  /** If the player should be killed if they fly through this block */
  val isLethal: Boolean = false

  /**
   * Updates the state of this box after some time has passed.
   * @param elapsed The time since this method was lased called in nanoseconds
   */
  def updateState(elapsed: Long) = {
    location += velocity * elapsed
    location = location.wrap(GameState.bounds)
  }

  /**
   * Checks if a point is contained in the block. A point is considered inside even if it is on the boundary.
   * @param point The point to check
   * @return If the given point is in this block
   */
  def contains(point: Position): Boolean = {
    (point.x >= location.x && point.x <= (location.x + size.x) || point.x <= (location.x + size.x - GameState.bounds.x)) &&
    (point.y >= location.y && point.y <= (location.y + size.y) || point.y <= (location.y + size.y - GameState.bounds.y)) &&
    (point.z >= location.z && point.z <= (location.z + size.z) || point.z <= (location.z + size.z - GameState.bounds.z))
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

object Block {
  /**
   * Generates a random block within the given set of parameters.
   * @param sizeLimits (smallest, biggest) size of all dimensions of the generated box
   * @return The new random box
   */
  def apply(sizeLimits:(Int, Int) = (10, 30)) = {
    // generate a size within the given limits
    val size = Position(Random.nextInt(sizeLimits._2 - sizeLimits._1) + sizeLimits._1,
                        Random.nextInt(sizeLimits._2 - sizeLimits._1) + sizeLimits._1,
                        Random.nextInt(sizeLimits._2 - sizeLimits._1) + sizeLimits._1)
    // generate a random position in bounds
    val position = Position(Random.nextInt((GameState.bounds.x - size.x).toInt),
                            Random.nextInt((GameState.bounds.y - size.y).toInt),
                            Random.nextInt((GameState.bounds.z - size.z).toInt))
    val velocity = Position(Random.nextDouble()*4e-8 - 2e-8,
                            Random.nextDouble()*4e-8 - 2e-8,
                            Random.nextDouble()*4e-8 - 2e-8)
    new Block(position, size, velocity, Color.magenta)
  }
}

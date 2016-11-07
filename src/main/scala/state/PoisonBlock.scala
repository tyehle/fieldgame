package state

import java.awt.Color

import scala.util.Random

/**
 * @author Tobin Yehle
 */
class PoisonBlock(location:Position, size:Position, velocity:Position, color: Color)
  extends Block(location, size, velocity, color)

object PoisonBlock {

  /**
   * Generates a random block within the given set of parameters.
   * @param sizeLimits (smallest, biggest) size of all dimensions of the generated box
   * @return The new random box
   */
  def apply(sizeLimits:(Int, Int) = (10, 30)): PoisonBlock = {
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
    new PoisonBlock(position, size, velocity, Color.green)
  }
}

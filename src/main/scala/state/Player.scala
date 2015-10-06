package state

/**
 *
 * @author Tobin Yehle
 */
class Player(var location: Position,
             var forward: Position,
             var right: Position,
             var speed: Double,
             var rollRate: Double,
             var pitchRate: Double,
             var yawRate: Double) {
  /* Clarification of directions. +x = ->, +y = v, +z = X
   * This means the vector (1,0,0) points right
   *                       (0,1,0) points down
   *                       (0,0,1) points into the screen
   */

  def updateState(elapsed: Long): Unit = {
    move(elapsed)
    removeBlocks()
  }

  private def move(elapsed: Long): Unit = {
    pitch(pitchRate * elapsed)
    roll(rollRate * elapsed)
    yaw(yawRate * elapsed)
    location += forward * (speed * elapsed)
    location = location.wrap(GameState.bounds)
  }

  def pitch(theta: Double): Unit = {
    forward = forward.rotate(theta, right)
  }

  def yaw(theta: Double): Unit = {
    val axis = forward.cross(right)
    forward = forward.rotate(theta, axis)
    right = right.rotate(theta, axis)
  }

  /**
   * Positive is clockwise
   * @param theta The angle to roll
   */
  def roll(theta: Double): Unit = {
    right = right.rotate(theta, forward)
  }

  private def removeBlocks() = {
    val toRemove = GameState.blocks.indices.filter(i => GameState.blocks(i).contains(location)).sorted
    toRemove.indices.map(i => toRemove(i) + i).foreach(GameState.blocks.remove) // Relies on foreach going in order
  }

  override def toString: String = {
    "Player at "+location+", pointing "+forward+", going "+speed+", oriented right "+right
  }
}

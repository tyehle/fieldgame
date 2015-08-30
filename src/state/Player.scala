package state

/**
 *
 * @author Tobin Yehle
 */
class Player(var position: Position,
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

  def updateState(): Unit = {
    pitch(pitchRate)
    roll(rollRate)
    yaw(yawRate)
    position += forward * speed
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

  override def toString: String = {
    "Player at "+position+", pointing "+forward+", going "+speed+", oriented right "+right
  }
}

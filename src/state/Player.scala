package state

/**
 *
 * Created by Tobin on 4/4/2014.
 */
object Player {
  /* Clarification of directions. +x = ->, +y = v, +z = X
   * This means the vector (1,0,0) points right
   *                       (0,1,0) points down
   *                       (0,0,1) points into the screen
   */

  var position = new Vector3(0,0,0)
  var forward = new Vector3(0, 0, 1)
  var right = new Vector3(1, 0, 0)
  var speed = 1.0

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
   * @param theta
   */
  def roll(theta: Double): Unit = {
    right = right.rotate(theta, forward)
  }

  override def toString: String = {
    "Player at "+position+", pointing "+forward+", going "+speed+", oriented right "+right
  }
}

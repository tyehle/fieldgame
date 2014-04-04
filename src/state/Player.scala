package state

/**
 *
 * Created by Tobin on 4/4/2014.
 */
object Player {
  /* Clarification of directions. +x = X, +y = <, +z = ^
   * This means the vector (1,0,0) points directly into the screen,
   *                       (0,1,0) points straight left, and
   *                       (0,0,1) points straight up
   */

  var position = new Vector3(0,0,0)
  var speed = 1.0

  /**
   * Direction the player is pointing stored as (alt, az, roll)
   */
  var alt = 0.0
  var az = 0.0
  var roll = 0.0
}

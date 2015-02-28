package state

/**
 *
 * Created by Tobin on 4/4/2014.
 */

import java.awt.{Graphics2D, Color}

class Block(var size:Position, var position:Position) {
  def isLethal: Boolean = true

  /**
   *
   * @param g The graphics object to draw on
   * @param center The position of the center of the screen
   * @param scale Pixels per radian
   */
  def render(g:Graphics2D, center:(Int, Int), scale:Double) = {
    // TODO: do this for real

    g.setColor(Color.RED)

    val radius = 5

    val camera = new LogarithmicCamera(GameState.player.position,
                                       GameState.player.forward,
                                       GameState.player.right,
                                       2*math.Pi)

    camera.screenSpace(position, center, scale) match {
      case (Some(x), Some(y)) => g.fillOval(x - radius, y - radius, 2*radius, 2*radius)
      case _ => None
    }
  }
}

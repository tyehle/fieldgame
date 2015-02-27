package state

/**
 *
 * Created by Tobin on 4/4/2014.
 */

import java.awt.{Graphics2D, Color}

class Block(var size:Vector3, var position:Vector3) {
  def isLethal: Boolean = true

  /**
   *
   * @param g
   * @param center
   * @param scale Pixels per radian
   */
  def render(g:Graphics2D, center:(Int, Int), scale:Double) = {
    // TODO: do this for real

    g.setColor(Color.RED)

    // draw a circle in the right spot

    // from camera to block
    val toBlock = position - GameState.player.position
    val alpha = math.acos((toBlock o Player.forward) / toBlock.length)
    val planar = toBlock - Player.forward.scale(toBlock o Player.forward)
    val cosBeta = (planar o Player.right) / planar.length

//    val radius = size.length / 2
    val radius = 5

    val x = alpha * cosBeta
    val posy = alpha * math.sqrt(1 - cosBeta*cosBeta)

    val y = if(((planar * Player.right) o Player.forward) < 0) posy
            else -posy

    val top = (x*scale - radius).asInstanceOf[Int] + center._1
    val left = (y*scale - radius).asInstanceOf[Int] + center._2

    g.fillOval(top, left, 2*radius.asInstanceOf[Int], 2*radius.asInstanceOf[Int])
  }
}

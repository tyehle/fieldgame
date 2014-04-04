package state

/**
 *
 * Created by Tobin on 4/4/2014.
 */

import java.awt.Graphics2D
import java.awt.Color

class Block(var size:Vector3, var position:Vector3) {
  def isLethal: Boolean = true

  def render(g:Graphics2D) = {
    // TODO: do this for real

    g.setColor(Color.RED)

    // draw a circle in the right spot

    // from camera to block
    var toBlock = position - GameState.player.position
  }
}

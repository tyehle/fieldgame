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
  def render(g:Graphics2D, center:(Int, Int), camera: LogarithmicCamera, scale: Double) = {
    // TODO: do this for real

    val vertices = List(position,
                        position + size.component(x=true, y=true, z=false),
                        position + size.component(x=true, y=false, z=true),
                        position + size.component(x=false, y=true, z=true))

    g.setColor(Color.RED)

    val lines = List((false, false, false),
                     (true, true, false),
                     (true, false, true),
                     (false, true, true)).flatMap{case (x, y, z) =>
                                                                     val start = position + size.component(x, y, z)
        List((start, start + size.component(x=true, y=false, z=false)*(if(x) -1 else 1)),
             (start, start + size.component(x=false, y=true, z=false)*(if(y) -1 else 1)),
             (start, start + size.component(x=false, y=false, z=true)*(if(z) -1 else 1)))
                                                                     }
    lines.foreach{case (start, end) => camera.drawLine(g, start, end, center, scale)}
  }
}

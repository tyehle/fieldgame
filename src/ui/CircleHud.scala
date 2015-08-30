package ui

import java.awt.{Graphics2D, Color}

import state.{GameState, Position}

/**
 * @author Tobin Yehle
 */
class CircleHud(val color: Color = new Color(0x0066cc)) {
  def render(g:Graphics2D, camera: LogarithmicCamera) = {
    g.setColor(color)
    drawOval(g, camera.center, (math.Pi / 2 * camera.scale).asInstanceOf[Int])
    drawOval(g, camera.center, (math.Pi * camera.scale).asInstanceOf[Int])
    g.drawString("Speed: " + GameState.player.speed, 5, 17)
    g.drawString(s"Lines rendered: ${camera.linesRendered}", 5, 34)
  }

  private def drawOval(g: Graphics2D, center: (Int, Int), radius: Int): Unit = {
    g.drawOval(center._1 - radius, center._2 - radius, 2 * radius, 2 * radius)
  }
}

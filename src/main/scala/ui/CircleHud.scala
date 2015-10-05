package ui

import java.awt.{Graphics2D, Color}

import state.{GameState, Position}

/**
 * @author Tobin Yehle
 */
class CircleHud(camera: LogarithmicCamera, val color: Color = new Color(0x0066cc)) {
  private val textHeight = 17

  def render(g:Graphics2D) = {
    g.setColor(color)
    drawOval(g, camera.center, (math.Pi / 2 * camera.scale).asInstanceOf[Int])
    drawOval(g, camera.center, (math.Pi * camera.scale).asInstanceOf[Int])
    drawCrosshair(g, camera.center, 10)
    g.drawString("Speed: " + GameState.player.speed, 5, textHeight)
    g.drawString(camera.frameInformation, 5, textHeight*2)
    g.drawString(GameState.goal.message, 5, textHeight*3)
  }

  private def drawCrosshair(g: Graphics2D, center: (Int, Int), size: Int): Unit = {
    g.drawLine(center._1, center._2 - size, center._1, center._2 + size)
    g.drawLine(center._1 - size, center._2, center._1 + size, center._2)
  }

  private def drawOval(g: Graphics2D, center: (Int, Int), radius: Int): Unit = {
    g.drawOval(center._1 - radius, center._2 - radius, 2 * radius, 2 * radius)
  }
}

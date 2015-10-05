package ui

import java.awt.{Graphics2D, Color}

import state.{GameState, Position}

/**
 * @author Tobin Yehle
 */
class CircleHud(camera: LogarithmicCamera, val color: Color = new Color(0x0066cc)) {
  private val textHeight = 17

  /**
   * Renders the HUD on a given graphics object.
   * @param g The object to render the HUD onto.
   */
  def render(g:Graphics2D) = {
    g.setColor(color)
    drawCircle(g, camera.center, (math.Pi / 2 * camera.scale).asInstanceOf[Int])
    drawCircle(g, camera.center, (math.Pi * camera.scale).asInstanceOf[Int])
    drawCrosshair(g, camera.center, 10)
    g.drawString("Speed: " + GameState.player.speed, 5, textHeight)
    g.drawString(camera.frameInformation, 5, textHeight*2)
    g.drawString(GameState.goal.message, 5, textHeight*3)
  }

  /**
   * Draws a cross hair in the center of the screen.
   * @param g The graphics object to draw onto
   * @param center The location of the center of the screen
   * @param size The size of the cross hair
   */
  @inline private def drawCrosshair(g: Graphics2D, center: (Int, Int), size: Int): Unit = {
    g.drawLine(center._1, center._2 - size, center._1, center._2 + size)
    g.drawLine(center._1 - size, center._2, center._1 + size, center._2)
  }

  /**
   * Draws a circle centered at a point with a given radius.
   * @param g The graphics object to draw on
   * @param center The center of the circle
   * @param radius The radius of the circle
   */
  @inline private def drawCircle(g: Graphics2D, center: (Int, Int), radius: Int): Unit = {
    g.drawOval(center._1 - radius, center._2 - radius, 2 * radius, 2 * radius)
  }
}

package ui

import java.awt.{Color, Graphics2D}

import state.{Line, Position}

/**
 * Does a fish eye projection capable of full 360 fov.
 * @param position The position of the camera
 * @param forward The direction the camera is facing
 * @param right The direction directly to the right of the camera
 * @param medium The function that changes the color of objects based on their distance from the camera
 * @param center The center of the screen
 * @param initialFov The initial field of view of the camera
 * @author Tobin Yehle
 */
class LogarithmicCamera(var position: Position,
                        var forward: Position,
                        var right: Position,
                        medium: Medium,
                        val center: (Int, Int),
                        initialFov: Double) extends Camera {

  /** Scale of the screen space in pixels per radian */
  var scale = center._1.min(center._2) / initialFov
  def setFov(rads: Double) = { scale = center._1.min(center._2) / rads * 2 }

  private var linesRendered: Int = 0
  def beginFrame() = { linesRendered = 0 }
  def frameInformation = s"Lines rendered: $linesRendered"

  /**
   * This computation is pushed to the medium.
   * @param point The point the color exists at in the world
   * @param color The true color of the point in the world
   * @return The color that should be rendered at this point.
   */
  def transformColor(point: Position, color: Color): Option[Color] = medium.getColor(position, point, color)

  def screenSpace(position: Position): (Int, Int) = {
    val toPoint = position - this.position
    val alpha = math.acos(toPoint.normalized o forward)

    val beta = alpha / (toPoint - forward.scale(toPoint o forward)).length

    val x = (toPoint o right) * beta
    val y = (toPoint o (forward x right)) * beta

    ((x * scale).asInstanceOf[Int] + center._1,
      (y * scale).asInstanceOf[Int] + center._2)
  }

  /**
   * @return The distance between the two given points on the screen
   */
  @inline private def screenDistance(a: (Int, Int), b: (Int, Int)): Double = {
    math.sqrt((a._1 - b._1) * (a._1 - b._1) + (a._2 - b._2) * (a._2 - b._2))
  }

  /**
   * Draws the given line on the provided graphics object.
   *
   * The line is split into multiple sections so it looks smooth even at high distortion. The number of sections it is
   * split into depends on the distance between the start and end points of the line on the screen.
   *
   * The line is split evenly along its length, so if a camera is very close to the line then there will be points in
   * the middle that are very jagged. A possible fix for this would be to render a smooth line through a set of points,
   * but I have not found an easy way to do this.
   * @param g The graphics object to draw on
   * @param line The line to render
   * @param color The color of the line to be rendered
   */
  def drawLine(g: Graphics2D, line: Line, color: Color) = {
    // TODO: render the line smoothly
    val first = screenSpace(line.start)
    val last = screenSpace(line.end)

    val parts = 1 max (screenDistance(first, last) / 5).asInstanceOf[Int]
    val ts = 1 until parts map (index => index.toDouble / parts)
    val points = (ts map (t => screenSpace(line.start * (1 - t) + line.end * t))) :+ last

    for ((start, end) <- first +: points zip points if pointIsOnScreen(start) || pointIsOnScreen(end)) {
      linesRendered += 1
      g.synchronized { g.setColor(color); g.drawLine(start._1, start._2, end._1, end._2) }
    }
  }

  /**
   * Checks if a given point is on the screen.
   * @param p The point to check
   * @return If the point is on the screen
   */
  @inline def pointIsOnScreen(p: (Int, Int)): Boolean = {
    0 < p._1 && p._1 < (center._1 << 1) && 0 < p._2 && p._2 < (center._2 << 1)
  }
}

package ui

import java.awt.{Color, Graphics2D}

import state.Position

/**
 * 2/27/2015
 * @author Tobin Yehle
 */
class LogarithmicCamera(var position: Position, var forward: Position, var right: Position, medium: Medium) {

  def transformColor(point: Position, color: Color): Option[Color] = medium.getColor(position, point, color)

  def screenSpace(position: Position, center: (Int, Int), scale: Double): (Int, Int) = {
    val toPoint = position - this.position
    val alpha = math.acos(toPoint.normalized o forward)

    val beta = alpha / (toPoint - forward.scale(toPoint o forward)).length

    val x = (toPoint o right) * beta
    val y = (toPoint o (forward x right)) * beta

    ((x * scale).asInstanceOf[Int] + center._1,
      (y * scale).asInstanceOf[Int] + center._2)
  }

  private def screenDistance(a: (Int, Int), b: (Int, Int)): Double = {
    math.sqrt((a._1 - b._1) * (a._1 - b._1) + (a._2 - b._2) * (a._2 - b._2))
  }

  def drawLine(g: Graphics2D, line: Line, center: (Int, Int), scale: Double) = {
    val first = screenSpace(line.start, center, scale)
    val last = screenSpace(line.end, center, scale)

    val parts = 1 max (screenDistance(first, last) / 5).asInstanceOf[Int]
    val ts = 1 until parts map (index => index.toDouble / parts)
    val points = (ts map (t => screenSpace(line.start * (1 - t) + line.end * t, center, scale))) :+ last

    for ((start, end) <- first +: points zip points if lineIsOnScreen(start, end, center)) {
      g.drawLine(start._1, start._2, end._1, end._2)
    }
  }

  @inline def lineIsOnScreen(start: (Int, Int), end: (Int, Int), center: (Int, Int)): Boolean = {
    pointIsOnScreen(start, center) || pointIsOnScreen(end, center)
  }

  @inline def pointIsOnScreen(p: (Int, Int), center: (Int, Int)): Boolean = {
    0 < p._1 && p._1 < (center._1 << 1) && 0 < p._2 && p._2 < (center._2 << 1)
  }
}

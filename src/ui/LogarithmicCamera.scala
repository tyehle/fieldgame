package ui

import java.awt.{Color, Graphics2D}

import state.Position

/**
 * 2/27/2015
 * @author Tobin Yehle
 */
class LogarithmicCamera(var position: Position,
                        var forward: Position,
                        var right: Position,
                        medium: Medium,
                        val center: (Int, Int),
                        var scale: Double) {

  private var _linesRendered: Int = 0
  def resetRenderCount() = { _linesRendered = 0 }
  def linesRendered = _linesRendered

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

  private def screenDistance(a: (Int, Int), b: (Int, Int)): Double = {
    math.sqrt((a._1 - b._1) * (a._1 - b._1) + (a._2 - b._2) * (a._2 - b._2))
  }

  def drawLine(g: Graphics2D, line: Line) = {
    val first = screenSpace(line.start)
    val last = screenSpace(line.end)

    val parts = 1 max (screenDistance(first, last) / 5).asInstanceOf[Int]
    val ts = 1 until parts map (index => index.toDouble / parts)
    val points = (ts map (t => screenSpace(line.start * (1 - t) + line.end * t))) :+ last

    for ((start, end) <- first +: points zip points if lineIsOnScreen(start, end)) {
      _linesRendered += 1
      g.drawLine(start._1, start._2, end._1, end._2)
    }
  }

  @inline def lineIsOnScreen(start: (Int, Int), end: (Int, Int)): Boolean = {
    pointIsOnScreen(start) || pointIsOnScreen(end)
  }

  @inline def pointIsOnScreen(p: (Int, Int)): Boolean = {
    0 < p._1 && p._1 < (center._1 << 1) && 0 < p._2 && p._2 < (center._2 << 1)
  }
}

package state

import java.awt.Graphics2D

/**
 * 2/27/2015
 * @author Tobin Yehle
 */
class LogarithmicCamera(var position: Position, var forward: Position, var right: Position) {

//  def screenSpace(position: Position, center:(Int, Int), scale:Double): (Option[Int], Option[Int]) = {
//    // from camera to block
//    val toBlock = position - this.position
//    val alpha = math.acos((toBlock o forward) / toBlock.length)
//
//    if (alpha > fov) return (None, None)
//
//    val planar = toBlock - forward.scale(toBlock o forward)
//    val cosBeta = (planar o right) / planar.length
//
//    val x = alpha * cosBeta
//    val posy = alpha * math.sqrt(1 - cosBeta * cosBeta)
//
//    val y = if (((planar x right) o forward) < 0) posy
//    else -posy
//
//    val screenX = (x * scale).asInstanceOf[Int] + center._1
//    val screenY = (y * scale).asInstanceOf[Int] + center._2
//
//    (Some(screenX), Some(screenY))
//  }

  def screenSpace(position: Position, center: (Int, Int), scale: Double): (Int, Int) = {
    val toPoint = position - this.position
    val alpha = math.acos(toPoint.normalized() o forward)

    val beta = alpha / (toPoint - forward.scale(toPoint o forward)).length

    val x = (toPoint o right) * beta
    val y = (toPoint o (forward x right)) * beta

    ((x * scale).asInstanceOf[Int] + center._1,
     (y * scale).asInstanceOf[Int] + center._2)
  }

  private def screenDistance(a: (Int, Int), b: (Int, Int)): Double = {
    math.sqrt((a._1-b._1)*(a._1-b._1) + (a._2-b._2)*(a._2-b._2))
  }

  def drawLine(g: Graphics2D, start: Position, end: Position, center: (Int, Int), scale: Double) = {
    val first = screenSpace(start, center, scale)
    val last = screenSpace(end, center, scale)

    val parts = 1 max (screenDistance(first, last) / 5).asInstanceOf[Int]
    val ts = 1 until parts map (index => index.toDouble / parts)
    val points = (ts map (t => screenSpace(start*(1-t) + end*t, center, scale))) :+ last

    (first +: points zip points) foreach {case (a, b) => g.drawLine(a._1, a._2, b._1, b._2)}
  }

  def drawPartialLine(g: Graphics2D, start: Position, end: Position, center: (Int, Int), scale: Double) = {

  }
}

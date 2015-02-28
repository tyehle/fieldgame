package state

/**
 * 2/27/2015
 * @author Tobin Yehle
 */
class LogarithmicCamera(var position: Position, var forward: Position, var right: Position, var fov: Double) {

  def screenSpace(position: Position, center:(Int, Int), scale:Double): (Option[Int], Option[Int]) = {
    // from camera to block
    val toBlock = position - this.position
    val alpha = math.acos((toBlock o forward) / toBlock.length)

    if (alpha > fov) return (None, None)

    val planar = toBlock - forward.scale(toBlock o forward)
    val cosBeta = (planar o right) / planar.length

    val x = alpha * cosBeta
    val posy = alpha * math.sqrt(1 - cosBeta * cosBeta)

    val y = if (((planar x right) o forward) < 0) posy
    else -posy

    val screenX = (x * scale).asInstanceOf[Int] + center._1
    val screenY = (y * scale).asInstanceOf[Int] + center._2

    (Some(screenX), Some(screenY))
  }

  def screenSpace2(position: Position, center: (Int, Int), scale: Double): (Option[Int], Option[Int]) = {
    val toPoint = position - this.position
    val alpha = math.acos(toPoint.normalized() o forward)

    if(alpha <= fov) {
      val beta = alpha / (toPoint - forward.scale(toPoint o forward)).length

      val x = (toPoint o right) * beta
      val y = (toPoint o (forward x right)) * beta

      (Some((x * scale).asInstanceOf[Int] + center._1),
       Some((y * scale).asInstanceOf[Int] + center._2))
    } else {
      (None, None)
    }
  }
}

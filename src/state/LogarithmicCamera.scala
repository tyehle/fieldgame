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

    val y = if (((planar * right) o forward) < 0) posy
    else -posy

    val screenX = (x * scale).asInstanceOf[Int] + center._1
    val screenY = (y * scale).asInstanceOf[Int] + center._2

    (Some(screenX), Some(screenY))
  }
}

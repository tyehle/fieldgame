package test

import state.{Vector3, Player}

/**
 * 2/27/2015
 * @author Tobin Yehle
 */
object Tester {
  def main (args: Array[String]): Unit = {
    playerRotation()
  }

  def vectorRotation() = {
    val angle = math.Pi *2.0 / 3.0

    val dir = new Vector3(0, 0, 1)
    val axis = new Vector3(1, 1, 1).normalized()

    println(dir.rotate(angle, axis))
  }

  def playerRotation() = {
    val angle = math.Pi / 4

    println(Player)
    Player.pitch(angle)
    println(Player)
    Player.pitch(angle)
    println(Player)
  }
}

package test

import state.{GameState, Position}

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

    val dir = Position(0, 0, 1)
    val axis = Position(1, 1, 1).normalized()

    println(dir.rotate(angle, axis))
  }

  def playerRotation() = {
    val angle = math.Pi / 4

    println(GameState.player)
    GameState.player.pitch(angle)
    println(GameState.player)
    GameState.player.pitch(angle)
    println(GameState.player)
  }
}

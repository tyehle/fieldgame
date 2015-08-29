package test.state

import org.scalatest.{Matchers, FlatSpec}
import state.GameState

/**
 * @author Tobin Yehle
 */
class PlayerSpec extends FlatSpec with Matchers {
  "A player" should "pitch relative to its current orientation" in {
    val angle = math.Pi / 4

    println(GameState.player)
    GameState.player.pitch(angle)
    println(GameState.player)
    GameState.player.pitch(angle)
    println(GameState.player)
  }
}

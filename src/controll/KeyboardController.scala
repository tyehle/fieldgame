package controll

import java.awt.event.{KeyEvent, KeyListener}

import state.GameState

/**
 * @author Tobin Yehle
 */
class KeyboardController extends KeyListener {
  private val speed = math.Pi / 36.0
  private val accel = .1
  private val pitchSign = 1

  override def keyTyped(e: KeyEvent): Unit = {}

  override def keyPressed(e: KeyEvent): Unit = {
    e.getKeyCode match {
      case KeyEvent.VK_CONTROL => GameState.player.speed -= accel
      case KeyEvent.VK_SHIFT => GameState.player.speed += accel
      case KeyEvent.VK_W | KeyEvent.VK_UP    => GameState.player.pitchRate = -speed * pitchSign
      case KeyEvent.VK_S | KeyEvent.VK_DOWN  => GameState.player.pitchRate = speed * pitchSign
      case KeyEvent.VK_A | KeyEvent.VK_LEFT  => GameState.player.rollRate = -speed
      case KeyEvent.VK_D | KeyEvent.VK_RIGHT => GameState.player.rollRate = speed
      case _ =>
    }
  }

  override def keyReleased(e: KeyEvent): Unit = {
    e.getKeyCode match {
      case KeyEvent.VK_W | KeyEvent.VK_UP    => GameState.player.pitchRate = 0
      case KeyEvent.VK_S | KeyEvent.VK_DOWN  => GameState.player.pitchRate = 0
      case KeyEvent.VK_A | KeyEvent.VK_LEFT  => GameState.player.rollRate = 0
      case KeyEvent.VK_D | KeyEvent.VK_RIGHT => GameState.player.rollRate = 0
      case _ =>
    }
  }
}

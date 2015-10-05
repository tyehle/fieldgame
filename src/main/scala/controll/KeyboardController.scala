package controll

import java.awt.event.{KeyEvent, KeyListener}

import state.GameState

/**
 * Processes all input from they keyboard.
 * @author Tobin Yehle
 */
class KeyboardController extends KeyListener {
  // set up some globals that govern how the player will respond to control input
  private val angularSpeed = 5e-9
  private val accel = 1e-8
  private val pitchSign = 1 // set to -1 to invert pitch

  override def keyTyped(e: KeyEvent): Unit = {}

  /**
   * Called whenever a key is pushed. Used to accept control input from the keyboard.
   * @param e The event that triggered the call to this method
   */
  override def keyPressed(e: KeyEvent): Unit = {
    e.getKeyCode match {
      case KeyEvent.VK_CONTROL => GameState.player.speed -= accel
      case KeyEvent.VK_SHIFT => GameState.player.speed += accel
      case KeyEvent.VK_W | KeyEvent.VK_UP    => GameState.player.pitchRate = -angularSpeed * pitchSign
      case KeyEvent.VK_S | KeyEvent.VK_DOWN  => GameState.player.pitchRate = angularSpeed * pitchSign
      case KeyEvent.VK_A | KeyEvent.VK_LEFT  => GameState.player.rollRate = -angularSpeed
      case KeyEvent.VK_D | KeyEvent.VK_RIGHT => GameState.player.rollRate = angularSpeed
      case _ => // ignore every other key
    }
  }

  /**
   * Called whenever a key is released. Used to accept control input from the keyboard.
   * @param e The event that triggered the call to this method
   */
  override def keyReleased(e: KeyEvent): Unit = {
    e.getKeyCode match {
      case KeyEvent.VK_W | KeyEvent.VK_UP    => GameState.player.pitchRate = 0
      case KeyEvent.VK_S | KeyEvent.VK_DOWN  => GameState.player.pitchRate = 0
      case KeyEvent.VK_A | KeyEvent.VK_LEFT  => GameState.player.rollRate = 0
      case KeyEvent.VK_D | KeyEvent.VK_RIGHT => GameState.player.rollRate = 0
      case _ => // ignore every other key
    }
  }
}

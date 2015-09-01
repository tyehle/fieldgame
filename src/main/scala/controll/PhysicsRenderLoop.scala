package controll

import java.awt.{Color, Graphics2D}

import state.GameState
import ui.{GameWindow, LogarithmicCamera}

/**
 * @author Tobin Yehle
 */
object PhysicsRenderLoop extends Runnable {
  override def run(): Unit = {
    var lastLoopTime:Long = 0
    var loopTime = System.nanoTime() - (1e9/30).toLong

    while (!GameState.goal.completed) {
      lastLoopTime = loopTime
      loopTime = System.nanoTime()

      doPhysics(loopTime - lastLoopTime)

      val buffer = GameWindow.getBufferStrategy
      render(buffer.getDrawGraphics.asInstanceOf[Graphics2D], GameState.playerCamera)
      buffer.show()
    }

    GameWindow.setVisible(false)
    GameWindow.dispose()
  }

  def doPhysics(elapsed: Long): Unit = {
    GameState.player.updateState(elapsed)
    GameState.blocks.foreach(_.updateState(elapsed))

    GameState.goal.update(elapsed)

    // update camera position
    GameState.playerCamera.position = GameState.player.location
    GameState.playerCamera.forward = GameState.player.forward
    GameState.playerCamera.right = GameState.player.right
  }

  def render(g: Graphics2D, camera: LogarithmicCamera) {
    g.setColor(Color.black)
    g.fillRect(0, 0, GameWindow.getWidth, GameWindow.getHeight)

    GameState.hud.render(g, camera)

    camera.resetRenderCount()
    GameState.blocks.par.foreach(_.render(g, camera))
  }
}

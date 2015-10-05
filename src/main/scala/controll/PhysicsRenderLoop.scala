package controll

import java.awt.{Color, Graphics2D}

import state.GameState
import ui.{Camera, GameWindow, LogarithmicCamera}

/**
 * The thread that runs the game.
 * @author Tobin Yehle
 */
object PhysicsRenderLoop extends Runnable {

  /**
   * Loops game updates until the game goal has been achieved. When the goal has been reached, the game window is
   * disposed, and the program will shut down.
   */
  override def run(): Unit = {
    var lastLoopTime:Long = 0
    var loopTime = System.nanoTime() - (1e9/30).toLong

    while (!GameState.goal.completed) {
      lastLoopTime = loopTime
      loopTime = System.nanoTime()

      updateWorld(loopTime - lastLoopTime)

      val buffer = GameWindow.getBufferStrategy
      render(buffer.getDrawGraphics.asInstanceOf[Graphics2D], GameState.playerCamera)
      buffer.show()
    }

    GameWindow.setVisible(false)
    GameWindow.dispose()
  }

  /**
   * Updates all of the objects in the world.
   * @param elapsed The time in nanoseconds since the last time this method was called
   */
  def updateWorld(elapsed: Long): Unit = {
    GameState.player.updateState(elapsed)
    GameState.blocks.foreach(_.updateState(elapsed))

    GameState.goal.update(elapsed)

    // update camera position
    GameState.playerCamera.position = GameState.player.location
    GameState.playerCamera.forward = GameState.player.forward
    GameState.playerCamera.right = GameState.player.right
  }

  /**
   * Renders a frame onto a graphics object from the point of view of the given camera.
   * @param g The graphics to render onto
   * @param camera The camera to use during the rendering
   */
  def render(g: Graphics2D, camera: Camera) {
    g.setColor(Color.black)
    g.fillRect(0, 0, GameWindow.getWidth, GameWindow.getHeight)

    GameState.hud.render(g)

    camera.beginFrame()
    GameState.blocks.par.foreach(_.render(g, camera))
  }
}

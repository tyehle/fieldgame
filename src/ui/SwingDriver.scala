package ui

import java.awt._
import java.awt.event.{KeyEvent, KeyListener}
import javax.swing._
import controll.KeyboardController
import state._

import scala.util.Random

/**
 *
 * @author Tobin Yehle
 */

object SwingDriver extends JFrame {
  def main(args: Array[String]) = {
    //    GameState.blocks +:= new Block(new Vector3(100,100,100),
    //                                   new Vector3(100,100,100))
    val bounds = 700
    0 to 10000 foreach { _ => GameState.blocks += new Block(
      Position(Random.nextInt(bounds * 2) - bounds,
               Random.nextInt(bounds * 2) - bounds,
               Random.nextInt(bounds * 2) - bounds),
      Position(Random.nextInt(20) + 10,
               Random.nextInt(20) + 10,
               Random.nextInt(20) + 10),
      Color.magenta)
    }

    val device = GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice
    setUndecorated(true)
    setResizable(false)
    device.setFullScreenWindow(this)
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

    setIgnoreRepaint(true)

    createBufferStrategy(2)

    println("Page Flipping: " + getBufferStrategy.getCapabilities.isPageFlipping)
    println("Multi Buffer: " + getBufferStrategy.getCapabilities.isMultiBufferAvailable)
    println("Fullscreen Required: " + getBufferStrategy.getCapabilities.isFullScreenRequired)

    setVisible(true)

    addKeyListener(new KeyboardController)

    // disappear into the rendering loop
    mainLoop()
  }

  def mainLoop() {
    val done = false
    while (!done) {
      // do physics
      doPhysics()

      // do graphics
      render(getBufferStrategy.getDrawGraphics.asInstanceOf[Graphics2D], GameState.playerCamera)
      getBufferStrategy.show()
    }
  }

  def doPhysics(): Unit = {
    GameState.player.updateState()

    // update camera position
    GameState.playerCamera.position = GameState.player.position
    GameState.playerCamera.forward = GameState.player.forward
    GameState.playerCamera.right = GameState.player.right
  }

  def render(g: Graphics2D, camera: LogarithmicCamera) {
    val screen = Toolkit.getDefaultToolkit.getScreenSize
    val center = (screen.width / 2, screen.height / 2)
    val scale = center._1.min(center._2) / math.Pi * 1

    g.setColor(Color.black)
    g.fillRect(0, 0, getWidth, getHeight)

    // Render a HUD
    g.setColor(new Color(0x0066cc))
    drawOval(g, center, (math.Pi / 2 * scale).asInstanceOf[Int])
    drawOval(g, center, (math.Pi * scale).asInstanceOf[Int])
    g.drawString("Speed: " + GameState.player.speed, 5, 17)

    val drew = GameState.blocks.map(_.render(g, center, camera, scale)).count(identity)

    g.setColor(new Color(0x0066cc))
    g.drawString(s"Blocks drawn: $drew", 5, 34)
  }

  def drawOval(g: Graphics2D, center: (Int, Int), radius: Int): Unit = {
    g.drawOval(center._1 - radius, center._2 - radius, 2 * radius, 2 * radius)
  }

  override def paint(g: Graphics) {
    //    g setColor Color.MAGENTA
    //    g.fillRect(0, 0, getWidth(), getHeight())
  }
}

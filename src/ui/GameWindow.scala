package ui

import java.awt._
import javax.swing._
import controll.{PhysicsRenderLoop, KeyboardController}
import state._

import scala.util.Random

/**
 *
 * @author Tobin Yehle
 */

object GameWindow extends JFrame {
  def main(args: Array[String]): Unit = {
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

    // start the rendering loop
    new Thread(PhysicsRenderLoop).start()
  }
}

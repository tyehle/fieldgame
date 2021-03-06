package ui

import java.awt._
import javax.swing._

import controll.{KeyboardController, PhysicsRenderLoop}

/**
 *
 * @author Tobin Yehle
 */

object GameWindow extends JFrame {
  def main(args: Array[String]): Unit = {
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
    println(s"Window size: (${getSize.width}, ${getSize.height})")

    addKeyListener(new KeyboardController)

    new Thread(PhysicsRenderLoop).start()
  }
}

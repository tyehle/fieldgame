package ui

import java.awt._
import javax.swing._
import state._

/**
 *
 * Created by Tobin on 4/4/2014.
 */

object SwingDriver extends JFrame {
  val speed = 10
  var direction = new Vector3(speed,speed,0)

  def main(args: Array[String]) = {
    GameState.blocks += new Block(new Vector3(100,100,100),
      new Vector3(100,100,100))

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

    // disappear into the rendering loop
    mainLoop()
  }

  def mainLoop() {
    var done = false
    while(!done) {
      // do physics
      doPhysics()

      // do graphics
      render(getBufferStrategy.getDrawGraphics)
      getBufferStrategy.show()
    }
  }

  def doPhysics() {
    val b = GameState.blocks(0)

    if(b.position.x <= 0 || b.position.x >= getWidth - b.size.x) {
      direction = new Vector3(-direction.x, direction.y, 0)
    }

    if(b.position.y <= 0 || b.position.y >= getHeight - b.size.y) {
      direction = new Vector3(direction.x, -direction.y, 0)
    }

    b.position = b.position + direction
  }

  def render(g:Graphics) {
    g setColor Color.BLACK
    g.fillRect(0, 0, getWidth, getHeight)

    g setColor Color.RED
    for(b <- GameState.blocks) {
      g.fillRect(b.position.x.toInt, b.position.y.toInt,
        b.size.x.toInt, b.size.y.toInt)
    }
  }

  override def paint(g:Graphics) {
    //    g setColor Color.MAGENTA
    //    g.fillRect(0, 0, getWidth(), getHeight())
  }
}

package ui

import java.awt._
import java.awt.event.{KeyEvent, KeyListener}
import javax.swing._
import state._

import scala.util.Random

/**
 *
 * Created by Tobin on 4/4/2014.
 */

object SwingDriver extends JFrame {
  val speed = 1

  def main(args: Array[String]) = {
//    GameState.blocks +:= new Block(new Vector3(100,100,100),
//                                   new Vector3(100,100,100))

    0 to 100 foreach {_ => GameState.blocks +:= new Block(new Vector3(0,0,0),
                                                          new Vector3(Random.nextInt(200) - 100,
                                                                      Random.nextInt(200) - 100,
                                                                      Random.nextInt(200) - 100))}

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

    addKeyListener(new KeyListener {
      override def keyTyped(e: KeyEvent): Unit = {}

      override def keyPressed(e: KeyEvent): Unit = {
        val angle = math.Pi / 36.0
        e.getKeyCode match {
          case KeyEvent.VK_W => Player.pitch(-angle)
          case KeyEvent.VK_S => Player.pitch(angle)
          case KeyEvent.VK_A => Player.roll(-angle)
          case KeyEvent.VK_D => Player.roll(angle)
          case _ =>
        }
      }

      override def keyReleased(e: KeyEvent): Unit = {}
    })

    // disappear into the rendering loop
    mainLoop()
  }

  def mainLoop() {
    var done = false
    while(!done) {
      // do physics
      doPhysics()

      // do graphics
      render(getBufferStrategy.getDrawGraphics.asInstanceOf[Graphics2D])
      getBufferStrategy.show()
    }
  }

  def doPhysics(): Unit = {
    Player.position += Player.forward * speed
  }

  def render(g:Graphics2D) {
    g.setColor(Color.BLACK)
    g.fillRect(0, 0, getWidth, getHeight)

    val screen = Toolkit.getDefaultToolkit.getScreenSize

//    println("\n\nRendering:")

//    g.setColor(Color.RED)
    for(b <- GameState.blocks) {
      b.render(g, (screen.width/2, screen.height/2), 1080 / math.Pi)
    }
  }

  override def paint(g:Graphics) {
    //    g setColor Color.MAGENTA
    //    g.fillRect(0, 0, getWidth(), getHeight())
  }
}

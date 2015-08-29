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
    val bounds = 700
    0 to 10000 foreach {_ => GameState.blocks +:= new Block(Position(Random.nextInt(20) + 10,
                                                                   Random.nextInt(20) + 10,
                                                                   Random.nextInt(20) + 10),
                                                          Position(Random.nextInt(bounds*2) - bounds,
                                                                   Random.nextInt(bounds*2) - bounds,
                                                                   Random.nextInt(bounds*2) - bounds))}

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
      private val speed = math.Pi / 36.0
      private val accel = .1

      override def keyTyped(e: KeyEvent): Unit = {}

      override def keyPressed(e: KeyEvent): Unit = {
        e.getKeyCode match {
          case KeyEvent.VK_CONTROL => GameState.player.speed -= accel
          case KeyEvent.VK_SHIFT => GameState.player.speed += accel
          case KeyEvent.VK_W => GameState.player.pitchRate = -speed
          case KeyEvent.VK_S => GameState.player.pitchRate = speed
          case KeyEvent.VK_A => GameState.player.rollRate = -speed
          case KeyEvent.VK_D => GameState.player.rollRate = speed
          case _ =>
        }
      }

      override def keyReleased(e: KeyEvent): Unit = {
        e.getKeyCode match {
          case KeyEvent.VK_W => GameState.player.pitchRate = 0
          case KeyEvent.VK_S => GameState.player.pitchRate = 0
          case KeyEvent.VK_A => GameState.player.rollRate = 0
          case KeyEvent.VK_D => GameState.player.rollRate = 0
          case _ =>
        }
      }
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
    GameState.player.updateState()
  }

  def render(g:Graphics2D) {
    val screen = Toolkit.getDefaultToolkit.getScreenSize
    val center = (screen.width/2, screen.height/2)
    val scale = center._1.min(center._2) / math.Pi

    g.setColor(Color.BLACK)
    g.fillRect(0, 0, getWidth, getHeight)

    // Render a HUD
    g.setColor(new Color(0x0066cc))
    drawOval(g, center, (math.Pi / 2 * scale).asInstanceOf[Int])
    drawOval(g, center, (math.Pi * scale).asInstanceOf[Int])
    g.drawString("Speed: "+GameState.player.speed, 5, 17)

    val camera = new LogarithmicCamera(GameState.player.position,
                                       GameState.player.forward, GameState.player.right)

//    println("\n\nRendering:")

//    g.setColor(Color.RED)
    val lod = 200
    var drew = 0
    for(block <- GameState.blocks) {
      val distance = (block.position - GameState.player.position).mag
      if(distance < lod) {
        drew += 1
        val brightness = (Math.exp(- distance / lod * 3.5) * 255).toInt
        g.setColor(new Color(brightness, 0, brightness))
        block.render(g, center, camera, scale)
      }
    }
    g.setColor(new Color(0x0066cc))
    g.drawString(s"Drew $drew blocks", 5, 34)
//    GameState.blocks.foreach(_.render(g, center, camera, scale))
  }

  def drawOval(g: Graphics2D, center: (Int, Int), radius: Int): Unit = {
    g.drawOval(center._1 - radius, center._2 - radius, 2*radius, 2*radius)
  }

  override def paint(g:Graphics) {
    //    g setColor Color.MAGENTA
    //    g.fillRect(0, 0, getWidth(), getHeight())
  }
}

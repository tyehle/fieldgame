package state

import java.awt.{Toolkit, Color}

import ui.{CircleHud, Fog, LogarithmicCamera}

import collection.mutable.ListBuffer
import scala.collection.mutable
import scala.util.Random

/**
 * This is just a container for all of the things in the game. This is the
 * object that different parts of the game will communicate through, so it must
 * be threadsafe.
 * @author Tobin Yehle
 */
object GameState {
  val bounds = new Position(1000, 1000, 1000)
  private val targetDensity = 3e-6
  val blocks:mutable.Buffer[Block] = ListBuffer.fill((targetDensity*GameState.bounds.volume).toInt)(generateBlock)
  val player = new Player(bounds / 2, // position
                          Position(0, 0, 1), Position(1, 0, 0), // orientation
                          5e-8, // speed
                          0, 0, 0) // rotation speed
  private val screen = Toolkit.getDefaultToolkit.getScreenSize
  val playerCamera = new LogarithmicCamera(player.position, player.forward, player.right,
                                           medium = new Fog(200, Color.black),
                                           center = (screen.width / 2, screen.height / 2),
                                           scale = screen.width.min(screen.height) / math.Pi * .5)
  val hud = new CircleHud()

  def generateBlock = {
    val size = Position(Random.nextInt(20) + 10,
                        Random.nextInt(20) + 10,
                        Random.nextInt(20) + 10)
    val position = Position(Random.nextInt((GameState.bounds.x - size.x).toInt),
                            Random.nextInt((GameState.bounds.y - size.y).toInt),
                            Random.nextInt((GameState.bounds.z - size.z).toInt))
    new Block(position, size, if(Random.nextBoolean()) new Color(0x006600) else new Color(0xff00ff))
  }
}


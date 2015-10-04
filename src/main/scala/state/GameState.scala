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
  val bounds = new Position(200, 200, 200)

  private val targetDensity = 5e-6
  val blocks:mutable.Buffer[Block] = ListBuffer.fill((targetDensity*GameState.bounds.volume).toInt)(generateBlock)
  blocks.foreach(_.edges) // force computation of the edges
  blocks.foreach(_.images) // force computation of the images

  val player = new Player(bounds / 2, // position
                          Position(-1, 1, 1).normalized, Position(1, 0, 1).normalized, // orientation
                          5e-8, // speed
                          0, 0, 0) // rotation speed

  val viewDistance = 200 // should be less than bounds
  private val screen = Toolkit.getDefaultToolkit.getScreenSize
  val playerCamera = new LogarithmicCamera(player.location, player.forward, player.right,
                                           medium = new Fog(viewDistance, Color.black),
                                           center = (screen.width / 2, screen.height / 2),
                                           initialFov = math.Pi)

  val hud = new CircleHud()

  val goal:Goal = new ClearGoal(blocks.length)

  def generateBlock = {
    val size = Position(Random.nextInt(20) + 10,
                        Random.nextInt(20) + 10,
                        Random.nextInt(20) + 10)
    val position = Position(Random.nextInt((GameState.bounds.x - size.x).toInt),
                            Random.nextInt((GameState.bounds.y - size.y).toInt),
                            Random.nextInt((GameState.bounds.z - size.z).toInt))
    val velocity = Position(Random.nextDouble()*4e-8 - 2e-8,
                            Random.nextDouble()*4e-8 - 2e-8,
                            Random.nextDouble()*4e-8 - 2e-8)
    new Block(position, size, velocity, Color.magenta)
  }
}


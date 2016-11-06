package state

import java.awt.{Color, Toolkit}

import ui.{CircleHud, Fog, GameWindow, LogarithmicCamera}

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
  /** The size of the world */
  val bounds = new Position(200, 200, 200)

  private val targetDensity = 5e-6
  /** The list of blocks in the world */
  val blocks:mutable.Buffer[Block] = ListBuffer.fill((targetDensity*GameState.bounds.volume).toInt)(generateBlock())
  blocks.foreach(_.edges) // force computation of the edges
  blocks.foreach(_.images) // force computation of the images

  /** The player */
  val player = new Player(bounds / 2, // position
                          Position(-1, 1, 1).normalized, Position(1, 0, 1).normalized, // orientation
                          5e-8, // speed
                          0, 0, 0) // rotation speed

  /**
   * The farthest away a block is visible. If this is greater than the bounds of the world, then blocks that should be
   * rendered will not be.
   */
  val viewDistance = 200
  private val screen = GameWindow.getSize
  /** A camera that follows the player */
  val playerCamera = new LogarithmicCamera(player.location, player.forward, player.right,
                                           medium = new Fog(viewDistance, Color.black),
                                           center = (screen.width / 2, screen.height / 2),
                                           initialFov = math.Pi)

  /** The hud for the player */
  val hud = new CircleHud(playerCamera)

  /** The goal of the game. When this goal is achieved the game is over. */
  val goal:Goal = new ClearGoal()

  /**
   * Generates a random block within the given set of parameters.
   * @param sizeLimits (smallest, biggest) size of all dimensions of the generated box
   * @return The new random box
   */
  def generateBlock(sizeLimits:(Int, Int) = (10, 30)) = {
    // generate a size within the given limits
    val size = Position(Random.nextInt(sizeLimits._2 - sizeLimits._1) + sizeLimits._1,
                        Random.nextInt(sizeLimits._2 - sizeLimits._1) + sizeLimits._1,
                        Random.nextInt(sizeLimits._2 - sizeLimits._1) + sizeLimits._1)
    // generate a random position in bounds
    val position = Position(Random.nextInt((GameState.bounds.x - size.x).toInt),
                            Random.nextInt((GameState.bounds.y - size.y).toInt),
                            Random.nextInt((GameState.bounds.z - size.z).toInt))
    val velocity = Position(Random.nextDouble()*4e-8 - 2e-8,
                            Random.nextDouble()*4e-8 - 2e-8,
                            Random.nextDouble()*4e-8 - 2e-8)
    new Block(position, size, velocity, Color.magenta)
  }
}


package state

import java.awt.Color

import ui.{CircleHud, Fog, GameWindow, LogarithmicCamera}

/**
 * This is just a container for all of the things in the game. This is the
 * object that different parts of the game will communicate through, so it must
 * be threadsafe.
 * @author Tobin Yehle
 */
object GameState {
  /** The size of the world */
  val bounds = new Position(200, 200, 200)

  /** The goal of the game. When this goal is achieved the game is over. */
  val goal:Goal = new PoisonGoal()

  /** The list of blocks in the world */
  val blocks = goal.initializeBlocks

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

  def removeBlocks(toRemove: Seq[Int]): Unit = {
    // Relies on foreach going in order
    toRemove.indices.map(i => toRemove(i) + i).foreach { i =>
      goal.blockDestroyed(GameState.blocks.remove(i))
    }
  }
}


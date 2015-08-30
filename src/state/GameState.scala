package state

import java.awt.Color

import ui.{Fog, LogarithmicCamera}

import collection.mutable.ListBuffer
import scala.collection.mutable

/**
 * This is just a container for all of the things in the game. This is the
 * object that different parts of the game will communicate through, so it must
 * be threadsafe.
 * @author Tobin Yehle
 */
object GameState {
  val blocks:mutable.Buffer[Block] = ListBuffer.empty[Block]
  val player = new Player(Position(0,0,0), // position
                          Position(0, 0, 1), Position(1, 0, 0), // orientation
                          1.0, // speed
                          0, 0, 0) // rotation speed
  val playerCamera = new LogarithmicCamera(player.position, player.forward, player.right,
                                           new Fog(200, Color.black))
}


package state

import java.awt.{Toolkit, Color}

import ui.{CircleHud, Fog, LogarithmicCamera}

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
                          5e-8, // speed
                          0, 0, 0) // rotation speed
  private val screen = Toolkit.getDefaultToolkit.getScreenSize
  val playerCamera = new LogarithmicCamera(player.position, player.forward, player.right,
                                           medium = new Fog(200, Color.black),
                                           center = (screen.width / 2, screen.height / 2),
                                           scale = screen.width.min(screen.height) / math.Pi * .5)
  val hud = new CircleHud()
}


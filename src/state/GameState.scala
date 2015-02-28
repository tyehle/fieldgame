package state

import collection.mutable.ListBuffer
import scala.collection.mutable

/**
 * This is just a container for all of the things in the game. This is the
 * object that different parts of the game will communicate through, so it must
 * be threadsafe.
 * Created by Tobin on 4/4/2014.
 */
object GameState {
  var blocks:mutable.Seq[Block] = ListBuffer.empty[Block]
  var player = new Player(Position(0,0,0), Position(0, 0, 1), Position(1, 0, 0), 1.0)
}


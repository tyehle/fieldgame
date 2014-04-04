package state

import collection.mutable.ListBuffer

/**
 * This is just a container for all of the things in the game. This is the
 * object that different parts of the game will communicate through, so it must
 * be threadsafe.
 * Created by Tobin on 4/4/2014.
 */
object GameState {
  var blocks:ListBuffer[Block] = ListBuffer()
  var player = Player
  var camera = Camera
}


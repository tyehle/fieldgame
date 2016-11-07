package state

import scala.collection.mutable

/**
 * @author Tobin Yehle
 */
trait Goal {
  def completed: Boolean
  def initializeBlocks: mutable.Buffer[Block]
  def update(elapsed: Long): Unit
  def blockDestroyed(block: Block): Unit
  def message: String
}

package state

/**
 * @author Tobin Yehle
 */
trait Goal {
  def completed: Boolean
  def update(elapsed: Long): Unit
  def message: String
}

package state

/**
 * @author Tobin Yehle
 */
class ClearGoal(initialNumber: Int) extends Goal {

  private val maxSpeed = 1.5e-7
  private val minSpeed = 5e-8

  private val minFov = math.Pi / 4
  private val maxFov = math.Pi * 2

  override def completed: Boolean = GameState.blocks.isEmpty

  override def update(elapsed: Long): Unit = {
    val howDone = 1 - (GameState.blocks.length / initialNumber.toDouble)
    GameState.player.speed = minSpeed + (maxSpeed - minSpeed)*howDone
    GameState.playerCamera.setFov(minFov + (maxFov - minFov)*howDone)
  }

  override def message = s"Blocks remaining: ${GameState.blocks.length}"
}

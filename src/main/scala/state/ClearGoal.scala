package state
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * @author Tobin Yehle
 */
class ClearGoal extends Goal {
  private val targetDensity = 5e-6
  private val initialNumber = (targetDensity*GameState.bounds.volume).toInt
  private var blocksRemaining = initialNumber

  private val maxSpeed = 1.5e-7
  private val minSpeed = 5e-8

  private val minFov = math.Pi / 4
  private val maxFov = math.Pi * 2

  override def completed: Boolean = blocksRemaining == 0

  override def initializeBlocks: mutable.Buffer[Block] = {
    val sizeLimits = (10, 30)
    val blocks = ListBuffer.fill((targetDensity*GameState.bounds.volume).toInt)(Block(sizeLimits))
    blocks.foreach(_.edges) // force computation of the edges
    blocks.foreach(_.images) // force computation of the images
    blocks
  }

  override def update(elapsed: Long): Unit = {
    val howDone = 1 - (blocksRemaining / initialNumber.toDouble)
    GameState.player.speed = minSpeed + (maxSpeed - minSpeed)*howDone
    GameState.playerCamera.setFov(minFov + (maxFov - minFov)*howDone)
  }

  override def blockDestroyed(block: Block) = blocksRemaining -= 1

  override def message = s"Blocks remaining: ${GameState.blocks.length}"
}

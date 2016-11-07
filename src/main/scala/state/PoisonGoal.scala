package state
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * @author Tobin Yehle
 */
class PoisonGoal extends Goal {
  private val targetDensity = 5e-6
  private val poisonRatio = 1/10.0
  private val numBlocks = targetDensity*GameState.bounds.volume

  private val initialNumber = (numBlocks * (1-poisonRatio)).toInt
  private var blocksRemaining = initialNumber

  private val maxSpeed = 1.5e-7
  private val minSpeed = 5e-8

  private val minFov = math.Pi / 4
  private val maxFov = math.Pi * 2

  private val sizeLimits = (10, 30)

  override def completed: Boolean = blocksRemaining == 0

  override def initializeBlocks: mutable.Buffer[Block] = {
    val blocks = ListBuffer.fill((numBlocks * (1-poisonRatio)).toInt)(Block(sizeLimits))
    blocks ++= ListBuffer.fill((numBlocks * poisonRatio).toInt)(PoisonBlock(sizeLimits))
    blocks.foreach(_.edges) // force computation of the edges
    blocks.foreach(_.images) // force computation of the images
    blocks
  }

  override def update(elapsed: Long): Unit = {
    val howDone = 1 - (blocksRemaining / initialNumber.toDouble)
    GameState.player.speed = minSpeed + (maxSpeed - minSpeed)*howDone
    GameState.playerCamera.setFov(minFov + (maxFov - minFov)*howDone)
  }

  override def blockDestroyed(block: Block) = block match {
    case p: PoisonBlock =>
      GameState.blocks.append(PoisonBlock(sizeLimits), PoisonBlock(sizeLimits), Block(sizeLimits), Block(sizeLimits))
      blocksRemaining += 2
    case b: Block => blocksRemaining -= 1
  }

  override def message = s"Blocks remaining: $blocksRemaining"
}

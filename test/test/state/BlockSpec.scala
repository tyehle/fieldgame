package test.state

import java.awt.Color

import org.scalatest.{Matchers, FlatSpec}
import state.{Position, Block}
import ui.Line

/**
 * @author Tobin Yehle
 */
class BlockSpec extends FlatSpec with Matchers {
  val unitBlock = new Block(Position.zero, Position(1,1,1), Color.magenta)

  "A block" should "be lethal" in {
    unitBlock.isLethal should be (true)
  }

  it should "know when a point is inside it" in {
    unitBlock.contains(Position.zero) should be (true)
    unitBlock.contains(Position(1,1,1)) should be (true)
    unitBlock.contains(Position(.5, .5, 2)) should be (false)

    val offsetBlock = new Block(Position(-3, 5, 7), Position(1,1,1), Color.magenta)
    offsetBlock.contains(Position.zero) should be (false)
    offsetBlock.contains(Position(-2, 6, 8)) should be (true)
    offsetBlock.contains(Position(-2.5, 6.5, 7.5)) should be (false)
    offsetBlock.contains(Position(-1.5, 6, 8)) should be (false)
  }

  it should "produce its edges" in {
    val edges = unitBlock.edges

    val c = unitBlock.color
    val expectedEdges = Seq(
      Line(Position.zero, Position(1,0,0), c), Line(Position.zero, Position(0,1,0), c), Line(Position.zero, Position(0,0,1), c),
      Line(Position(1,1,0),Position(0,1,0),c), Line(Position(1,1,0),Position(1,0,0),c), Line(Position(1,1,0),Position(1,1,1),c),
      Line(Position(1,0,1),Position(0,0,1),c), Line(Position(1,0,1),Position(1,1,1),c), Line(Position(1,0,1),Position(1,0,0),c),
      Line(Position(0,1,1),Position(1,1,1),c), Line(Position(0,1,1),Position(0,0,1),c), Line(Position(0,1,1),Position(0,1,0),c)
    )

    edges should contain theSameElementsAs expectedEdges
  }
}

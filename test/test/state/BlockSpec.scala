package test.state

import java.awt.Color

import org.scalatest.{Matchers, FlatSpec}
import state.{Line, GameState, Position, Block}

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
      Line(Position.zero, Position(1,0,0)), Line(Position.zero, Position(0,1,0)), Line(Position.zero, Position(0,0,1)),
      Line(Position(1,1,0),Position(0,1,0)), Line(Position(1,1,0),Position(1,0,0)), Line(Position(1,1,0),Position(1,1,1)),
      Line(Position(1,0,1),Position(0,0,1)), Line(Position(1,0,1),Position(1,1,1)), Line(Position(1,0,1),Position(1,0,0)),
      Line(Position(0,1,1),Position(1,1,1)), Line(Position(0,1,1),Position(0,0,1)), Line(Position(0,1,1),Position(0,1,0))
    )

    edges should contain theSameElementsAs expectedEdges
  }

  it should "produce a set of images" in {
    val bounds = GameState.bounds
    val location = Position(100, 40, 20)
    val block = new Block(location, Position(10, 10, 10), Color.magenta)

    val offsets = Seq(Position(bounds.x,0,0),
                      Position(0,bounds.y,0),
                      Position(0,0,bounds.z),

                      Position(-bounds.x,0,0),
                      Position(0,-bounds.y,0),
                      Position(0,0,-bounds.z),

                      Position(0,bounds.y,bounds.z),
                      Position(bounds.x,0,bounds.z),
                      Position(bounds.x,bounds.y,0),

                      Position(0,-bounds.y,bounds.z),
                      Position(-bounds.x,0,bounds.z),
                      Position(-bounds.x,bounds.y,0),

                      Position(0,bounds.y,-bounds.z),
                      Position(bounds.x,0,-bounds.z),
                      Position(bounds.x,-bounds.y,0),

                      Position(0,-bounds.y,-bounds.z),
                      Position(-bounds.x,0,-bounds.z),
                      Position(-bounds.x,-bounds.y,0),

                      Position(bounds.x,bounds.y,bounds.z),

                      Position(-bounds.x,bounds.y,bounds.z),
                      Position(bounds.x,-bounds.y,bounds.z),
                      Position(bounds.x,bounds.y,-bounds.z),

                      Position(bounds.x,-bounds.y,-bounds.z),
                      Position(-bounds.x,bounds.y,-bounds.z),
                      Position(-bounds.x,-bounds.y,bounds.z),

                      Position(-bounds.x,-bounds.y,-bounds.z))
    val imageEdges = offsets.map(off => block.edges.map(_.offset(off)))

    block.images should have size 26
    block.images.map(_.location) should contain theSameElementsAs offsets.map(_ + location)
    block.images.map(_.edges) should contain theSameElementsAs imageEdges
  }
}

package test.state

import org.scalatest.{FlatSpec, Matchers}
import state.Position

/**
 * 2/27/2015
 * @author Tobin Yehle
 */
class VectorSpec extends FlatSpec with Matchers {
//  def within

  "A Position" should "be able to be viewed as a Seq[Double]" in {
    "asdf" should be("asdf")
  }

  it should "rotate around an axis" in {
    val angle = math.Pi * 2.0 / 3.0

    val dir = Position(0, 0, 1)
    val axis = Position(1, 1, 1).normalized()

    val target = Position(1, 0 ,0)
    (dir.rotate(angle, axis) - target).length should be < 0.0001
  }
}

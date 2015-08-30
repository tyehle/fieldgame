package test.state

import org.scalatest.{FlatSpec, Matchers}
import state.Position

/**
 * 2/27/2015
 * @author Tobin Yehle
 */
class PositionSpec extends FlatSpec with Matchers {
  private val eps = 1e-10

  "A Position" should "print in cartesian coordinates" in {
    Position(1,2,5).toString should be("Position(1.0, 2.0, 5.0)")
  }

  it should "add and subtract like a vector" in {
    Position(1,2,5) + Position.zero should be(Position(1,2,5))
    Position(1,2,5) + Position(-1, -5, -2) should be(Position(0, -3, 3))

    Position(1,2,5) - Position.zero should be(Position(1,2,5))
    Position(1,2,5) - Position(-1, -5, -2) should be(Position(2, 7, 7))
  }

  it should "have an additive inverse" in {
    -Position.zero should be(Position.zero)
    -Position(-3, 2, 0) should be(Position(3, -2, 0))
  }

  it should "have an inner product with other Positions" in {
    Position.zero dot Position(2,3,4) should be(0.0)
    Position(1, 0, 1) o Position(0, 4, 0) should be(0.0)
    Position(1, 2, 5) o Position(5, 3, 1) should be(16.0)
  }

  it should "be able to be viewed as a Seq[Double]" in {
    Position(1, 2, 5).toSeq should be(Seq(1, 2, 5))
  }

  it should "have magnitude equal to its L2 norm" in {
    Position.zero.mag should be (0.0)
    Position.zero.length should be (0.0)

    Position(1,2,5).length should be(math.sqrt(30))
    Position(1,2,5).mag should be(math.sqrt(30))
  }

  it should "allow multiplication by a scalar" in {
    Position(1,2,5) * 2 should be(Position(2, 4, 10))
    Position(1,2,5).scale(.5) should be (Position(.5, 1, 2.5))
  }

  it should "allow filtering components" in {
    Position(1,2,5).component(x=true, y=false, z=true) should be (Position(1, 0, 5))
    Position(1,2,5).component(x=false, y=true, z=false) should be (Position(0, 2, 0))
  }

  it should "allow cross products" in {
    Position.zero.cross(Position(1,2,5)) should be (Position.zero)
    Position(1,0,0).cross(Position(0,1,0)) should be (Position(0,0,1))
    Position(1,2,5) x Position(1,2,5) should be (Position.zero)
    Position(1,2,5) x Position(5,2,7) should be (Position(14-10, 25-7, 2-10))
  }

  it should "rotate around an axis" in {
    val angle = math.Pi * 2.0 / 3.0

    val dir = Position(0, 0, 1)
    val axis = Position(1, 1, 1)

    val target = Position(1, 0 ,0)
    (dir.rotate(angle, axis) - target).length should be < 0.0001
  }

  it should "be able to build a basis for R3" in {
    Position.zero.basis should be (None)

    var i = Position(1,2,5)
    var basis = i.basis
    basis should matchPattern { case Some((j:Position, k:Position)) => }
    basis match {
      case Some((j, k)) =>
        j.length should be (1.0 +- eps)
        k.length should be (1.0 +- eps)
        i o j should be (0.0 +- eps)
        i o k should be (0.0 +- eps)
        j o k should be (0.0 +- eps)
      case None =>
    }

    i = Position(0,2,5)
    basis = i.basis
    basis should matchPattern { case Some((j:Position, k:Position)) => }
    basis match {
      case Some((j, k)) =>
        j.length should be (1.0 +- eps)
        k.length should be (1.0 +- eps)
        i o j should be (0.0 +- eps)
        i o k should be (0.0 +- eps)
        j o k should be (0.0 +- eps)
      case None =>
    }
  }

  it should "point the same direction after being normalized" in {
    val i = Position(1,2,5)
    all ((i.normalized * i.length - i).toSeq) should be (0.0 +- eps)
  }

  it should "know what it can equal" in {
    Position.zero.canEqual("asdf") should be (false)
    Position.zero.canEqual(Position(1,2,5)) should be (true)
  }

  it should "equal similar Positions" in {
    Position(1,2,5) should be (Position(1,2,5))
    Position(1,2,5) should not be Position(1,2,4)

    // make sure equals does not use hashCode
    Position(1,2,5).## should be (Position(1,1,46).##)
    Position(1,2,5) should not be Position(1,1,46)

    Position.zero should not be "asdf"
  }

  it should "not hash to a similar position" in {
    val hash = Position(1,2,5).##
    hash should not be Position(1,2,4).##
    hash should not be Position(1,2,6).##
    hash should not be Position(1,1,5).##
    hash should not be Position(1,3,5).##
    hash should not be Position(0,2,5).##
    hash should not be Position(2,2,5).##
  }
}

package test.state

import org.scalatest.{Matchers, FlatSpec}
import state.{Position, Quaternion}
import TestUtil._

/**
 * @author Tobin Yehle
 */
class QuaternionSpec extends FlatSpec with Matchers {
  private val q = new Quaternion(1.2, 1, 2, 5)

  "A quaternion" should "print as a quadruple" in {
    q.toString should be ("Quaternion(1.2, 1.0, 2.0, 5.0)")
  }

  "A quaternion" should "be built from a real part and an imaginary position" in {
    val qPos = new Quaternion(1, Position.zero)
    qPos.r should be (1)
    qPos.i should be (0)
    qPos.j should be (0)
    qPos.k should be (0)
  }

  it should "realize the imaginary component as a Position" in {
    q.imaginaryComponent should be (Position(1,2,5))
  }

  it should "be able to be multiplied by another quaternion" in {
    val product = q * new Quaternion(2, 3, 5, 7)
    product.r should be (1.2*2 - 1*3 - 2*5 - 5*7)
    product.i should be (1.2*3 + 1*2 + 2*7 - 5*5)
    product.j should be (1.2*5 - 1*7 + 2*2 + 5*3)
    product.k should be (1.2*7 + 1*5 - 2*3 + 5*2)
  }

  it should "have norm equal to the l2 norm of its components" in {
    q.norm should be (math.sqrt(1.2*1.2 + 1 + 4 + 25))
  }

  it should "allow scaling by a real number" in {
    q.scale(2) should be (new Quaternion(2.4, 2, 4, 10))
    q * 2 should be (new Quaternion(2.4, 2, 4, 10))
    q / 2 should be (new Quaternion(0.6, 0.5, 1, 2.5))
  }

  it should "normalize to a quaternion with magnitude 1 pointing in the same direction" in {
    q.normalize * q.norm should be (q)
  }

  it should "have a conjugate" in {
    q.conjugate should be(new Quaternion(1.2, -1, -2, -5))
  }

  it should "have an inverse equal to the norm squared" in {
    q.inverse should be (q.conjugate / (q.norm*q.norm))
    val i = q.inverse * q
    i.r should beAlmost(1)
    i.i should beAlmost(0)
    i.j should beAlmost(0)
    i.k should beAlmost(0)
  }

  it should "be convertible to a sequence" in {
    q.toSeq should be (Seq(1.2, 1, 2, 5))
  }

  it should "know what it can equal" in {
    q.canEqual("some text") should be (false)
    q.canEqual(new Quaternion(2, 3, 5, 7)) should be (true)
  }

  it should "equal a similar quaternion" in {
    q should be (new Quaternion(1.2, 1, 2, 5))
    q should not equal new Quaternion(1.1, 1, 2, 5)
    q should not equal new Quaternion(1.3, 1, 2, 5)
    q should not equal new Quaternion(1.2, 0.9, 2, 5)
    q should not equal new Quaternion(1.2, 1.1, 2, 5)
    q should not equal new Quaternion(1.2, 1, 1.9, 5)
    q should not equal new Quaternion(1.2, 1, 2.1, 5)
    q should not equal new Quaternion(1.2, 1, 2, 4.9)
    q should not equal new Quaternion(1.2, 1, 2, 5.1)

    q should not equal "some text"

    // make sure equals is not using hashcode
    val maliciousness = new Quaternion(1.2, 0, 43, 5)
    q.## should equal (maliciousness.##)
    q should not equal maliciousness
  }

  it should "hash to a similar quaternion" in {
    q.## should be (new Quaternion(1.2, 1, 2, 5).##)

    q.## should not be new Quaternion(1.1, 1, 2, 5).##
    q.## should not be new Quaternion(1.3, 1, 2, 5).##
    q.## should not be new Quaternion(1.2, 0.9, 2, 5).##
    q.## should not be new Quaternion(1.2, 1.1, 2, 5).##
    q.## should not be new Quaternion(1.2, 1, 1.9, 5).##
    q.## should not be new Quaternion(1.2, 1, 2.1, 5).##
    q.## should not be new Quaternion(1.2, 1, 2, 4.9).##
    q.## should not be new Quaternion(1.2, 1, 2, 5.1).##
  }
}

package test.state

import org.scalatest.Matchers._

/**
 * @author Tobin Yehle
 */
object TestUtil {
  val eps = 1e-10

  def beWithin(eps:Double)(value:Double) = be > value-eps and be < value+eps

  def beAlmost(value: Double) = be >(value-eps) and be < value+eps
}

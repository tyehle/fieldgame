package state

/**
 * Represents a three dimensional vector. This is an immutable object, so all
 * methods do not change the value of the object.
 * Created by Tobin on 4/4/2014.
 */

class Position(val x:Double, val y:Double, val z:Double) {

  override def toString:String = "( "+x+", "+y+", "+z+" )"

  def +(other: Position) = Position(x+other.x, y+other.y, z+other.z)
  def -(other: Position) = Position(x-other.x, y-other.y, z-other.z)
  def unary_-() = Position(-x, -y, -z)

  /**
   * The dot product of this vector with the given vector
   */
  def dot(other: Position) = x*other.x + y*other.y + z*other.z

  /**
   * The dot product of this vector with the given vector
   */
  def o(other: Position) = dot(other)

  /**
   * The magnitude of this vector
   */
  def mag = Math.sqrt(x*x + y*y + z*z)

  /**
   * The magnitude of this vector
   */
  def length = mag

  /**
   * Scales this vector by the given scalar
   */
  def scale(a: Double) = Position(a*x, a*y, a*z)

  /**
   * Scales this vector by the given scalar
   */
  def *(a: Double) = scale(a)

  def component(x:Boolean, y:Boolean, z:Boolean) = {
    Position(if(x) this.x else 0, if(y) this.y else 0, if(z) this.z else 0)
  }

  /**
   * The cross product of this vector and the given vector
   */
  def cross(other: Position): Position = Position(
    y*other.z - z*other.y,
    z*other.x - x*other.z,
    x*other.y - y*other.x)

  /**
   * The cross product of this vector and the given vector
   */
  def x(other: Position): Position = cross(other)

  /**
   * Rotates this vector by a number of radians about an axis in three dimensions.
   * @param theta The angle to rotate this vector by
   * @param axis The axis to rotate around
   * @return A rotated version of this vector
   */
  def rotate(theta: Double, axis: Position): Position = {
    val q = new Quaternion(math.cos(theta / 2), axis.normalized() * math.sin(theta / 2))
    val p = new Quaternion(0, this)
    (q*p*q.inverse).imaginaryComponent
  }

  /**
   * Returns two vectors that, together with this vector, form an orthogonal
   * basis for R3. If this vector is the 0 vector returns None. If this vector
   * is of unit length then the basis is orthonormal.
   */
  def basis:Option[(Position, Position)] = {
    var first:Position = null
    if(x == 0) {
      if(z == 0 && y == 0)
        return None
      else
        first = Position(1,0,0)
    } else {
      // set zn = 0 and yn = 1
      // x*xn + y*yn = 0
      first = Position(-y/x,1,0).normalized()
    }

    Some(first, (this x first).normalized())
  }

  /**
   * Returns a normalized copy of this vector.
   */
  def normalized() = {
    val len = mag
    Position(x/len, y/len, z/len)
  }
}

object Position {
  def apply(x: Double, y: Double, z: Double): Position = {
    new Position(x, y, z)
  }
}
package state

/**
 * Represents a three dimensional vector. This is an immutable object, so all
 * methods do not change the value of the object.
 * Created by Tobin on 4/4/2014.
 */

class Vector3(val x:Double, val y:Double, val z:Double) {

  override def toString:String = "( "+x+", "+y+", "+z+" )"

  def +(other: Vector3) = new Vector3(x+other.x, y+other.y, z+other.z)
  def -(other: Vector3) = new Vector3(x-other.x, y-other.y, z-other.z)
  def unary_-() = new Vector3(-x, -y, -z)

  /**
   * The dot product of this vector with the given vector
   */
  def dot(other: Vector3) = x*other.x + y*other.y + z*other.z
  /**
   * The dot product of this vector with the given vector
   */
  def o(other: Vector3) = dot(other)
  /**
   * The magnitude of this vector
   */
  def mag() = Math.sqrt(x*x + y*y + z*z)
  /**
   * The magnitude of this vector
   */
  def length() = mag
  /**
   * Scales this vector by the given scalar
   */
  def scale(a: Double) = new Vector3(a*x, a*y, a*z)
  /**
   * Scales this vector by the given scalar
   */
  def *(a: Double) = scale(a)
  /**
   * The cross product of this vector and the given vector
   */
  def cross(other: Vector3) = new Vector3(
    y*other.z - z*other.y,
    z*other.x - x*other.z,
    x*other.y - y*other.x)
  /**
   * The cross product of this vector and the given vector
   */
  def *(other: Vector3) = cross(other)
  /**
   * theta: the angle to rotate in radians
   * axis: the axis to rotate around (0 for x, 1 for y, and 2 for z)
   */
  def rotate(theta: Double, axis:Int) = {
    val cos = math.cos(theta)
    val sin = math.sin(theta)
    axis match {
      case 0 => new Vector3(x, cos*y-sin*z, sin*y+cos*z)
      case 1 => new Vector3(cos*x+sin*z, y, -sin*x+cos*z)
      case 2 => new Vector3(cos*x-sin*y, sin*x+cos*y, z)
      case _ => throw new Error("Illegal axis: " + axis)
    }
  }
  /**
   * Returns two vectors that, together with this vector, form an orthogonal
   * basis for R3. If this vector is the 0 vector returns None. If this vector
   * is of unit length then the basis is orthonormal.
   */
  def basis():Option[(Vector3, Vector3)] = {
    var first:Vector3 = null
    if(x == 0) {
      if(z == 0 && y == 0)
        return None
      else
        first = new Vector3(1,0,0)
    } else {
      // set zn = 0 and yn = 1
      // x*xn + y*yn = 0
      first = new Vector3(-y/x,1,0).normalized()
    }

    Some(first, (this * first).normalized())
  }
  /**
   * Returns a normalized copy of this vector.
   */
  def normalized() = {
    val len = mag()
    new Vector3(x/len, y/len, z/len)
  }
}

package state

/**
 * 2/21/2015
 * @author Tobin Yehle
 */
class Quaternion(val r: Double, val i: Double, val j: Double, val k: Double) {
  /**
   * Build a quaternion from a real part, and an imaginary three vector
   * @param real The real component
   * @param imaginary The imaginary component
   */
  def this(real: Double, imaginary: Position) = this(real, imaginary.x, imaginary.y, imaginary.z)

  /**
   * @return The imaginary component as a three vector
   */
  def imaginaryComponent: Position = Position(i, j, k)

  /**
   * Multiplication is done in the same way as imaginary numbers, and then
   * reduced to a quaternion using Hamilton's rules.
   * @param other The quaternion to multiply by
   * @return The product of this quaternion with the given quaternion
   */
  def *(other: Quaternion): Quaternion = {
    new Quaternion(r*other.r - i*other.i - j*other.j - k*other.k,
                   r*other.i + i*other.r + j*other.k - k*other.j,
                   r*other.j - i*other.k + j*other.r + k*other.i,
                   r*other.k + i*other.j - j*other.i + k*other.r)
  }

  /**
   * The norm is the same as the l2norm of the four vector containing the
   * coefficients.
   * @return The norm of this quaternion.
   */
  def norm: Double = math.sqrt(r*r + i*i + j*j + k*k)

  /**
   * Scales this quaternion by a real factor.
   * @param factor The factor to scale each coefficient
   * @return A scaled version of this quaternion
   */
  def scale(factor: Double): Quaternion = new Quaternion(r*factor, i*factor, j*factor, k*factor)

  /**
   * Multiplies this quaternion by a scalar.
   * @param factor The factor to scale each coefficient
   * @return A scaled version of this quaternion
   */
  def *(factor: Double): Quaternion = scale(factor)

  /**
   * Divides this quaternion by a scalar.
   * @param factor The factor to scale each coefficient
   * @return A scaled version of this quaternion
   */
  def /(factor: Double): Quaternion = scale(1 / factor)

  /**
   * Finds the unit quaternion associated with this quaternion by dividing each
   * coefficient by the norm.
   * @return A normalized version of this quaternion
   */
  def normalize: Quaternion = this / norm

  /**
   * The conjugate of a quaternion is found by inverting the imaginary
   * components.
   * @return The conjugate of this quaternion
   */
  def conjugate: Quaternion = new Quaternion(r, -i, -j, -k)

  /**
   * The inverse is the defined as the conjugate over the norm squared.
   * @return The inverse of this quaternion
   */
  def inverse: Quaternion = conjugate / (norm*norm)
}

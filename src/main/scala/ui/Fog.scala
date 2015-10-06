package ui

import java.awt.Color

import state.Position

/**
 *
 * Makes objects that are farther away more dim, as though seeing them through a cloud of dust, or fog.
 * @param lod The maximum distance at which an object can be seen.
 * @param bg The color of the background.
 *           The farther away an object is, the closer its color will be to the background color.
 * @author Tobin Yehle
 */
class Fog(lod: Int, bg: Color) extends Medium {

  private val lodDensityRelation = 3.5

  /**
   * Gets the perceived color of the given point as seen from the point of view of the given camera.
   *
   * If the point is farther away than the lod then it should not be rendered, so this function will return None. As
   * objects get farther away their color appears more similar to the color of the background. The dimming is
   * exponential in the distance to the object because the probability a ray will intersect the object instead of a
   * particle of dust is modeled by an exponential. I chose the density of the fog such that objects appear very dim
   * when they are at the maximum rendered distance. Note: they should never disappear completely, but beyond a certain
   * point you won't see them anyway. I chose a value that looked good on my screen. This may need to be changed.
   * @param camera The position of the observer
   * @param point The object being viewed
   * @param color The true color of the object being viewed
   * @return The perceived color of the object, or None if it should not be rendered
   */
  override def getColor(camera: Position, point: Position, color: Color): Option[Color] = {
    val distance = (point - camera).length
    if(distance < lod) {
      val brightness = math.exp(-distance/lod * lodDensityRelation)
      if(bg == Color.black) Some(multiplyColor(brightness, color))
      else Some(combineColors(color, bg, brightness))
    }
    else {
      None
    }
  }

  /**
   * Combines two colors such that there is r of the first color and 1-r of the second color.
   * @param r The ratio of the first color to the second color
   * @return The new mixed color
   */
  @inline def combineColors(a: Color, b: Color, r: Double): Color = {
    val nr = 1-r
    new Color((a.getRed*r + b.getRed*nr).toInt, (a.getGreen*r + b.getGreen*nr).toInt, (a.getBlue*r + b.getBlue*nr).toInt)
  }

  /**
   * Multiplies each RGB component of a color by some scalar. Warning: If components are scaled above their maximum
   * value an exception will be thrown. This function is really only meant to be used with scalars less than 1.
   * @param scalar The scalar to multiply colors by
   * @param c The color to modify
   * @return The new color.
   */
  @inline def multiplyColor(scalar: Double, c: Color): Color = {
    new Color((c.getRed*scalar).toInt, (c.getGreen*scalar).toInt, (c.getBlue*scalar).toInt)
  }
}

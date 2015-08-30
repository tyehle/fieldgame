package ui

import java.awt.Color

import state.Position

/**
 * @author Tobin Yehle
 */
class Fog(lod: Int, bg: Color) extends Medium {
  override def getColor(camera: Position, point: Position, color: Color): Option[Color] = {
    val distance = (point - camera).length
    if(distance < lod) {
      val brightness = math.exp(-distance/lod * 3.5)
      if(bg == Color.black) Some(multiplyColor(brightness, color))
      else Some(combineColors(color, bg, brightness))
    }
    else {
      None
    }
  }

  @inline def combineColors(a: Color, b: Color, r: Double): Color = {
    val nr = 1-r
    new Color((a.getRed*r + b.getRed*nr).toInt, (a.getGreen*r + b.getGreen*nr).toInt, (a.getBlue*r + b.getBlue*nr).toInt)
  }

  @inline def multiplyColor(scalar: Double, c: Color): Color = {
    new Color((c.getRed*scalar).toInt, (c.getGreen*scalar).toInt, (c.getBlue*scalar).toInt)
  }
}

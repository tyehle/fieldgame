package state

/**
 * @author Tobin Yehle
 */
case class Line(start: Position, end: Position) {
  def offset(d: Position): Line = Line(start + d, end + d)
}

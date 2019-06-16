package de.htwg.se.Schach.model.fieldBaseImpl

private[model] case class Cell(colour: Colour, contains: Option[Figure]) {
  override def toString: String = {
    colour match {
      case Colour.`black` => if (contains.isEmpty) "♦##♦" else "#" + contains.get.toString + "#"
      case Colour.`white` => if (contains.isEmpty) "♦⁕⁕♦" else "⁕" + contains.get.toString + "⁕"
    }
  }

  def isBlack: Boolean = colour == Colour.black

}

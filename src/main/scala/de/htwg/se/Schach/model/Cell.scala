package de.htwg.se.Schach.model

import _root_.de.htwg.se.Schach.model.Colour.Colour
import de.htwg.se.Schach.model.Figures.Figure

private[model] case class Cell(colour: Colour, contains: Option[Figure]) {
  override def toString: String = {
    colour match {
      case Colour.`black` => if (contains.isEmpty) "♦##♦" else "#" + contains.get.toString + "#"
      case Colour.`white` => if (contains.isEmpty) "♦⁕⁕♦" else "⁕" + contains.get.toString + "⁕"
    }
  }

  def isBlack: Boolean = colour == Colour.black

}
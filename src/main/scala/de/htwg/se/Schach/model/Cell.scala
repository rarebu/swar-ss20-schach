package de.htwg.se.Schach.model

import _root_.de.htwg.se.Schach.model.Colour.Colour

case class Cell(colour: Colour, contains: Option[Figure]) {
  override def toString: String = {
    colour match {
      case Colour.Black => if (contains.isEmpty) "♦##♦" else "#" + contains.get.toString() + "#"
      case Colour.White => if (contains.isEmpty) "♦⁕⁕♦" else "⁕" + contains.get.toString() + "⁕"
    }
  }
}

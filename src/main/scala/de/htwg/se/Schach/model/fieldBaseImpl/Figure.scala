package de.htwg.se.Schach.model.fieldBaseImpl

import de.htwg.se.Schach.model.fieldBaseImpl.Colour.Colour
import de.htwg.se.Schach.model.Rook

trait Figure {
  val colour: Colour

  def getName: String

  def getPossibleNewPositions(field: Field, coordinates: Coordinates): Vector[Vector[Coordinates]]

  def move: Figure

  def unMove: Figure
}

object Figure {
  def apply(figure: String, colour: Colour, stepcount: Int) = figure match {
    case "Queen" => Option.apply(Queen(colour, stepcount))
    case "King" => Option.apply(King(colour, stepcount))
    case "Rook" => Option.apply(Rook(colour, stepcount))
    case "Knight" => Option.apply(Knight(colour, stepcount))
    case "Bishop" => Option.apply(Bishop(colour, stepcount))
    case "Pawn" => Option.apply(Pawn(colour, stepcount))
    case _ => Option.empty
  }

  def applyNew(figure: String, colour: Colour) = apply(figure, colour, 0)
  def applyNotNew(figure: String, colour: Colour) = apply(figure, colour, 1)

  val ROW_WHITE = 7
  val ROW_BLACK = 0
  val ROW_BLACK_PAWN = 1
  val ROW_WHITE_PAWN = 6
  val CHANGABLE_WHITE_FIGURES = "♕♖♗♘"
  val CHANGABLE_BLACK_FIGURES = "♛♜♝♞"
}
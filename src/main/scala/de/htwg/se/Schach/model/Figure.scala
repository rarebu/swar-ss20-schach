package de.htwg.se.Schach.model

import _root_.de.htwg.se.Schach.model.Colour.Colour

trait Figure {
  val colour: Colour

  def getName: String

  def getPossibleNewPositions(field: Field, coordinates: Coordinates): Vector[Vector[Coordinates]]

  def move: Figure

  def unMove: Figure
}

private[Schach] object Figure {
  val ROW_WHITE = 7
  val ROW_BLACK = 0
  val ROW_BLACK_PAWN = 1
  val ROW_WHITE_PAWN = 6
  val CHANGABLE_WHITE_FIGURES = "♕♖♗♘"
  val CHANGABLE_BLACK_FIGURES = "♛♜♝♞"
}
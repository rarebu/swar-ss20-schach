package de.htwg.se.Schach.model

import _root_.de.htwg.se.Schach.model.Colour.Colour

trait Figure {
  val colour: Colour
  val coordinates: Coordinates

  def getName: String

  def getPossibleNewPositions: Vector[Vector[Coordinates]]

  def move(coordinates: Coordinates): Figure
}

object Figure {
  val ROW_WHITE = 7
  val ROW_BLACK = 0
  val ROW_BLACK_PAWN = 1
  val ROW_WHITE_PAWN = 6
  object Side extends Enumeration {
    type Side = Value
    val Left, Right = Value
  }
}
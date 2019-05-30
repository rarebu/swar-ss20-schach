package de.htwg.se.schach.model

import _root_.de.htwg.se.schach.model.Colour.Colour
import _root_.de.htwg.se.schach.model.rules.Moves.bishopMove

case class Bishop(colour: Colour, stepCounter: Int) extends Figure {

  override def getName: String = "Bishop"

  override def getPossibleNewPositions(field: Field, coordinates: Coordinates): Vector[Vector[Coordinates]] = bishopMove(field, this.colour, coordinates)

  override def toString: String = if (colour == Colour.black) "♝" else "♗"

  override def move: Figure = {
    Bishop(this.colour, this.stepCounter + 1)
  }

  override def unMove: Figure = {
    Bishop(this.colour, this.stepCounter - 1)
  }
}

object Bishop {

  import Figure.{ROW_BLACK, ROW_WHITE}

  val COL_FIGURE_LEFT = 2
  val COL_FIGURE_RIGHT = 5
  val COORDINATES_BLACK_LEFT: Coordinates = Coordinates(ROW_BLACK, COL_FIGURE_LEFT)
  val COORDINATES_BLACK_RIGHT: Coordinates = Coordinates(ROW_BLACK, COL_FIGURE_RIGHT)
  val COORDINATES_WHITE_LEFT: Coordinates = Coordinates(ROW_WHITE, COL_FIGURE_LEFT)
  val COORDINATES_WHITE_RIGHT: Coordinates = Coordinates(ROW_WHITE, COL_FIGURE_RIGHT)
}
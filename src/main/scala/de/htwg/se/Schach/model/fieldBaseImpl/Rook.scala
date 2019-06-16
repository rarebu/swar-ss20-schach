package de.htwg.se.Schach.model.fieldBaseImpl

import de.htwg.se.Schach.model.fieldBaseImpl.Colour.Colour
import de.htwg.se.Schach.model.fieldBaseImpl.rules.Moves


case class Rook(colour: Colour, stepCounter: Int) extends Figure {

  override def getName: String = "Rook"

  override def getPossibleNewPositions(field: Field, coordinates: Coordinates): Vector[Vector[Coordinates]] = Moves.rookMove(field, this.colour, coordinates)

  override def toString: String = if (colour == Colour.black) "♜" else "♖"

  override def move: Figure = {
    Rook(this.colour, this.stepCounter + 1)
  }

  override def unMove: Figure = {
    Rook(this.colour, this.stepCounter - 1)
  }

  def hasAbility: Boolean = stepCounter == 0
}

private object Rook {

  import Figure.{ROW_BLACK, ROW_WHITE}

  val COL_FIGURE_LEFT = 0
  val COL_FIGURE_RIGHT = 7
  val COORDINATES_BLACK_LEFT: Coordinates = Coordinates(ROW_BLACK, COL_FIGURE_LEFT)
  val COORDINATES_BLACK_RIGHT: Coordinates = Coordinates(ROW_BLACK, COL_FIGURE_RIGHT)
  val COORDINATES_WHITE_LEFT: Coordinates = Coordinates(ROW_WHITE, COL_FIGURE_LEFT)
  val COORDINATES_WHITE_RIGHT: Coordinates = Coordinates(ROW_WHITE, COL_FIGURE_RIGHT)
}
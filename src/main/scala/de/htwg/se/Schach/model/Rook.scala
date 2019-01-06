package de.htwg.se.Schach.model

import _root_.de.htwg.se.Schach.model.Colour.Colour
import _root_.de.htwg.se.Schach.util.Utils._
import de.htwg.se.Schach.model.Side.Side


case class Rook(colour: Colour, coordinates: Coordinates, ability: Boolean) extends Figure {
  def this(colour: Colour, side: Side) = this(colour, {
    import Rook._
    if (colour == Colour.black) if (side == Side.left) COORDINATES_BLACK_LEFT else COORDINATES_BLACK_RIGHT
    else if (side == Side.left) COORDINATES_WHITE_LEFT else COORDINATES_WHITE_RIGHT
  }, true)

  override def getName: String = "Rook"

  override def getPossibleNewPositions(field: Field, coordinates: Coordinates): Vector[Vector[Coordinates]] = goMultiStepsCross(field, this, coordinates)

  override def toString: String = if (colour == Colour.black) "♜" else "♖"

  override def move(coordinates: Coordinates): Figure = Rook(this.colour, coordinates, ability = false)
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
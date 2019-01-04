package de.htwg.se.Schach.model

import _root_.de.htwg.se.Schach.model.Colour.Colour
import _root_.de.htwg.se.Schach.util.Utils._
import de.htwg.se.Schach.model.Figure.Side
import de.htwg.se.Schach.model.Figure.Side.Side

case class Knight(colour: Colour, coordinates: Coordinates) extends Figure {
  def this(colour: Colour, side: Side) = this(colour, {
    import Knight._
    if (colour == Colour.Black) if (side == Side.Left) COORDINATES_BLACK_LEFT else COORDINATES_BLACK_RIGHT
    else if (side == Side.Left) COORDINATES_WHITE_LEFT else COORDINATES_WHITE_RIGHT
  })

  override def getName: String = "Knight"

  override def getPossibleNewPositions: Vector[Vector[Coordinates]] = goKnightJump(coordinates)

  override var hasAbility: Boolean = false

  override def toString: String = if (colour == Colour.Black) "♞" else "♘"

  override def move(coordinates: Coordinates): Figure = Knight(this.colour, coordinates)
}

object Knight {

  import Figure.{ROW_BLACK, ROW_WHITE}

  val COL_FIGURE_LEFT = 1
  val COL_FIGURE_RIGHT = 6
  val COORDINATES_BLACK_LEFT: Coordinates = Coordinates(ROW_BLACK, COL_FIGURE_LEFT)
  val COORDINATES_BLACK_RIGHT: Coordinates = Coordinates(ROW_BLACK, COL_FIGURE_RIGHT)
  val COORDINATES_WHITE_LEFT: Coordinates = Coordinates(ROW_WHITE, COL_FIGURE_LEFT)
  val COORDINATES_WHITE_RIGHT: Coordinates = Coordinates(ROW_WHITE, COL_FIGURE_RIGHT)
}

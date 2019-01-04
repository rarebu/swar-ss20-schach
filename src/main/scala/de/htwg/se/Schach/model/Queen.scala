package de.htwg.se.Schach.model

import _root_.de.htwg.se.Schach.model.Colour.Colour
import _root_.de.htwg.se.Schach.util.Utils._

case class Queen(colour: Colour, coordinates: Coordinates) extends Figure {
  def this(colour: Colour) = this(colour, {
    import Queen._
    if (colour == Colour.Black) COORDINATES_BLACK else COORDINATES_WHITE
  })

  override def getName: String = "Queen"

  override def getPossibleNewPositions: Vector[Vector[Coordinates]] = goMultiStepsInAllDirections(coordinates)

  override var hasAbility: Boolean = false

  override def toString: String = if (colour == Colour.Black) "♛" else "♛"

  override def move(coordinates: Coordinates): Figure = Queen(this.colour, coordinates)
}

object Queen {

  import Figure.{ROW_BLACK, ROW_WHITE}

  val COL_FIGURE = 3
  val COORDINATES_BLACK: Coordinates = Coordinates(ROW_BLACK, COL_FIGURE)
  val COORDINATES_WHITE: Coordinates = Coordinates(ROW_WHITE, COL_FIGURE)
}

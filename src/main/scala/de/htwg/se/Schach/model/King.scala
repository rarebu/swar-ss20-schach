package de.htwg.se.Schach.model

import _root_.de.htwg.se.Schach.model.Colour.Colour
import _root_.de.htwg.se.Schach.util.Utils._

case class King(colour: Colour, coordinates: Coordinates, ability: Boolean) extends Figure {
  def this(colour: Colour) = this(colour, {
    import King._
    if (colour == Colour.black) COORDINATES_BLACK else COORDINATES_WHITE
  }, true)

  override def getName: String = "King"

  override def getPossibleNewPositions: Vector[Vector[Coordinates]] = goOneStepInAllDirections(coordinates)

  override def toString: String = if (colour == Colour.black) "♚" else "♔"

  override def move(coordinates: Coordinates): Figure = King(this.colour, coordinates, ability = false)
}

object King {

  import Figure.{ROW_BLACK, ROW_WHITE}

  val COL_FIGURE = 4
  val COORDINATES_BLACK: Coordinates = Coordinates(ROW_BLACK, COL_FIGURE)
  val COORDINATES_WHITE: Coordinates = Coordinates(ROW_WHITE, COL_FIGURE)
}

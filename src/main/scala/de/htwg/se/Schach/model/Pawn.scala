package de.htwg.se.Schach.model

import _root_.de.htwg.se.Schach.model.Colour.Colour
import _root_.de.htwg.se.Schach.util.Utils._

case class Pawn(colour: Colour, coordinates: Coordinates) extends Figure {
  def this(colour: Colour) = this(colour, {
    import Pawn._
    if (colour == Colour.Black) COORDINATES_BLACK else COORDINATES_WHITE
  })

  override def getName: String = "Pawn"

  override def getPossibleNewPositions(): Vector[Vector[Coordinates]] = {
    if (hasAbility) {
      hasAbility = false
      goTwoStepsUpOrOneStepUp(coordinates)
    } else {
      Vector(Vector(goOneStepUp(coordinates)))
    }
  }
  override var hasAbility: Boolean = true

  override def toString: String = if (colour == Colour.Black) "♟" else "♙"

}

object Pawn {

  import Figure.{ROW_BLACK, ROW_WHITE}

  val COL_FIGURE = 4
  val COORDINATES_BLACK: Coordinates = Coordinates(ROW_BLACK, COL_FIGURE)
  val COORDINATES_WHITE: Coordinates = Coordinates(ROW_WHITE, COL_FIGURE)
}

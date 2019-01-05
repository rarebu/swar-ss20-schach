package de.htwg.se.Schach.model

import _root_.de.htwg.se.Schach.model.Colour.Colour
import _root_.de.htwg.se.Schach.util.Utils._
import de.htwg.se.Schach.model.Figure._

case class Pawn(colour: Colour, coordinates: Coordinates, ability: Boolean) extends Figure {
  def this(colour: Colour, col: Int) = this(colour, {
    if (colour == Colour.black) Coordinates(ROW_BLACK_PAWN, col) else Coordinates(ROW_WHITE_PAWN, col)
  }, true)

  override def getName: String = "Pawn"

  override def getPossibleNewPositions: Vector[Vector[Coordinates]] = {
    if (colour == Colour.white) if (ability) goTwoStepsDownOrOneStepDown(coordinates) else Vector(Vector(goOneStepDown(coordinates)))
    else if (ability) goTwoStepsUpOrOneStepUp(coordinates) else Vector(Vector(goOneStepUp(coordinates)))
  }

  override def toString: String = if (colour == Colour.black) "♟" else "♙"

  override def move(coordinates: Coordinates): Figure = Pawn(this.colour, coordinates, ability = false)
}
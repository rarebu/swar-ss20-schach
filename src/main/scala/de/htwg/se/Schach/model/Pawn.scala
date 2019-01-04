package de.htwg.se.Schach.model

import _root_.de.htwg.se.Schach.model.Colour.Colour
import _root_.de.htwg.se.Schach.util.Utils._
import Figure._

case class Pawn(colour: Colour, coordinates: Coordinates) extends Figure {
  def this(colour: Colour, col: Int) = this(colour, {
    if (colour == Colour.Black) Coordinates(ROW_BLACK_PAWN, col) else Coordinates(ROW_WHITE_PAWN, col)
  })

  override def getName: String = "Pawn"

  override def getPossibleNewPositions: Vector[Vector[Coordinates]] = {
    if (hasAbility) {
      hasAbility = false
      goTwoStepsUpOrOneStepUp(coordinates)
    } else {
      Vector(Vector(goOneStepUp(coordinates)))
    }
  }

  override var hasAbility: Boolean = true

  override def toString: String = if (colour == Colour.Black) "♟" else "♙"

  override def move(coordinates: Coordinates): Figure = Pawn(this.colour, coordinates)
}
package de.htwg.se.Schach.model

import _root_.de.htwg.se.Schach.model.Colour.Colour
import de.htwg.se.Schach.model.rules.Moves.pawnMove

import scala.collection.mutable

private[model] case class Pawn(colour: Colour, ability: Boolean) extends Figure {

  override def getName: String = "Pawn"

  override def getPossibleNewPositions(field: Field, coordinates: Coordinates): Vector[Vector[Coordinates]] = pawnMove(field, this.colour, coordinates, ability)

  override def toString: String = if (colour == Colour.black) "♟" else "♙"

  override def move: Figure = Pawn(this.colour, ability = false)
}
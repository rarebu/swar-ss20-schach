package de.htwg.se.Schach.model.Figures

import _root_.de.htwg.se.Schach.model.Colour.Colour
import de.htwg.se.Schach.model.Field.Field
import de.htwg.se.Schach.model._
import de.htwg.se.Schach.model.rules.Moves.kingMove

case class King(colour: Colour, stepCounter: Int) extends Figure {

  override def getName: String = "King"

  override def getPossibleNewPositions(field: Field, coordinates: Coordinates): Vector[Vector[Coordinates]] = kingMove(field, this.colour, coordinates, hasAbility)

  override def toString: String = if (colour == Colour.black) "♚" else "♔"

  override def move: Figure = {
    King(this.colour, this.stepCounter + 1)
  }

  override def unMove: Figure = {
    King(this.colour, this.stepCounter - 1)
  }

  def hasAbility: Boolean = stepCounter == 0
}

object King {

  import Figure.{ROW_BLACK, ROW_WHITE}

  val COL_FIGURE = 4
  val COORDINATES_BLACK: Coordinates = Coordinates(ROW_BLACK, COL_FIGURE)
  val COORDINATES_WHITE: Coordinates = Coordinates(ROW_WHITE, COL_FIGURE)
}
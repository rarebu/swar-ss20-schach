package de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl

import de.htwg.se.Schach.model.FigureInterface
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Colour.Colour
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.rules.Moves._

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

  override def getStepCount: Int = stepCounter
}

object King {

  import Figure.{ROW_BLACK, ROW_WHITE}

  val COL_FIGURE = 4
  val COORDINATES_BLACK: Coordinates = Coordinates(ROW_BLACK, COL_FIGURE)
  val COORDINATES_WHITE: Coordinates = Coordinates(ROW_WHITE, COL_FIGURE)
}

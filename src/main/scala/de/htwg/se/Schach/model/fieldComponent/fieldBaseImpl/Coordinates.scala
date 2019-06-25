package de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl

import de.htwg.se.Schach.model.CoordinatesInterface

case class Coordinates(row: Int, col: Int) extends CoordinatesInterface {
  override def getCoordinates: (Int, Int) = (this.row, this.col)
}

package de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl

case class Coordinates(row: Int, col: Int) {
  def getCoordinates: (Int, Int) = (this.row, this.col)
}

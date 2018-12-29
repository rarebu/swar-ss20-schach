package de.htwg.se.Schach.model

class CellSpec {
  val cell1 = Cell(Colour.Black, Option.empty)

  cell1.colour == Colour.Black
  cell1.contains == None
}

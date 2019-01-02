package de.htwg.se.Schach.model

import King._
import Figure._

case class Field(cells: Matrix[Cell]) {
  def this() = this(new Matrix[Cell]((row, col) => {
    val a = (row, col) match {
      case (COL_FIGURE, ROW_BLACK) => Option.apply(new King(Colour.Black))
      case _ => Option.empty
    }
    if (row % 2 == 0)
      if (col % 2 == 0) Cell(Colour.Black, Option.empty) else Cell(Colour.White, Option.empty)
    else if (col % 2 == 0) Cell(Colour.White, Option.empty) else Cell(Colour.Black, Option.empty)
  }))
  def cell(row: Int, col: Int): Cell = cells.cell(row, col)
}
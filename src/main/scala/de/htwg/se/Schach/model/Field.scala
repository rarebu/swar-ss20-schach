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
      if (col % 2 == 0) Cell(Colour.White, a) else Cell(Colour.Black, a)
    else if (col % 2 == 0) Cell(Colour.Black, a) else Cell(Colour.White, a)
  }))

  def cell(row: Int, col: Int): Cell = cells.cell(row, col)

  override def toString: String = {
    val SIZE = 8
    val barrier = "|" + "♦––♦|" * SIZE + "\n"
    val line = "|" + "X|" * SIZE + "\n"
    var box = barrier + (line + barrier) * 8
    for {
      col <- 0 until SIZE
      row <- 0 until SIZE
    } box = box.replaceFirst("X", cell(row, col).toString)
    box
  }
}


package de.htwg.se.Schach.model

import Figure._
import Field._
import de.htwg.se.Schach.model.Colour.Colour
import de.htwg.se.Schach.model.rules.{Castling, PawnPromotion, ToChange}

case class Field(cells: Matrix[Cell], changeFigure: Option[ToChange]) {

  def this() = this(new Matrix[Cell]((row, col) => {
    val a = (row, col) match {
      case (ROW_BLACK, column) => getFigure(Colour.black, column)
      case (ROW_WHITE, column) => getFigure(Colour.white, column)
      case (ROW_BLACK_PAWN, column) => Option.apply(new Pawn(Colour.black, true))
      case (ROW_WHITE_PAWN, column) => Option.apply(new Pawn(Colour.white, true))
      case _ => Option.empty
    }
    if (row % 2 == 0)
      if (col % 2 == 0) Cell(Colour.white, a) else Cell(Colour.black, a)
    else if (col % 2 == 0) Cell(Colour.black, a) else Cell(Colour.white, a)
  }), None)

  def cell(row: Int, col: Int): Cell = cells.cell(row, col)

  def move(row: Int, col: Int, newRow: Int, newCol: Int): Field = {
    if (changeFigure.isDefined || cell(row, col).contains.isEmpty) this else {
      val figure = cell(row, col).contains.get
      val t = figure.getPossibleNewPositions(this, Coordinates(row, col)).flatten
      if (!t.contains(Coordinates(newRow, newCol))) this else {
        figure match {
          case king: King => if (Math.abs(col - newCol) == 2) return Castling.doCastling(Coordinates(row, col), Coordinates(newRow, newCol), this, king)
          case pawn: Pawn => PawnPromotion.doPawnPromotion(Coordinates(row, col), Coordinates(newRow, newCol), pawn, this) match {
            case Some(ret) => return ret
            case _ =>
          }
          case _ =>
        }
        copy(cells.replaceCell(row, col, Cell(cell(row, col).colour, Option.empty)).replaceCell(newRow, newCol, Cell(cell(newRow, newCol).colour,
          Option.apply(figure.move))), None)
      }
    }
  }

  def changePawn(input: String): Field = PawnPromotion.changePawn(this, changeFigure, input)

  override def toString: String = {
    val SIZE = 8
    val barrier = "|" + "♦––♦|" * SIZE + "\n"
    val line = "|" + "X|" * SIZE + "\n"
    var box = barrier + (line + barrier) * 8
    for {
      row <- 0 until SIZE
      col <- 0 until SIZE
    } box = box.replaceFirst("X", cell(row, col).toString)
    if (changeFigure.isEmpty) {
      box
    } else {
      box + "\n\n Choose a figure " + PawnPromotion.pawnChange(changeFigure.get.figure.colour)
    }
  }
}

private object Field {
  def getFigure(colour: Colour, col: Int): Option[Figure] = {
    col match {
      case King.COL_FIGURE => Option.apply(new King(colour, true))
      case Queen.COL_FIGURE => Option.apply(new Queen(colour))
      case Rook.COL_FIGURE_LEFT => Option.apply(new Rook(colour, true))
      case Rook.COL_FIGURE_RIGHT => Option.apply(new Rook(colour, true))
      case Knight.COL_FIGURE_LEFT => Option.apply(new Knight(colour))
      case Knight.COL_FIGURE_RIGHT => Option.apply(new Knight(colour))
      case Bishop.COL_FIGURE_LEFT => Option.apply(new Bishop(colour))
      case Bishop.COL_FIGURE_RIGHT => Option.apply(new Bishop(colour))

    }
  }
}
package de.htwg.se.Schach.model

import Figure._
import Field._
import de.htwg.se.Schach.model.Colour.Colour

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
          case king: King => {
            val tmp = col - newCol
            if (Math.abs(tmp) == 2) {
              if (tmp > 0) {
                return copy(cells.replaceCell(row, 0, Cell(cell(row, 0).colour, Option.empty)).replaceCell(row, 3, Cell(cell(row, 3).colour,
                  Option.apply(cell(row, 0).contains.get.move))).replaceCell(row, col, Cell(cell(row, col).colour, Option.empty)).replaceCell(newRow, newCol,
                  Cell(cell(newRow, newCol).colour, Option.apply(figure.move))), None)
              } else {
                return copy(cells.replaceCell(row, 7, Cell(cell(row, 7).colour, Option.empty)).replaceCell(row, 5, Cell(cell(row, 5).colour,
                  Option.apply(cell(row, 7).contains.get.move))).replaceCell(row, col, Cell(cell(row, col).colour, Option.empty)).replaceCell(newRow, newCol,
                  Cell(cell(newRow, newCol).colour, Option.apply(figure.move))), None)
              }
            }
          }
          case pawn: Pawn => {
            if (pawn.colour == Colour.black && newRow == Figure.ROW_WHITE) {
              return copy(cells.replaceCell(row, col, Cell(cell(row, col).colour, Option.empty)).replaceCell(newRow, newCol, Cell(cell(newRow, newCol).colour,
                Option.apply(figure.move))), Option.apply(ToChange(Coordinates(newRow, newCol), cell(newRow, newCol), cell(row, col).contains.get)))
            }
            if (pawn.colour == Colour.white && newRow == Figure.ROW_BLACK) {
              return copy(cells.replaceCell(row, col, Cell(cell(row, col).colour, Option.empty)).replaceCell(newRow, newCol, Cell(cell(newRow, newCol).colour,
                Option.apply(figure.move))), Option.apply(ToChange(Coordinates(newRow, newCol), cell(newRow, newCol), cell(row, col).contains.get)))
            }
          }
          case _ =>
        }
        copy(cells.replaceCell(row, col, Cell(cell(row, col).colour, Option.empty)).replaceCell(newRow, newCol, Cell(cell(newRow, newCol).colour,
          Option.apply(figure.move))), None)
      }
    }
  }

  def pawnChange(colour: Colour): String = {
    colour match {
      case Colour.black => Figure.CHANGABLE_BLACK_FIGURES
      case _ => Figure.CHANGABLE_WHITE_FIGURES
    }
  }

  def findFigure(colour: Colour, input: String): Option[Figure] = {
    if (colour == Colour.black && Figure.CHANGABLE_BLACK_FIGURES.contains(input)) {
      Option.apply(input match {
        case "♛" => Queen(Colour.black)
        case "♜" => Rook(Colour.black, false)
        case "♝" => Bishop(Colour.black)
        case "♞" => Knight(Colour.black)
      })
    }
    else if (colour == Colour.white && Figure.CHANGABLE_WHITE_FIGURES.contains(input)) {
      Option.apply(input match {
        case "♕" => Queen(Colour.white)
        case "♖" => Rook(Colour.white, false)
        case "♗" => Bishop(Colour.white)
        case "♘" => Knight(Colour.white)
      })
    } else None
  }

  def changePawn(input: String): Field = {
    if (changeFigure.isDefined) {
      val tmp = changeFigure.get
      val coordinates = tmp.coordinates
      val cell = tmp.cell
      val figure = tmp.figure
      val colour = figure.colour
      val z = findFigure(colour, input)
      if (z.isDefined) {
        copy(cells.replaceCell(coordinates.row, coordinates.col, Cell(cell.colour, z)), None)
      } else this
    } else this
  }

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
      box + "\n\n Choose a figure " + pawnChange(changeFigure.get.figure.colour)
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

case class ToChange(coordinates: Coordinates, cell: Cell, figure: Figure)

//TODO refactor
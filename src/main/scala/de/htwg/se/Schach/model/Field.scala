package de.htwg.se.Schach.model

import Figure._
import Field._
import de.htwg.se.Schach.model.Colour.Colour
import de.htwg.se.Schach.model.rules.{Castling, PawnPromotion, ToChange}

case class Field(cells: Matrix[Cell], changeFigure: Option[ToChange], roundCounter: Int, removedFigures: RemovedFigures) {
  var wrongInput: Boolean = false

  def this() = this(new Matrix[Cell]((row, col) => {
    val a = (row, col) match {
      case (ROW_BLACK, column) => getFigure(Colour.black, column)
      case (ROW_WHITE, column) => getFigure(Colour.white, column)
      case (ROW_BLACK_PAWN, column) => Option.apply(new Pawn(Colour.black, 0))
      case (ROW_WHITE_PAWN, column) => Option.apply(new Pawn(Colour.white, 0))
      case _ => Option.empty
    }
    if (row % 2 == 0)
      if (col % 2 == 0) Cell(Colour.white, a) else Cell(Colour.black, a)
    else if (col % 2 == 0) Cell(Colour.black, a) else Cell(Colour.white, a)
  }), None, 0, new RemovedFigures())

  def cell(row: Int, col: Int): Cell = cells.cell(row, col)

  def undoStep(input: String): Field = {
    val o = removedFigures.containsFigureThatGotRemovedThisRound(roundCounter - 1) match {
      case Some(entry) => Option.apply(entry.figure)
      case None => None
    }
//    return Option.apply(copy(cells.replaceCell(row, col, Cell(cell(row, col).colour, o)).replaceCell(newRow, newCol, Cell(cell(newRow, newCol).colour,
//      Option.apply(figure.unMove))), None, roundCounter - 1))

    this
  }

  def doStep: Field = {
    this
  }

  def processInput(input: String, undo: Boolean): Field = {
//    println("process " + roundCounter)
    val pattern = {
      "[" + Figure.CHANGABLE_BLACK_FIGURES + Figure.CHANGABLE_WHITE_FIGURES + "]"
    }.r

    val a = input.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
      case row :: column :: newRow :: newColumn :: Nil => if (undo) {
        move(newRow, newColumn, row, column, undo)
      } else move(row, column, newRow, newColumn, undo)
      case _ => {
        pattern.findFirstIn(input) match {
          case Some(c) => {
            if (undo) {
              removedFigures.containsFigureThatGotRemovedThisRound(roundCounter) match {
                case Some(fig) => Option.apply(copy(cells.replaceCell(fig.coordinates.row, fig.coordinates.col, Cell(cell(fig.coordinates.row, fig.coordinates.col).colour, Option.apply(fig.figure))), Option.apply(ToChange(fig.coordinates, cell(fig.coordinates.row, fig.coordinates.col), fig.figure)), roundCounter - 1))
                case None => None
              }
            } else changePawn(c)
          }
          case _ => None
        }
      }
    }
    a match {
      case Some(a) => a
      case None => {
        wrongInput = true
        this
      }
    }
  }

  def move(row: Int, col: Int, newRow: Int, newCol: Int, undo: Boolean): Option[Field] = {
    if ((changeFigure.isDefined && !undo) || cell(row, col).contains.isEmpty) None else {
      val figure = cell(row, col).contains.get
      val t = figure.getPossibleNewPositions(this, Coordinates(row, col)).flatten
      if (undo) {
        figure match {
          case king: King => if (Math.abs(col - newCol) == 2) return Option.apply(Castling.undoCastling(Coordinates(row, col), Coordinates(newRow, newCol), this, king))
          case _ =>
        }
        val o = removedFigures.containsFigureThatGotRemovedThisRound(roundCounter) match {
          case Some(entry) => Option.apply(entry.figure)
          case None => None
        }
        return Option.apply(copy(cells.replaceCell(row, col, Cell(cell(row, col).colour, o)).replaceCell(newRow, newCol, Cell(cell(newRow, newCol).colour,
          Option.apply(figure.unMove))), None, roundCounter - 1))
      }
      if (t.contains(Coordinates(newRow, newCol))) {
        figure match {
          case king: King => if (Math.abs(col - newCol) == 2) return Option.apply(Castling.doCastling(Coordinates(row, col), Coordinates(newRow, newCol), this, king))
          case pawn: Pawn => PawnPromotion.doPawnPromotion(Coordinates(row, col), Coordinates(newRow, newCol), pawn, this) match {
            case Some(ret) => {
              removedFigures.append(Entry(pawn.move, Coordinates(newRow, newCol), roundCounter + 2))
              return Option.apply(ret) }
            case _ =>
          }
          case _ =>
        }
        if (cell(newRow, newCol).contains.isDefined) removedFigures.append(Entry(cell(newRow, newCol).contains.get, Coordinates(newRow, newCol), roundCounter + 1))
        Option.apply(copy(cells.replaceCell(row, col, Cell(cell(row, col).colour, Option.empty)).replaceCell(newRow, newCol, Cell(cell(newRow, newCol).colour,
          Option.apply(figure.move))), None, roundCounter + 1))
      } else None
    }
  }

  def changePawn(input: String): Option[Field] = PawnPromotion.changePawn(this, changeFigure, input)

  override def toString: String = {
    val SIZE = 8
    val barrier = "|" + "♦––♦|" * SIZE + "\n"
    val line = "|" + "X|" * SIZE + "\n"
    var box = barrier + (line + barrier) * 8
    for {
      row <- 0 until SIZE
      col <- 0 until SIZE
    } box = box.replaceFirst("X", cell(row, col).toString)
    box = "Step: " + roundCounter + "\n" + box
    if (wrongInput) {
//      wrongInput = false
      "Wrong Input!"
    } else if (changeFigure.isEmpty) {
      box
    } else {
      box + "\n\n Choose a figure " + PawnPromotion.pawnChange(changeFigure.get.figure.colour)
    }
  }
}

private object Field {
  def getFigure(colour: Colour, col: Int): Option[Figure] = {
    col match {
      case King.COL_FIGURE => Option.apply(new King(colour, 0))
      case Queen.COL_FIGURE => Option.apply(new Queen(colour, 0))
      case Rook.COL_FIGURE_LEFT => Option.apply(new Rook(colour, 0))
      case Rook.COL_FIGURE_RIGHT => Option.apply(new Rook(colour, 0))
      case Knight.COL_FIGURE_LEFT => Option.apply(new Knight(colour, 0))
      case Knight.COL_FIGURE_RIGHT => Option.apply(new Knight(colour, 0))
      case Bishop.COL_FIGURE_LEFT => Option.apply(new Bishop(colour, 0))
      case Bishop.COL_FIGURE_RIGHT => Option.apply(new Bishop(colour, 0))

    }
  }
}
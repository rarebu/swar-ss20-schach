package de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl

import de.htwg.se.Schach.model._
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Colour.Colour
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.rules.{Castling, PawnPromotion, ToChange}
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Figure._
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field._

case class Field(cells: Matrix[Cell], changeFigure: Option[ToChange], roundCounter: Int, removedFigures: RemovedFigures) extends FieldInterface {
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

  def this(figurePositions:List[(CoordinatesInterface, FigureInterface)], toChange: String, roundCount:Int, removedFigures: RemovedFiguresInterface) = this(new Matrix[Cell]((row, col) => {
    val ab: Option[(CoordinatesInterface, FigureInterface)] = figurePositions.find(figure => { figure._1.getCoordinates == (row, col) })
    var d:Option[Figure] = Option.empty
    if(ab.isDefined) {
      val tm = ab.get._2
      val c:String = tm.getRepresentation
      val col = if (tm.isBlack) Colour.black else Colour.white
      d = Figure.apply(c, col, 0)
    }
    if (row % 2 == 0)
      if (col % 2 == 0) Cell(Colour.white, d) else Cell(Colour.black, d)
    else if (col % 2 == 0) Cell(Colour.black, d) else Cell(Colour.white, d)
  }), None, roundCount, null)

  def cell(row: Int, col: Int): Cell = cells.cell(row, col)

  def moveTwoFigures(coordinates: Coordinates, newCoordinates: Coordinates, figure: Figure, coordinates1: Coordinates, newCoordinates1: Coordinates,
                     figure1: Figure, undo: Boolean): Field = {
    replaceFour(coordinates, Cell(cell(coordinates.row, coordinates.col).colour, Option.empty), newCoordinates,
      Cell(cell(newCoordinates.row, newCoordinates.col).colour, Option.apply(figure)), coordinates1, Cell(cell(coordinates1.row, coordinates1.col).colour,
        Option.empty), newCoordinates1, Cell(cell(newCoordinates1.row, newCoordinates1.col).colour, Option.apply(figure1)), undo)
  }

  def moveOneFigure(coordinates: Coordinates, newCoordinates: Coordinates, change: Option[ToChange], figure: Figure): Field = {
    replaceTwo(coordinates, Cell(cell(coordinates.row, coordinates.col).colour, Option.empty), newCoordinates,
      Cell(cell(newCoordinates.row, newCoordinates.col).colour, Option.apply(figure)), change, false)
  }

  def replace(coordinates: Coordinates, cell: Cell, change: Option[ToChange], undo: Boolean): Field = {
    val op: (Int, Int) => Int = if (undo) _ - _ else _ + _
    copy(cells
      .replaceCell(coordinates.row, coordinates.col, cell)
      , change, op(roundCounter, 1))
  }

  def replaceTwo(coordinates: Coordinates, cell: Cell, coordinates1: Coordinates, cell1: Cell, change: Option[ToChange], undo: Boolean): Field = {
    val op: (Int, Int) => Int = if (undo) _ - _ else _ + _
    copy(cells
      .replaceCell(coordinates.row, coordinates.col, cell)
      .replaceCell(coordinates1.row, coordinates1.col, cell1)
      , change, op(roundCounter, 1))
  }

  def replaceFour(coordinates: Coordinates, cell: Cell, coordinates1: Coordinates, cell1: Cell, coordinates2: Coordinates, cell2: Cell,
                  coordinates3: Coordinates, cell3: Cell, undo: Boolean): Field = {
    val op: (Int, Int) => Int = if (undo) _ - _ else _ + _
    copy(cells
      .replaceCell(coordinates.row, coordinates.col, cell)
      .replaceCell(coordinates1.row, coordinates1.col, cell1)
      .replaceCell(coordinates2.row, coordinates2.col, cell2)
      .replaceCell(coordinates3.row, coordinates3.col, cell3)
      , None, op(roundCounter, 1))
  }

  override def move(row: Int, col: Int, newRow: Int, newCol: Int): Option[Field] = {
    val a = if (changeFigure.isDefined || cell(row, col).contains.isEmpty) None else {
      val figure = cell(row, col).contains.get
      val t = figure.getPossibleNewPositions(this, Coordinates(row, col)).flatten
      if (t.contains(Coordinates(newRow, newCol))) {
        figure match {
          case king: King => if (Math.abs(col - newCol) == 2) return Option.apply(Castling.doCastling(Coordinates(row, col), Coordinates(newRow, newCol), this, king))
          case pawn: Pawn => PawnPromotion.doPawnPromotion(Coordinates(row, col), Coordinates(newRow, newCol), pawn, this) match {
            case Some(ret) => {
              removedFigures.append(Entry(pawn.move, Coordinates(newRow, newCol), roundCounter + 2))
              return Option.apply(ret)
            }
            case _ =>
          }
          case _ =>
        }
        if (cell(newRow, newCol).contains.isDefined) removedFigures.append(Entry(cell(newRow, newCol).contains.get, Coordinates(newRow, newCol), roundCounter + 1))
        Option.apply(moveOneFigure(Coordinates(row, col), Coordinates(newRow, newCol), None, figure.move))
      } else None
    }
    a match {
      case Some(_) =>
      case None => {
        wrongInput = true
      }
    }
    a
  }

  override def unMove(row: Int, col: Int, newRow: Int, newCol: Int): Field = {
    val figure = cell(row, col).contains.get
    figure match {
      case king: King => if (Math.abs(col - newCol) == 2) return Castling.undoCastling(Coordinates(row, col), Coordinates(newRow, newCol), this, king)
      case _ =>
    }
    val o = removedFigures.containsFigureThatGotRemovedThisRound(roundCounter) match {
      case Some(entry) => Option.apply(entry.figure)
      case None => None
    }
    replaceTwo(Coordinates(row, col), Cell(cell(row, col).colour, o), Coordinates(newRow, newCol), Cell(cell(newRow, newCol).colour,
      Option.apply(figure.unMove)), None, true)
  }

  override def changePawn(input: String): Option[Field] = {
    val a = PawnPromotion.changePawn(this, changeFigure, input)
    a match {
      case Some(_) =>
      case None => {
        wrongInput = true
      }
    }
    a
  }

  override def undoChangePawn(input: String): Option[Field] = {
    val a = PawnPromotion.undoChangePawn(this, input)
    a match {
      case Some(_) =>
      case None => {
        wrongInput = true
      }
    }
    a
  }

  override def getFigures: Option[String] = if (changeFigure.isDefined) Option.apply(PawnPromotion.pawnChange(changeFigure.get.figure.colour)) else None

  override def CHANGEABLE_BLACK_FIGURES: String = Figure.CHANGEABLE_BLACK_FIGURES

  override def CHANGEABLE_WHITE_FIGURES: String = Figure.CHANGEABLE_WHITE_FIGURES

  override def cellIsBlack(row: Int, col: Int): Boolean = cell(row, col).isBlack

  override def cellContains(row: Int, col: Int): Option[String] = {
    val tmp = cell(row, col).contains
    if (tmp.isDefined) Some(tmp.get.toString) else None
  }

  override def toString: String = {
    val barrier = "|" + "♦––♦|" * SIZE + "\n"
    val line = "|" + "X|" * SIZE + "\n"
    var box = barrier + (line + barrier) * SIZE
    for {
      row <- 0 until SIZE
      col <- 0 until SIZE
    } box = box.replaceFirst("X", cell(row, col).toString)
    box = "Step: " + roundCounter + "\n" + box
    if (wrongInput) {
      "Wrong Input!"
    } else if (changeFigure.isEmpty) {
      box
    } else {
      box + "\n\n Choose a figure " + getFigures.get
    }
  }

  override def getSize: Int = SIZE

  override def getRoundCount: Int = roundCounter

  override def getToChange: String = if (changeFigure.isDefined) changeFigure.get.figure.colour.toString else ""
}

//private[fieldComponent]
object Field {
  val SIZE = 8

  def getFigure(colour: Colour, col: Int): Option[Figure] = {
    col match {
      case King.COL_FIGURE => Figure.applyNew("King", colour)
      case Queen.COL_FIGURE => Figure.applyNew("Queen", colour)
      case Rook.COL_FIGURE_LEFT => Figure.applyNew("Rook", colour)
      case Rook.COL_FIGURE_RIGHT => Figure.applyNew("Rook", colour)
      case Knight.COL_FIGURE_LEFT => Figure.applyNew("Knight", colour)
      case Knight.COL_FIGURE_RIGHT => Figure.applyNew("Knight", colour)
      case Bishop.COL_FIGURE_LEFT => Figure.applyNew("Bishop", colour)
      case Bishop.COL_FIGURE_RIGHT => Figure.applyNew("Bishop", colour)

    }
  }
}

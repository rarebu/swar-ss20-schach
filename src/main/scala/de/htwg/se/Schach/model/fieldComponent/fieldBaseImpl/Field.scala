package de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl

import de.htwg.se.Schach.model._
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Colour.Colour
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.rules.{Castling, PawnPromotion, ToChange}
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Figure._
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field._
import de.htwg.se.Schach.util.Utils
import play.api.libs.json._

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

case class Field(cells: Matrix[Cell], changeFigure: Option[ToChange], roundCounter: Int, removedFigures: RemovedFigures) extends FieldInterface {

  def this() = this(new Matrix[Cell]((row, col) => {
    val a = (row, col) match {
      case (ROW_BLACK, column) => getFigure(Colour.black, column)
      case (ROW_WHITE, column) => getFigure(Colour.white, column)
      case (ROW_BLACK_PAWN, _) => Option.apply(Pawn(Colour.black, 0))
      case (ROW_WHITE_PAWN, _) => Option.apply(Pawn(Colour.white, 0))
      case _ => Option.empty
    }

    val colIsEven = Utils.isEven(col)
    if (Utils.isEven(row))
      if (colIsEven) Cell(Colour.white, a) else Cell(Colour.black, a)
    else if (colIsEven) Cell(Colour.black, a) else Cell(Colour.white, a)
  }), None, 0, new RemovedFigures())

  def this(figurePositions:List[FigureInterface], toChange: Option[ToChangeInterface], removedFigures: List[RemovedFigureInterface], roundCount:Int) =
    this(new Matrix[Cell]((row, col) => {
      val content: Option[FigureInterface] = figurePositions.find(figure => { figure.getPosition == (row, col) })

      def figureInterfaceToFigure(tmpFig: FigureInterface) = {
        val tmpColour = if (tmpFig.isBlack) Colour.black else Colour.white
        Figure.apply(tmpFig.getKind, tmpColour, tmpFig.getStepCount)
      }

      val d:Option[Figure] = if (content.isDefined) figureInterfaceToFigure(content.get) else Option.empty

      if (Utils.isEven(row))
        if (Utils.isEven(col)) Cell(Colour.white, d) else Cell(Colour.black, d)
      else if (Utils.isEven(col)) Cell(Colour.black, d) else Cell(Colour.white, d)
    }),
      {
        def factorToChange(tmpToChange:ToChangeInterface) = {
            val tmpFigurePers = tmpToChange.getFigure
            val tmpFigureCol = if (tmpFigurePers.isBlack) Colour.black else Colour.white
            val tmpFigure: Figure = Figure.apply(tmpFigurePers.getKind, tmpFigureCol, tmpFigurePers.getStepCount).get
            val tmpFigurePosition = tmpToChange.getFigure.getPosition
            Option.apply(ToChange(Coordinates(tmpFigurePosition._1, tmpFigurePosition._2), if(tmpToChange.isBlack) Colour.black else Colour.white, tmpFigure))
        }
        val tmpChangeFigure: Option[ToChange] = if (toChange.isDefined) factorToChange(toChange.get) else Option.empty
        tmpChangeFigure
      }, roundCount, {
        val tmpRemovedFigures:RemovedFigures = new RemovedFigures(removedFigures)
        tmpRemovedFigures
      })

  def this (field:FieldDataInterface) = this(field.getFigurePositions, field.getToChange, field.getRemovedFigures, field.getRoundCount)



  override def getField:FieldDataInterface = {

    def factorChangeFigure(change: ToChange) = {
      val figure = change.figure
      val coords = change.coordinates
      Option.apply(PersistToChange(PersistFigure(figure.colour == Colour.black, figure.getName, figure.getStepCount, (coords.row,coords.col)),
        change.colour == Colour.black))
    }

    val toChange = if(changeFigure.isDefined) factorChangeFigure(changeFigure.get) else Option.empty
    val figureList = (0 until SIZE).flatMap(row => (0 until SIZE)
      .filter(col => cell(row, col).contains.isDefined).map(col => {
      val tmpFigure = cell(row, col).contains.get
      PersistFigure(tmpFigure.colour == Colour.black, tmpFigure.getName, tmpFigure.getStepCount, (row,col))
    })).toList

    PersistField(figureList, toChange, removedFigures.persistRemoveFigures, roundCounter)
  }

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
      case None =>
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
      case None =>
    }
    a
  }

  override def undoChangePawn(input: String): Option[Field] = {
    val a = PawnPromotion.undoChangePawn(this, input)
    a match {
      case Some(_) =>
      case None =>
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
    val box = barrier + (line + barrier) * SIZE
    val content = cells.flatten.iterator;
    box.iterator.map(c => if(c.equals('X')) content.next() else c).mkString
  }

  override def getSize: Int = SIZE

  override def getRoundCount: Int = roundCounter

  override def getToChange: String = if (changeFigure.isDefined) changeFigure.get.figure.colour.toString else ""

  implicit val figureWrites = new Writes[FigureInterface] {
    override def writes(figure: FigureInterface): JsValue = Json.obj(
      "isBlack" -> figure.isBlack,
      "kind" -> figure.getKind,
      "row" -> figure.getPosition._1,
      "col" -> figure.getPosition._2
    )
  }
  override def toJson: JsValue = Json.obj(
    "figurePostions" -> Json.toJson(
      for (figure <- this.getField.getFigurePositions)
      yield Json.toJson(figure))
  )
}

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

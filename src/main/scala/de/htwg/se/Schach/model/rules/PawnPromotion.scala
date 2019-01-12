package de.htwg.se.Schach.model.rules

import de.htwg.se.Schach.model.Colour.Colour
import de.htwg.se.Schach.model._

object PawnPromotion {
  def doPawnPromotion(coordinates: Coordinates, newCoordinates: Coordinates, pawn: Pawn, field: Field): Option[Field] = {
    val row = coordinates.row
    val col = coordinates.col
    val newRow = newCoordinates.row
    val newCol = newCoordinates.col
    if (pawn.colour == Colour.black && newRow == Figure.ROW_WHITE) {
      return Option.apply(field.copy(field.cells.replaceCell(row, col, Cell(field.cell(row, col).colour, Option.empty)).replaceCell(newRow, newCol, Cell(field.cell(newRow, newCol).colour,
        Option.apply(pawn.move))), Option.apply(ToChange(Coordinates(newRow, newCol), field.cell(newRow, newCol), field.cell(row, col).contains.get))))
    }
    if (pawn.colour == Colour.white && newRow == Figure.ROW_BLACK) {
      return Option.apply(field.copy(field.cells.replaceCell(row, col, Cell(field.cell(row, col).colour, Option.empty)).replaceCell(newRow, newCol, Cell(field.cell(newRow, newCol).colour,
        Option.apply(pawn.move))), Option.apply(ToChange(Coordinates(newRow, newCol), field.cell(newRow, newCol), field.cell(row, col).contains.get))))
    }
    None
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

  def changePawn(field: Field, changeFigure: Option[ToChange], input: String): Field = {
    if (changeFigure.isDefined) {
      val tmp = changeFigure.get
      val coordinates = tmp.coordinates
      val cell = tmp.cell
      val figure = tmp.figure
      val colour = figure.colour
      val z = findFigure(colour, input)
      if (z.isDefined) {
        field.copy(field.cells.replaceCell(coordinates.row, coordinates.col, Cell(cell.colour, z)), None)
      } else field
    } else field
  }
}

case class ToChange(coordinates: Coordinates, cell: Cell, figure: Figure)
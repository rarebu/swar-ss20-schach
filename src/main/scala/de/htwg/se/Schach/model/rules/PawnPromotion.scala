package de.htwg.se.Schach.model.rules

import de.htwg.se.Schach.model.Colour.Colour
import de.htwg.se.Schach.model._

object PawnPromotion {
  def doPawnPromotion(coordinates: Coordinates, newCoordinates: Coordinates, pawn: Pawn, field: Field): Option[Field] = {
    val row = coordinates.row
    val col = coordinates.col
    val newRow = newCoordinates.row
    val newCol = newCoordinates.col
    if (pawn.colour == Colour.black && newRow == Figure.ROW_WHITE || pawn.colour == Colour.white && newRow == Figure.ROW_BLACK) {
      if (field.cell(newRow, newCol).contains.isDefined) field.removedFigures.append(Entry(field.cell(newRow, newCol).contains.get,
        Coordinates(newRow, newCol), field.roundCounter + 1))
      Option.apply(field.moveOneFigure(coordinates, newCoordinates, Option.apply(ToChange(Coordinates(newRow, newCol), field.cell(newRow, newCol),
        field.cell(row, col).contains.get)), pawn.move))
    } else None
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
        case "♛" => Queen(Colour.black, 1)
        case "♜" => Rook(Colour.black, 1)
        case "♝" => Bishop(Colour.black, 1)
        case "♞" => Knight(Colour.black, 1)
      })
    }
    else if (colour == Colour.white && Figure.CHANGABLE_WHITE_FIGURES.contains(input)) {
      Option.apply(input match {
        case "♕" => Queen(Colour.white, 1)
        case "♖" => Rook(Colour.white, 1)
        case "♗" => Bishop(Colour.white, 1)
        case "♘" => Knight(Colour.white, 1)
      })
    } else None
  }

  def changePawn(field: Field, changeFigure: Option[ToChange], input: String): Option[Field] = {
    if (changeFigure.isDefined) {
      val tmp = changeFigure.get
      val coordinates = tmp.coordinates
      val cell = tmp.cell
      val figure = tmp.figure
      val colour = figure.colour
      val z = findFigure(colour, input)
      if (z.isDefined) {
        Option.apply(field.replace(coordinates, Cell(cell.colour, z), None, false))
      } else None
    } else None
  }
}

case class ToChange(coordinates: Coordinates, cell: Cell, figure: Figure)
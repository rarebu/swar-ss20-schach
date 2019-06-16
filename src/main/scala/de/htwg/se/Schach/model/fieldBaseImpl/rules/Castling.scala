package de.htwg.se.Schach.model.fieldBaseImpl.rules

import de.htwg.se.Schach.model._
import de.htwg.se.Schach.model.fieldBaseImpl.{Coordinates, Field, King, Rook}

object Castling {
  def doCastling(coordinates: Coordinates, newCoordinates: Coordinates, field: Field, king: King): Field = {
    val row = coordinates.row
    val col = coordinates.col
    val newRow = newCoordinates.row
    val newCol = newCoordinates.col
    val tmp = col - newCol
    if (tmp > 0) {
      field.moveTwoFigures(coordinates, newCoordinates, king.move, Coordinates(row, 0), Coordinates(row, 3), field.cell(row, 0).contains.get.move, false)
    } else {
      field.moveTwoFigures(coordinates, newCoordinates, king.move, Coordinates(row, 7), Coordinates(row, 5), field.cell(row, 7).contains.get.move, false)
    }
  }

  def undoCastling(coordinates: Coordinates, newCoordinates: Coordinates, field: Field, king: King): Field = {
    val newRow = coordinates.row
    val newCol = coordinates.col
    val row = newCoordinates.row
    val col = newCoordinates.col
    val tmp = col - newCol
    if (tmp > 0) {
      field.moveTwoFigures(coordinates, newCoordinates, king.unMove, Coordinates(row, 3), Coordinates(row, 0), field.cell(row, 3).contains.get.unMove, true)
    } else {
      field.moveTwoFigures(coordinates, newCoordinates, king.unMove, Coordinates(row, 7), Coordinates(row, 5), field.cell(row, 7).contains.get.unMove, true)
    }
  }

  def castlingPossible(field: Field, coordinates: Coordinates, towerCol: Int, range: Range): Boolean = {
    val tmp = field.cell(coordinates.row, towerCol).contains
    (tmp.isDefined && (
      tmp.get match {
        case fig: Rook => fig.hasAbility
        case _ => false
      }) && { // check if fields between rook and king are empty
      var u = true
      for (x <- range) {
        field.cell(coordinates.row, x).contains match {
          case Some(_) => u = false
          case _ =>
        }
      }
      u
    })
  }

  def castleKingside(field: Field, coordinates: Coordinates): Option[Coordinates] = {
    val towerCol = 7
    if (castlingPossible(field, coordinates, towerCol, (coordinates.col + 1) to (towerCol - 1))) Option.apply(Coordinates(coordinates.row, towerCol - 1)) else None

  }

  def castleQueenside(field: Field, coordinates: Coordinates): Option[Coordinates] = {
    val towerCol = 0
    if (castlingPossible(field, coordinates, towerCol, (towerCol + 1) to (coordinates.col - 1))) Option.apply(Coordinates(coordinates.row, 2)) else None
  }
}
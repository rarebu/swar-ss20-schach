package de.htwg.se.Schach.model.rules

import de.htwg.se.Schach.model.{Coordinates, Field, Rook}

object Castling {
  def castlingPossible(field: Field, coordinates: Coordinates, towerCol: Int, range: Range): Boolean = {
    val tmp = field.cell(coordinates.row, towerCol).contains
    (tmp.isDefined && (
      tmp.get match {
        case fig: Rook => fig.ability
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
    if (castlingPossible(field, coordinates, towerCol, (towerCol + 1) to (coordinates.col - 1))) Option.apply(Coordinates(coordinates.row, 2))else None
  }
}
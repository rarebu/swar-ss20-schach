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
    if (castlingPossible(field, coordinates, 7, 5 to 6)) Option.apply(Coordinates(coordinates.row, 6))
    else None

  }

  def castleQueenside(field: Field, coordinates: Coordinates): Option[Coordinates] = {
    if (castlingPossible(field, coordinates, 0, 1 to 3))
      Option.apply(Coordinates(coordinates.row, 2))
    else None
  }
}
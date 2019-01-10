package de.htwg.se.Schach.model.rules

import de.htwg.se.Schach.model.{Colour, Coordinates, Field, Figure}
import de.htwg.se.Schach.util.Utils.{kingMove, _}

import scala.collection.mutable

object Moves {
  def bishopMove(field: Field, figure: Figure, coordinates: Coordinates): Vector[Vector[Coordinates]] = goMultiStepsDiagonal(field, figure, coordinates)

  def knightMove(field: Field, figure: Figure, coordinates: Coordinates): Vector[Vector[Coordinates]] = goKnightJump(field, figure, coordinates)

  def rookMove(field: Field, figure: Figure, coordinates: Coordinates): Vector[Vector[Coordinates]] = goMultiStepsCross(field, figure, coordinates)

  def queenMove(field: Field, figure: Figure, coordinates: Coordinates): Vector[Vector[Coordinates]] = goMultiStepsInAllDirections(field, this, coordinates)

  def pawnMove(field: Field, figure: Figure, coordinates: Coordinates, ability: Boolean): Vector[Vector[Coordinates]] = {
    if (figure.colour == Colour.white) {
      val tmp: Vector[Vector[Coordinates]] = if (!isOponent(field, Coordinates(coordinates.row - 1, coordinates.col), Colour.white)) {
        if (ability)
          if (isOponent(field, Coordinates(coordinates.row - 2, coordinates.col), Colour.white)) removeInvalidsFromMultiVector(field, figure, Vector(Vector(goOneStepDown(coordinates))))
          else goTwoStepsDownOrOneStepDown(field, figure, coordinates)
        else removeInvalidsFromMultiVector(field, figure, Vector(Vector(goOneStepDown(coordinates))))
      } else Nil.toVector
      var g: mutable.Buffer[Vector[Coordinates]] = tmp.toBuffer
      if (isOponent(field, Coordinates(coordinates.row - 1, coordinates.col - 1), Colour.white)) g += Vector(Coordinates(coordinates.row - 1, coordinates.col - 1))
      if (isOponent(field, Coordinates(coordinates.row - 1, coordinates.col + 1), Colour.white)) g += Vector(Coordinates(coordinates.row - 1, coordinates.col + 1))
      g.toVector
    } else {
      val tmp: Vector[Vector[Coordinates]] = if (!isOponent(field, Coordinates(coordinates.row + 1, coordinates.col), Colour.black)) {
        if (ability)
          if (isOponent(field, Coordinates(coordinates.row + 2, coordinates.col), Colour.black)) removeInvalidsFromMultiVector(field, figure, Vector(Vector(goOneStepUp(coordinates))))
          else goTwoStepsUpOrOneStepUp(field, figure, coordinates)
        else removeInvalidsFromMultiVector(field, figure, Vector(Vector(goOneStepUp(coordinates))))
      } else Nil.toVector
      var g: mutable.Buffer[Vector[Coordinates]] = tmp.toBuffer
      if (isOponent(field, Coordinates(coordinates.row + 1, coordinates.col - 1), Colour.black)) g += Vector(Coordinates(coordinates.row + 1, coordinates.col - 1))
      if (isOponent(field, Coordinates(coordinates.row + 1, coordinates.col + 1), Colour.black)) g += Vector(Coordinates(coordinates.row + 1, coordinates.col + 1))
      g.toVector
    }
  }

  def kingMove(field: Field, figure: Figure, coordinates: Coordinates, ability: Boolean): Vector[Vector[Coordinates]] = kingMove(field, figure, coordinates, ability)
}

package de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.rules

import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.rules.Castling._
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Colour.Colour
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.{Colour, Coordinates, Field}
import de.htwg.se.Schach.util.StepUtils._
import de.htwg.se.Schach.util.Validation.{isOponent, removeInvalidsFromMultiVector}

object Moves {
  def bishopMove(field: Field, colour: Colour, coordinates: Coordinates): Vector[Vector[Coordinates]] = goMultiStepsDiagonal(field, colour, coordinates)

  def knightMove(field: Field, colour: Colour, coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    val twoUp = twoStepsUp(coordinates)
    val twoDown = twoStepsDown(coordinates)
    val twoLeft = twoStepsLeft(coordinates)
    val twoRight = twoStepsRight(coordinates)
    val mvl = Vector(Vector(goOneStepRight(twoUp)), Vector(goOneStepRight(twoDown)),
      Vector(goOneStepLeft(twoUp)), Vector(goOneStepLeft(twoDown)), Vector(goOneStepUp(twoRight)),
      Vector(goOneStepUp(twoLeft)), Vector(goOneStepDown(twoRight)), Vector(goOneStepDown(twoLeft)))
    removeInvalidsFromMultiVector(field, colour, mvl)
  }

  def rookMove(field: Field, colour: Colour, coordinates: Coordinates): Vector[Vector[Coordinates]] = goMultiStepsCross(field, colour, coordinates)

  def queenMove(field: Field, colour: Colour, coordinates: Coordinates): Vector[Vector[Coordinates]] =
    goMultiStepsCross(field, colour, coordinates) ++ goMultiStepsDiagonal(field, colour, coordinates)

  def pawnMove(field: Field, colour: Colour, coordinates: Coordinates, ability: Boolean): Vector[Vector[Coordinates]] = {
    def inited_fight_check = if (colour == Colour.white) {
      val tmp: Vector[Vector[Coordinates]] = if (!isOponent(field, Coordinates(coordinates.row - 1, coordinates.col), Colour.white)) {
        if (ability)
          if (isOponent(field, Coordinates(coordinates.row - 2, coordinates.col), Colour.white)) removeInvalidsFromMultiVector(field, colour, Vector(Vector(goOneStepDown(coordinates))))
          else goTwoStepsDownOrOneStepDown(field, colour, coordinates)
        else removeInvalidsFromMultiVector(field, colour, Vector(Vector(goOneStepDown(coordinates))))
      } else Nil.toVector

      check_pawn_fight(_ - _, _: Field, tmp, _: Coordinates, Colour.white)
    } else {
      val tmp: Vector[Vector[Coordinates]] = if (!isOponent(field, Coordinates(coordinates.row + 1, coordinates.col), Colour.black)) {
        if (ability)
          if (isOponent(field, Coordinates(coordinates.row + 2, coordinates.col), Colour.black)) removeInvalidsFromMultiVector(field, colour, Vector(Vector(goOneStepUp(coordinates))))
          else goTwoStepsUpOrOneStepUp(field, colour, coordinates)
        else removeInvalidsFromMultiVector(field, colour, Vector(Vector(goOneStepUp(coordinates))))
      } else Nil.toVector

      check_pawn_fight(_ + _, _: Field, tmp, _: Coordinates, Colour.black)
    }
    inited_fight_check(field, coordinates)
  }

  def check_pawn_fight(op: (Int, Int) => Int, field: Field, tmp: Vector[Vector[Coordinates]], coordinates: Coordinates, colour: Colour): Vector[Vector[Coordinates]] = {
    val firstPosition = isOponent(field, Coordinates(op(coordinates.row, 1), coordinates.col - 1), colour)
    val secondPosition = isOponent(field, Coordinates(op(coordinates.row, 1), coordinates.col + 1), colour)
    if (firstPosition && secondPosition) tmp ++ Vector(Vector(Coordinates(op(coordinates.row, 1), coordinates.col - 1)), Vector(Coordinates(op(coordinates.row, 1), coordinates.col + 1)))
    else if  (firstPosition) tmp :+ Vector(Coordinates(op(coordinates.row, 1), coordinates.col - 1))
    else if (secondPosition) tmp :+ Vector(Coordinates(op(coordinates.row, 1), coordinates.col + 1))
    else tmp
  }

  def kingMove(field: Field, colour: Colour, coordinates: Coordinates, ability: Boolean): Vector[Vector[Coordinates]] = {
    val tmp = goOneStepInAllDirections(field, colour, coordinates).toBuffer
    if (ability) {
      castleKingside(field, coordinates) match {
        case Some(coord) => tmp += Vector(coord)
        case _ =>
      }
      castleQueenside(field, coordinates) match {
        case Some(coord) => tmp += Vector(coord)
        case _ =>
      }
    }
    tmp.toVector
  }
}



package de.htwg.se.Schach.util

import de.htwg.se.Schach.model.fieldBaseImpl.Colour.Colour
import de.htwg.se.Schach.model.fieldBaseImpl.{Coordinates, Field}
import de.htwg.se.Schach.model.fieldBaseImpl.Figure

import scala.collection.mutable.ListBuffer
import de.htwg.se.Schach.util.Validation.removeInvalidsFromMultiVector


object Utils {
  def goOneStepUp(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row + 1, coordinates.col)

  def goOneStepDown(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row - 1, coordinates.col)

  def goOneStepRight(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row, coordinates.col + 1)

  def goOneStepLeft(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row, coordinates.col - 1)

  def goOneStepLeftUp(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row + 1, coordinates.col - 1)

  def goOneStepLeftDown(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row - 1, coordinates.col - 1)

  def goOneStepRightUp(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row + 1, coordinates.col + 1)

  def goOneStepRightDown(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row - 1, coordinates.col + 1)

  def twoStepsUp(coordinates: Coordinates): Coordinates = goOneStepUp(goOneStepUp(coordinates))

  def twoStepsDown(coordinates: Coordinates): Coordinates = goOneStepDown(goOneStepDown(coordinates))

  def twoStepsRight(coordinates: Coordinates): Coordinates = goOneStepRight(goOneStepRight(coordinates))

  def twoStepsLeft(coordinates: Coordinates): Coordinates = goOneStepLeft(goOneStepLeft(coordinates))

  def oneStepCross(field: Field, colour: Colour, coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(field, colour,
    Vector(Vector(goOneStepUp(coordinates)), Vector(goOneStepDown(coordinates)), Vector(goOneStepRight(coordinates)),
      Vector(goOneStepLeft(coordinates))))

  def oneStepDiagonal(field: Field, colour: Colour, coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(field, colour,
    Vector(Vector(goOneStepLeftUp(coordinates)), Vector(goOneStepLeftDown(coordinates)),
      Vector(goOneStepRightUp(coordinates)), Vector(goOneStepRightDown(coordinates))))

  def goMultiSteps(coordinates: Coordinates, fn: Coordinates => Coordinates): Vector[Coordinates] = {
    var tmp: ListBuffer[Coordinates] = ListBuffer()
    tmp += fn(coordinates)
    0 to 6 foreach { i =>
      tmp += fn(tmp(i))
    }
    tmp.toVector
  }

  def goMultiStepsLeftUp(coordinates: Coordinates): Vector[Coordinates] = goMultiSteps(coordinates, goOneStepLeftUp)

  def goMultiStepsRightUp(coordinates: Coordinates): Vector[Coordinates] = goMultiSteps(coordinates, goOneStepRightUp)

  def goMultiStepsLeftDown(coordinates: Coordinates): Vector[Coordinates] = goMultiSteps(coordinates, goOneStepLeftDown)

  def goMultiStepsRightDown(coordinates: Coordinates): Vector[Coordinates] = goMultiSteps(coordinates, goOneStepRightDown)

  def goMultiStepsRight(coordinates: Coordinates): Vector[Coordinates] = goMultiSteps(coordinates, goOneStepRight)

  def goMultiStepsLeft(coordinates: Coordinates): Vector[Coordinates] = goMultiSteps(coordinates, goOneStepLeft)

  def goMultiStepsUp(coordinates: Coordinates): Vector[Coordinates] = goMultiSteps(coordinates, goOneStepUp)

  def goMultiStepsDown(coordinates: Coordinates): Vector[Coordinates] = goMultiSteps(coordinates, goOneStepDown)

  def goOneStepInAllDirections(field: Field, colour: Colour, coordinates: Coordinates): Vector[Vector[Coordinates]] =
    removeInvalidsFromMultiVector(field, colour, oneStepCross(field, colour, coordinates) ++ oneStepDiagonal(field, colour, coordinates))

  def goMultiStepsDiagonal(field: Field, colour: Colour, coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(
    field, colour, Vector(goMultiStepsRightDown(coordinates), goMultiStepsLeftDown(coordinates), goMultiStepsRightUp(coordinates), goMultiStepsLeftUp(coordinates)))

  def goMultiStepsCross(field: Field, colour: Colour, coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(
    field, colour, Vector(goMultiStepsDown(coordinates), goMultiStepsLeft(coordinates), goMultiStepsRight(coordinates), goMultiStepsUp(coordinates)))

  def goTwoStepsUpOrOneStepUp(field: Field, colour: Colour, coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(field,
    colour, Vector(Vector(goOneStepUp(coordinates)) ++ Vector(twoStepsUp(coordinates))))

  def goTwoStepsDownOrOneStepDown(field: Field, colour: Colour, coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(field,
    colour, Vector(Vector(goOneStepDown(coordinates)) ++ Vector(twoStepsDown(coordinates))))
}

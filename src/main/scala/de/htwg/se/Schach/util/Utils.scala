package de.htwg.se.Schach.util

import de.htwg.se.Schach.model.Coordinates

object Utils {
  def validCoordinate(coordinates: Coordinates): Boolean =
    isAValidValueInsideTheField(coordinates.x) && isAValidValueInsideTheField(coordinates.y)

  def removeInvalidsFromVector(vector: Vector[Coordinates]): Vector[Coordinates] = {
    vector.zipWithIndex foreach { case (coord, index) =>
      if (!validCoordinate(coord)) {
        return vector.slice(0, index)
      }
    }
    vector
  }

  def removeInvalidsFromMultiVector(multiVector: Vector[Vector[Coordinates]]): Vector[Vector[Coordinates]] = {
    var newMultiVector: Vector[Vector[Coordinates]] = Vector()
    multiVector foreach {
      case vector =>
        val b = removeInvalidsFromVector(vector)
        if (b.size > 0) newMultiVector = newMultiVector :+ b
    }
    newMultiVector
  }

  def isAValidValueInsideTheField(value: Int): Boolean = value >= 0 && value < 8

  def goOneStepUp(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x, coordinates.y + 1)

  def goOneStepDown(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x, coordinates.y - 1)

  def goOneStepRight(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x + 1, coordinates.y)

  def goOneStepLeft(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x - 1, coordinates.y)

  def goOneStepLeftUp(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x - 1, coordinates.y + 1)

  def goOneStepLeftDown(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x - 1, coordinates.y - 1)

  def goOneStepRightUp(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x + 1, coordinates.y + 1)

  def goOneStepRightDown(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x + 1, coordinates.y - 1)

  def oneStepCross(coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(
    Vector(Vector(goOneStepUp(coordinates)), Vector(goOneStepDown(coordinates)), Vector(goOneStepRight(coordinates)),
      Vector(goOneStepLeft(coordinates))))

  def oneStepDiagonal(coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(
    Vector(Vector(goOneStepLeftUp(coordinates)), Vector(goOneStepLeftDown(coordinates)),
      Vector(goOneStepRightUp(coordinates)), Vector(goOneStepRightDown(coordinates))))

  def goOnStepInAllDirections(coordinates: Coordinates): Vector[Vector[Coordinates]] =
    oneStepCross(coordinates) ++ oneStepDiagonal(coordinates)
}

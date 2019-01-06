
package de.htwg.se.Schach.util

import de.htwg.se.Schach.model.{Coordinates, Field}

import scala.collection.mutable.ListBuffer

object Utils {
  def validCoordinate(coordinates: Coordinates): Boolean =
    isAValidValueInsideTheField(coordinates.row) && isAValidValueInsideTheField(coordinates.col)

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
      vector =>
        val b = removeInvalidsFromVector(vector)
        if (b.nonEmpty) newMultiVector = newMultiVector :+ b
    }
    newMultiVector
  }

  def isAValidValueInsideTheField(value: Int): Boolean = value >= 0 && value < 8

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

  def oneStepCross(coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(
    Vector(Vector(goOneStepUp(coordinates)), Vector(goOneStepDown(coordinates)), Vector(goOneStepRight(coordinates)),
      Vector(goOneStepLeft(coordinates))))

  def oneStepDiagonal(coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(
    Vector(Vector(goOneStepLeftUp(coordinates)), Vector(goOneStepLeftDown(coordinates)),
      Vector(goOneStepRightUp(coordinates)), Vector(goOneStepRightDown(coordinates))))

  def goOneStepInAllDirections(coordinates: Coordinates): Vector[Vector[Coordinates]] =
    oneStepCross(coordinates) ++ oneStepDiagonal(coordinates)

  def goKnightJump(coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    val twoUp = twoStepsUp(coordinates)
    val twoDown = twoStepsDown(coordinates)
    val twoLeft = twoStepsLeft(coordinates)
    val twoRight = twoStepsRight(coordinates)
    val mvl = Vector(Vector(goOneStepRight(twoUp)), Vector(goOneStepRight(twoDown)),
      Vector(goOneStepLeft(twoUp)), Vector(goOneStepLeft(twoDown)), Vector(goOneStepUp(twoRight)),
      Vector(goOneStepUp(twoLeft)), Vector(goOneStepDown(twoRight)), Vector(goOneStepDown(twoLeft)))
    removeInvalidsFromMultiVector(mvl)
  }

  def goMultiStepsLeftUp(coordinates: Coordinates): Vector[Coordinates] = {
    var tmp: ListBuffer[Coordinates] = ListBuffer()
    tmp += goOneStepLeftUp(coordinates)
    0 to 6 foreach { i =>
      tmp += goOneStepLeftUp(tmp(i))
    }
    tmp.toVector
  }

  def goMultiStepsRightUp(coordinates: Coordinates): Vector[Coordinates] = {
    var tmp: ListBuffer[Coordinates] = ListBuffer()
    tmp += goOneStepRightUp(coordinates)
    0 to 6 foreach { i =>
      tmp += goOneStepRightUp(tmp(i))
    }
    tmp.toVector
  }

  def goMultiStepsLeftDown(coordinates: Coordinates): Vector[Coordinates] = {
    var tmp: ListBuffer[Coordinates] = ListBuffer()
    tmp += goOneStepLeftDown(coordinates)
    0 to 6 foreach { i =>
      tmp += goOneStepLeftDown(tmp(i))
    }
    tmp.toVector
  }

  def goMultiStepsRightDown(coordinates: Coordinates): Vector[Coordinates] = {
    var tmp: ListBuffer[Coordinates] = ListBuffer()
    tmp += goOneStepRightDown(coordinates)
    0 to 6 foreach { i =>
      tmp += goOneStepRightDown(tmp(i))
    }
    tmp.toVector
  }

  def goMultiStepsRight(coordinates: Coordinates): Vector[Coordinates] = {
    var tmp: ListBuffer[Coordinates] = ListBuffer()
    tmp += goOneStepRight(coordinates)
    0 to 6 foreach { i =>
      tmp += goOneStepRight(tmp(i))
    }
    tmp.toVector
  }

  def goMultiStepsLeft(coordinates: Coordinates): Vector[Coordinates] = {
    var tmp: ListBuffer[Coordinates] = ListBuffer()
    tmp += goOneStepLeft(coordinates)
    0 to 6 foreach { i =>
      tmp += goOneStepLeft(tmp(i))
    }
    tmp.toVector
  }

  def goMultiStepsUp(coordinates: Coordinates): Vector[Coordinates] = {
    var tmp: ListBuffer[Coordinates] = ListBuffer()
    tmp += goOneStepUp(coordinates)
    0 to 6 foreach { i =>
      tmp += goOneStepUp(tmp(i))
    }
    tmp.toVector
  }

  def goMultiStepsDown(coordinates: Coordinates): Vector[Coordinates] = {
    var tmp: ListBuffer[Coordinates] = ListBuffer()
    tmp += goOneStepDown(coordinates)
    0 to 6 foreach { i =>
      tmp += goOneStepDown(tmp(i))
    }
    tmp.toVector
  }

  def goMultiStepsDiagonal(coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    removeInvalidsFromMultiVector(Vector(goMultiStepsRightDown(coordinates), goMultiStepsLeftDown(coordinates),
      goMultiStepsRightUp(coordinates), goMultiStepsLeftUp(coordinates)))
  }

  def goMultiStepsCross(coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    removeInvalidsFromMultiVector(Vector(goMultiStepsDown(coordinates), goMultiStepsLeft(coordinates),
      goMultiStepsRight(coordinates), goMultiStepsUp(coordinates)))
  }

  def goMultiStepsInAllDirections(coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    val listdiagonal = goMultiStepsDiagonal(coordinates)
    val listcross = goMultiStepsCross(coordinates)
    val list = listcross ++ listdiagonal

    removeInvalidsFromMultiVector(list)
  }

  def goTwoStepsUpOrOneStepUp(coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    Vector(Vector(goOneStepUp(coordinates)) ++ Vector(twoStepsUp(coordinates)))
  }

  def goTwoStepsDownOrOneStepDown(coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    Vector(Vector(goOneStepDown(coordinates)) ++ Vector(twoStepsDown(coordinates)))
  }
}

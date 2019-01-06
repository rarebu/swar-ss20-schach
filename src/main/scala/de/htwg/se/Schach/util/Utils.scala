
package de.htwg.se.Schach.util

import de.htwg.se.Schach.model.{Coordinates, Field}

//noinspection ScalaStyle
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
      Vector(goOneStepLeft(twoUp)), Vector(goOneStepLeft(twoDown)), Vector(goOneStepUp(twoRight)), Vector(goOneStepUp(twoLeft)),
      Vector(goOneStepDown(twoRight)), Vector(goOneStepDown(twoLeft)))
    //TODO:Bug if a vector was sliced here, the whole vector is invalid! Implement correct validation!
    removeInvalidsFromMultiVector(mvl)
  }

  def goMultiStepsLeftUp(coordinates: Coordinates):Vector[Coordinates] = {
    val tmp:Vector[Coordinates] = Vector()
    tmp :+ goOneStepLeftUp(coordinates)
    0 to 6 foreach { i =>
      goOneStepLeftUp(tmp(i))
    }
    tmp
  }

  def goMultiStepsRightUp(coordinates: Coordinates):Vector[Coordinates] = {
    val tmp:Vector[Coordinates] = Vector()
    tmp :+ goOneStepRightUp(coordinates)
    0 to 6 foreach { i =>
      goOneStepRightUp(tmp(i))
    }
    tmp
  }

  def goMultiStepsLeftDown(coordinates: Coordinates):Vector[Coordinates] = {
    val tmp:Vector[Coordinates] = Vector()
    tmp :+ goOneStepLeftDown(coordinates)
    0 to 6 foreach { i =>
      goOneStepLeftDown(tmp(i))
    }
    tmp
  }

  def goMultiStepsRightDown(coordinates: Coordinates):Vector[Coordinates] = {
    val tmp:Vector[Coordinates] = Vector()
    tmp :+ goOneStepRightDown(coordinates)
    0 to 6 foreach { i =>
      goOneStepRightDown(tmp(i))
    }
    tmp
  }

  def goMultiStepsRight(coordinates: Coordinates):Vector[Coordinates] = {
    val tmp:Vector[Coordinates] = Vector()
    tmp :+ goOneStepRight(coordinates)
    0 to 6 foreach { i =>
      goOneStepRight(tmp(i))
    }
    tmp
  }

  def goMultiStepsLeft(coordinates: Coordinates):Vector[Coordinates] = {
    val tmp:Vector[Coordinates] = Vector()
    tmp :+ goOneStepLeft(coordinates)
    0 to 6 foreach { i =>
      goOneStepLeft(tmp(i))
    }
    tmp
  }

  def goMultiStepsUp(coordinates: Coordinates):Vector[Coordinates] = {
    val tmp:Vector[Coordinates] = Vector()
    tmp :+ goOneStepUp(coordinates)
    0 to 6 foreach { i =>
      goOneStepUp(tmp(i))
    }
    tmp
  }

  def goMultiStepsDown(coordinates: Coordinates):Vector[Coordinates] = {
    val tmp:Vector[Coordinates] = Vector()
    tmp :+ goOneStepDown(coordinates)
    0 to 6 foreach { i =>
      goOneStepDown(tmp(i))
    }
    tmp
  }

  def goMultiStepsDiagonal(coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    removeInvalidsFromMultiVector(Vector(goMultiStepsRightDown(coordinates), goMultiStepsLeftDown(coordinates),
      goMultiStepsRightUp(coordinates), goMultiStepsLeftUp(coordinates)))
  }

  def goMultiStepsCross(coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    removeInvalidsFromMultiVector(Vector(goMultiStepsDown(coordinates), goMultiStepsLeft(coordinates), goMultiStepsRight(coordinates), goMultiStepsUp(coordinates)))
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

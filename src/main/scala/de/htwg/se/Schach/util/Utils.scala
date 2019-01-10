
package de.htwg.se.Schach.util

import de.htwg.se.Schach.model.Colour.Colour
import de.htwg.se.Schach.model.{Coordinates, Field, Figure, Rook}

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

  def removeInvalidsFromMultiVector(field: Field, figure: Figure, multiVector: Vector[Vector[Coordinates]]): Vector[Vector[Coordinates]] = {
    var newMultiVector: Vector[Vector[Coordinates]] = Vector()
    multiVector foreach {
      vector =>
        val b = cleanVec(field, removeInvalidsFromVector(vector), figure)
        if (b.nonEmpty) newMultiVector = newMultiVector :+ b
    }
    newMultiVector
  }

  def cellContainsFigure(field: Field, coordinates: Coordinates): Boolean = field.cell(coordinates.row, coordinates.col).contains.isDefined

  def cellContainsOwnFigure(field: Field, coordinates: Coordinates, figure: Figure): Boolean = field.cell(coordinates.row, coordinates.col)
    .contains.get.colour == figure.colour


  def cleanVec(field: Field, vector: Vector[Coordinates], figure: Figure): Vector[Coordinates] = {
    vector.zipWithIndex foreach {
      case (coordinate, index) =>
        if (cellContainsFigure(field, coordinate))
          if (cellContainsOwnFigure(field, coordinate, figure)) return vector.slice(0, index) else return vector.slice(0, index + 1)
    }
    vector
  }

  def isOponent(field: Field, coordinates: Coordinates, colour: Colour): Boolean = {
    if (validCoordinate(coordinates)) {
      val container = field.cell(coordinates.row, coordinates.col).contains
      if (container.isDefined)
        if (container.get.colour == colour) false else true
      else false
    } else false
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

  def oneStepCross(field: Field, figure: Figure, coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(field, figure,
    Vector(Vector(goOneStepUp(coordinates)), Vector(goOneStepDown(coordinates)), Vector(goOneStepRight(coordinates)),
      Vector(goOneStepLeft(coordinates))))

  def oneStepDiagonal(field: Field, figure: Figure, coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(field, figure,
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

  def goOneStepInAllDirections(field: Field, figure: Figure, coordinates: Coordinates): Vector[Vector[Coordinates]] =
    removeInvalidsFromMultiVector(field, figure, oneStepCross(field, figure, coordinates) ++ oneStepDiagonal(field, figure, coordinates))

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

  def kingMove(field: Field, figure: Figure, coordinates: Coordinates, ability: Boolean): Vector[Vector[Coordinates]] = {
    val tmp = goOneStepInAllDirections(field, figure, coordinates).toBuffer
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

  def goKnightJump(field: Field, figure: Figure, coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    val twoUp = twoStepsUp(coordinates)
    val twoDown = twoStepsDown(coordinates)
    val twoLeft = twoStepsLeft(coordinates)
    val twoRight = twoStepsRight(coordinates)
    val mvl = Vector(Vector(goOneStepRight(twoUp)), Vector(goOneStepRight(twoDown)),
      Vector(goOneStepLeft(twoUp)), Vector(goOneStepLeft(twoDown)), Vector(goOneStepUp(twoRight)),
      Vector(goOneStepUp(twoLeft)), Vector(goOneStepDown(twoRight)), Vector(goOneStepDown(twoLeft)))
    removeInvalidsFromMultiVector(field, figure, mvl)
  }

  def goMultiStepsDiagonal(field: Field, figure: Figure, coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(
    field, figure, Vector(goMultiStepsRightDown(coordinates), goMultiStepsLeftDown(coordinates), goMultiStepsRightUp(coordinates), goMultiStepsLeftUp(coordinates)))

  def goMultiStepsCross(field: Field, figure: Figure, coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(
    field, figure, Vector(goMultiStepsDown(coordinates), goMultiStepsLeft(coordinates), goMultiStepsRight(coordinates), goMultiStepsUp(coordinates)))

  def goMultiStepsInAllDirections(field: Field, figure: Figure, coordinates: Coordinates): Vector[Vector[Coordinates]] =
    goMultiStepsCross(field, figure, coordinates) ++ goMultiStepsDiagonal(field, figure, coordinates)

  def goTwoStepsUpOrOneStepUp(field: Field, figure: Figure, coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(field, figure,
    Vector(Vector(goOneStepUp(coordinates)) ++ Vector(twoStepsUp(coordinates))))

  def goTwoStepsDownOrOneStepDown(field: Field, figure: Figure, coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(field, figure,
    Vector(Vector(goOneStepDown(coordinates)) ++ Vector(twoStepsDown(coordinates))))
}

//TODO: refactor
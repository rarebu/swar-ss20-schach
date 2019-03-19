package de.htwg.se.Schach.util

import Colour.Colour
import de.htwg.se.Schach.model.Field.Field

object Validation {
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

  def removeInvalidsFromMultiVector(field: Field, colour: Colour, multiVector: Vector[Vector[Coordinates]]): Vector[Vector[Coordinates]] = {
    var newMultiVector: Vector[Vector[Coordinates]] = Vector()
    multiVector foreach {
      vector =>
        val b = cleanVec(field, removeInvalidsFromVector(vector), colour)
        if (b.nonEmpty) newMultiVector = newMultiVector :+ b
    }
    newMultiVector
  }


  def cellContainsFigure(field: Field, coordinates: Coordinates): Boolean = field.cell(coordinates.row, coordinates.col).contains.isDefined

  def cellContainsOwnFigure(field: Field, coordinates: Coordinates, colour: Colour): Boolean = field.cell(coordinates.row, coordinates.col)
    .contains.get.colour == colour


  def cleanVec(field: Field, vector: Vector[Coordinates], colour: Colour): Vector[Coordinates] = {
    vector.zipWithIndex foreach {
      case (coordinate, index) =>
        if (cellContainsFigure(field, coordinate))
          if (cellContainsOwnFigure(field, coordinate, colour)) return vector.slice(0, index) else return vector.slice(0, index + 1)
    }
    vector
  }

  def isAValidValueInsideTheField(value: Int): Boolean = value >= 0 && value < 8

  def isOponent(field: Field, coordinates: Coordinates, colour: Colour): Boolean = {
    validCoordinate(coordinates) && {
      val container = field.cell(coordinates.row, coordinates.col).contains
      (container.isDefined && container.get.colour != colour)
    }
  }
}
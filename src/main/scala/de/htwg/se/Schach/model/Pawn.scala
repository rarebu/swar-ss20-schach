package de.htwg.se.Schach.model

import _root_.de.htwg.se.Schach.model.Colour.Colour
import _root_.de.htwg.se.Schach.util.Utils._
import de.htwg.se.Schach.model.Figure._

import scala.collection.mutable

private[model] case class Pawn(colour: Colour, coordinates: Coordinates, ability: Boolean) extends Figure {
  def this(colour: Colour, col: Int) = this(colour, {
    if (colour == Colour.black) Coordinates(ROW_BLACK_PAWN, col) else Coordinates(ROW_WHITE_PAWN, col)
  }, true)

  override def getName: String = "Pawn"

  def isOponent(field: Field, coordinates: Coordinates, colour: Colour): Boolean = {
    if (validCoordinate(coordinates)) {
      val container = field.cell(coordinates.row, coordinates.col).contains
      if (container.isDefined)
        if (container.get.colour == colour) false else true
      else false
    } else false
  }

  override def getPossibleNewPositions(field: Field, coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    if (colour == Colour.white) {
      val tmp: Vector[Vector[Coordinates]] = if (!isOponent(field, Coordinates(coordinates.row - 1, coordinates.col), Colour.white)) {
        if (ability)
          if (isOponent(field, Coordinates(coordinates.row - 2, coordinates.col), Colour.white)) removeInvalidsFromMultiVector(field, this, Vector(Vector(goOneStepDown(coordinates))))
          else goTwoStepsDownOrOneStepDown(field, this, coordinates)
        else removeInvalidsFromMultiVector(field, this, Vector(Vector(goOneStepDown(coordinates))))
      } else Nil.toVector
      var g: mutable.Buffer[Vector[Coordinates]] = tmp.toBuffer
      if (isOponent(field, Coordinates(coordinates.row - 1, coordinates.col - 1), Colour.white)) g += Vector(Coordinates(coordinates.row - 1, coordinates.col - 1))
      if (isOponent(field, Coordinates(coordinates.row - 1, coordinates.col + 1), Colour.white)) g += Vector(Coordinates(coordinates.row - 1, coordinates.col + 1))
      g.toVector
    } else {
      val tmp: Vector[Vector[Coordinates]] = if (!isOponent(field, Coordinates(coordinates.row + 1, coordinates.col), Colour.black)) {
        if (ability)
          if (isOponent(field, Coordinates(coordinates.row + 2, coordinates.col), Colour.black)) removeInvalidsFromMultiVector(field, this, Vector(Vector(goOneStepUp(coordinates))))
          else goTwoStepsUpOrOneStepUp(field, this, coordinates)
        else removeInvalidsFromMultiVector(field, this, Vector(Vector(goOneStepUp(coordinates))))
      } else Nil.toVector
      var g: mutable.Buffer[Vector[Coordinates]] = tmp.toBuffer
      if (isOponent(field, Coordinates(coordinates.row + 1, coordinates.col - 1), Colour.black)) g += Vector(Coordinates(coordinates.row + 1, coordinates.col - 1))
      if (isOponent(field, Coordinates(coordinates.row + 1, coordinates.col + 1), Colour.black)) g += Vector(Coordinates(coordinates.row + 1, coordinates.col + 1))
      g.toVector
    }
  }

  override def toString: String = if (colour == Colour.black) "♟" else "♙"

  override def move(coordinates: Coordinates): Figure = Pawn(this.colour, coordinates, ability = false)
}
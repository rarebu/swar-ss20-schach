package de.htwg.se.Schach.model

import _root_.de.htwg.se.Schach.model.Colour.Colour

trait Figure {
  val colour: Colour
  val coordinates: Coordinates
  var hasAbility: Boolean

  def getName: String

  def getPossibleNewPositions(): Vector[Vector[Coordinates]]

}

object Figure {
  val ROW_WHITE = 7
  val ROW_BLACK = 0
}
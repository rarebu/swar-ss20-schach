package de.htwg.se.Schach.model

trait FieldInterface {
  def cellIsBlack(row: Int, col: Int): Boolean

  def cellContains(row: Int, col: Int): Option[String]

  def getFigures: Option[String]

  def move(row: Int, col: Int, newRow: Int, newCol: Int): Option[FieldInterface]

  def unMove(row: Int, col: Int, newRow: Int, newCol: Int): FieldInterface

  def changePawn(input: String): Option[FieldInterface]

  def undoChangePawn(input: String): Option[FieldInterface]

  def CHANGEABLE_BLACK_FIGURES: String

  def CHANGEABLE_WHITE_FIGURES: String
}

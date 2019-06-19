package de.htwg.se.Schach.model

trait FieldInterface {
//  def cell(row: Int, col: Int): FieldInterface
  def cellIsBlack(row: Int, col: Int): Boolean
  def cellContains(row: Int, col: Int): Option[String]
  def getFigures: Option[String]
  def move(row: Int, col: Int, newRow: Int, newCol: Int): Option[FieldInterface]
  def unMove(row: Int, col: Int, newRow: Int, newCol: Int): FieldInterface
  def changePawn(input: String): Option[FieldInterface]
  def undoChangePawn(input: String): Option[FieldInterface]
  def CHANGABLE_BLACK_FIGURES: String
  def CHANGABLE_WHITE_FIGURES: String
}

trait FigureInterface {
  def CHANGABLE_BLACK_FIGURES
  def CHANGABLE_WHITE_FIGURES
}

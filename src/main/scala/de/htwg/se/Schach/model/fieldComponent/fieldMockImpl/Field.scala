package de.htwg.se.Schach.model.fieldComponent.fieldMockImpl

import de.htwg.se.Schach.model.FieldInterface

class Field extends FieldInterface {
  override def cellIsBlack(row: Int, col: Int): Boolean = ???

  override def cellContains(row: Int, col: Int): Option[String] = ???

  override def getFigures: Option[String] = ???

  override def move(row: Int, col: Int, newRow: Int, newCol: Int): Option[FieldInterface] = ???

  override def unMove(row: Int, col: Int, newRow: Int, newCol: Int): FieldInterface = ???

  override def changePawn(input: String): Option[FieldInterface] = ???

  override def undoChangePawn(input: String): Option[FieldInterface] = ???

  override def CHANGABLE_BLACK_FIGURES: String = ???

  override def CHANGABLE_WHITE_FIGURES: String = ???
}

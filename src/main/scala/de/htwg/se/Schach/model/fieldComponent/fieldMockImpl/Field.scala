package de.htwg.se.Schach.model.fieldComponent.fieldMockImpl

import de.htwg.se.Schach.model.{FieldDataInterface, FieldInterface}
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field
import play.api.libs.json.JsValue

class Field extends FieldInterface {
  override def cellIsBlack(row: Int, col: Int): Boolean = ???

  override def cellContains(row: Int, col: Int): Option[String] = ???

  override def getFigures: Option[String] = ???

  override def move(row: Int, col: Int, newRow: Int, newCol: Int): Option[FieldInterface] = ???

  override def unMove(row: Int, col: Int, newRow: Int, newCol: Int): FieldInterface = ???

  override def changePawn(input: String): Option[FieldInterface] = ???

  override def undoChangePawn(input: String): Option[FieldInterface] = ???

  override def CHANGEABLE_BLACK_FIGURES: String = ???

  override def CHANGEABLE_WHITE_FIGURES: String = ???

  override def getSize: Int = Field.SIZE

  override def getRoundCount: Int = ???

  override def getToChange: String = ???

  override def getField: FieldDataInterface = ???

  override def toJson: JsValue = ???

  override def cellContentIsBlack(row: Int, col: Int): Option[Boolean] = ???
}

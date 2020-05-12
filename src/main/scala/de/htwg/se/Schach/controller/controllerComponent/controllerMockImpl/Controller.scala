package de.htwg.se.Schach.controller.controllerComponent.controllerMockImpl

import de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl.ControllerInterface
import de.htwg.se.Schach.model.FieldInterface
import de.htwg.se.Schach.model.fieldComponent.fieldMockImpl.Field
import play.api.libs.json.JsValue

import scala.util.Try

class Controller(var field: FieldInterface) extends ControllerInterface {

  field = new Field

  override def newField: Unit = ???

  override def undo: Unit = ???

  override def redo: Unit = ???

  override def pawnPromoting: Option[String] = ???

  override def choose(representation: String): Try[ControllerInterface] = ???

  override def move(row: Int, col: Int, newRow: Int, newCol: Int): Try[ControllerInterface] = ???

  override def statusText: String = ???

  override def getChangeableFigures: String = ???

  override def fieldToString: String = ???

  override def cellContains(row: Int, col: Int): String = ???

  override def cellIsBlack(row: Int, col: Int): Boolean = ???

  override def save: Unit = ???

  override def load: Unit = ???

  override def toJson: JsValue = ???
}

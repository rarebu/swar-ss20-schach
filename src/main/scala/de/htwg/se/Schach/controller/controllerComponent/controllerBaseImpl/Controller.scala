package de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl

import GameStatus._
import de.htwg.se.Schach.model.FieldInterface
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.Schach.model.fileIoComponent.fileIoJsonImpl.FileIOJson
import de.htwg.se.Schach.util.UndoManager
import play.api.libs.json.JsValue

import scala.swing.Publisher
import scala.util.Try

class Controller(var field: FieldInterface) extends ControllerInterface with Publisher {
  var gameStatus: GameStatus = GameStatus.IDLE
  private val undoManager = new UndoManager

  override def newField: Unit = {
    field = new Field()
    publish(new CellChanged)
  }

  override def move(row: Int, col: Int, newRow: Int, newCol: Int): Try[ControllerInterface] = {//what to return, controller:
    val x = undoManager.doStep(new MoveCommand(row, col, newRow, newCol, this)) //if invalid step returns error
    if (x.isSuccess) {
      gameStatus = MOVE
      publish(new CellChanged)
    }
    x
  }

  override def choose(representation: String): Try[ControllerInterface] = {
    val x = undoManager.doStep(new ChooseCommand(representation, this)) //if invalid character returns error
    if (x.isSuccess) {
      gameStatus = CHOOSE
      publish(new CellChanged)
    }
    x
  }

  override def getChangeableFigures: String = field.CHANGEABLE_BLACK_FIGURES + field.CHANGEABLE_WHITE_FIGURES

  override def fieldToString: String = field.toString

  override def undo: Unit = {
    undoManager.undoStep
    gameStatus = UNDO
    publish(new CellChanged)
  }

  override def redo: Unit = {
    undoManager.redoStep
    gameStatus = REDO
    publish(new CellChanged)
  }

  override def statusText: String = GameStatus.message(gameStatus)

  override def cellIsBlack(row: Int, col: Int): Boolean = field.cellIsBlack(row, col)

  override def cellContains(row: Int, col: Int): String = {
    var tmp = field.cellContains(row, col)
    if ( tmp.isDefined) tmp.get.toString else ""
  }

  override def pawnPromoting: Option[String] = field.getFigures

  override def save: Unit = {
    val fileIo = new FileIOJson
    fileIo.save(field)
    gameStatus = SAVED
    publish(new CellChanged)
  }

  override def load: Unit = {
    val fileIo = new FileIOJson
    field = fileIo.load
    gameStatus = LOADED
    publish(new CellChanged)
  }

  override def toJson: JsValue = field.toJson
}
package de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl

import com.google.inject.Guice
import GameStatus._
import de.htwg.se.Schach.controller.SafeConfigInjection
import de.htwg.se.Schach.controller.controllerComponent.{CellChanged, LogicControllerInterface}
import de.htwg.se.Schach.model.FieldInterface
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.Schach.model.fileIoComponent.FileIO
import de.htwg.se.Schach.util.UndoManager
import play.api.libs.json.JsValue

import scala.swing.Publisher

class LogicController(var field: FieldInterface) extends LogicControllerInterface with Publisher {
  var gameStatus: GameStatus = GameStatus.IDLE
  val injector = Guice.createInjector(new SafeConfigInjection)
  val fileIo = injector.getInstance(classOf[FileIO])
  private val undoManager = new UndoManager

  override def newField: Unit = {
    field = new Field()
    publish(new CellChanged)
  }

  override def move(row: Int, col: Int, newRow: Int, newCol: Int): Boolean = {
    val tmp = undoManager.doStep(new MoveCommand(row, col, newRow, newCol, this))
    if(tmp) {
      gameStatus = MOVE
      publish(new CellChanged)
    }
    tmp
  }

  override def choose(representation: String): Boolean = {
    val tmp = undoManager.doStep(new ChooseCommand(representation, this))
    if(tmp) {
      gameStatus = CHOOSE
      publish(new CellChanged)
    }
    tmp
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
    val tmp = field.cellContains(row, col)
    if ( tmp.isDefined) tmp.get else ""
  }

  override def cellContentBlack(row: Int, col: Int): Option[Boolean] = field.cellContentIsBlack(row, col)

  override def pawnPromoting: Option[String] = field.getFigures

  override def save: Unit = {
    fileIo.save(field)
    gameStatus = SAVED
    publish(new CellChanged)
  }

  override def load: Unit = {
    field = fileIo.load
    gameStatus = LOADED
    publish(new CellChanged)
  }

  override def toJson: JsValue = field.toJson
}
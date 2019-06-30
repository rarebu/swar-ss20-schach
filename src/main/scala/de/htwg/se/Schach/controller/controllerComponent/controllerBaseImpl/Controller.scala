package de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl

import GameStatus._
import de.htwg.se.Schach.model.FieldInterface
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.Schach.model.fileIoComponent.fileIoXmlImpl.FileIOXml
import de.htwg.se.Schach.util.UndoManager

import scala.swing.Publisher

class Controller(var field: FieldInterface) extends ControllerInterface with Publisher {
  var gameStatus: GameStatus = GameStatus.IDLE
  private val undoManager = new UndoManager

  override def newField: Unit = {
    field = new Field()
    publish(new CellChanged)
  }

  override def move(row: Int, col: Int, newRow: Int, newCol: Int): Unit = {
    undoManager.doStep(new MoveCommand(row, col, newRow, newCol, this))
    gameStatus = MOVE
    publish(new CellChanged)
  }

  override def choose(representation: String): Unit = {
    undoManager.doStep(new ChooseCommand(representation, this))
    gameStatus = CHOOSE
    publish(new CellChanged)
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

//  def cell(row: Int, col: Int) = field.cell(row, col)

  override def cellIsBlack(row: Int, col: Int): Boolean = field.cellIsBlack(row, col)

  override def cellContains(row: Int, col: Int): String = {
    var tmp = field.cellContains(row, col)
    if ( tmp.isDefined) tmp.get.toString else ""
  }

  override def pawnPromoting: Option[String] = field.getFigures

  override def save: Unit = {
    val fileIo = new FileIOXml(field)
    fileIo.save(field)
    gameStatus = SAVED
    publish(new CellChanged)
  }

  override def load: Unit = {
    val fileIo = new FileIOXml(field)
    field = fileIo.load
    gameStatus = LOADED
    publish(new CellChanged)
  }
}
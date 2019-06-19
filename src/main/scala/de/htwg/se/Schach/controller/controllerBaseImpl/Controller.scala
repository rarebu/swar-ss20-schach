package de.htwg.se.Schach.controller.controllerBaseImpl

import de.htwg.se.Schach.controller.{CellChanged, ControllerInterface, GameStatus}
import de.htwg.se.Schach.controller.GameStatus._
import de.htwg.se.Schach.model.FieldInterface
import de.htwg.se.Schach.model.fieldBaseImpl.Field
//import de.htwg.se.Schach.model.fieldBaseImpl.{Colour, Field}
//import de.htwg.se.Schach.model.{Cell, Figure}
import de.htwg.se.Schach.util.UndoManager

import scala.swing.Publisher

class Controller(var field: FieldInterface) extends ControllerInterface with Publisher {
  var gameStatus: GameStatus = GameStatus.IDLE
  private val undoManager = new UndoManager

  def newField: Unit = {
    field = new Field()
    publish(new CellChanged)
  }

  def move(row: Int, col: Int, newRow: Int, newCol: Int): Unit = {
    undoManager.doStep(new MoveCommand(row, col, newRow, newCol, this))
    gameStatus = MOVE
    publish(new CellChanged)
  }

  def choose(representation: String): Unit = {
    undoManager.doStep(new ChooseCommand(representation, this))
    gameStatus = CHOOSE
    publish(new CellChanged)
  }

  def getChangableFigures: String = field.CHANGABLE_BLACK_FIGURES + field.CHANGABLE_WHITE_FIGURES

  def fieldToString: String = field.toString

  def undo: Unit = {
    undoManager.undoStep
    gameStatus = UNDO
    publish(new CellChanged)
  }

  def redo: Unit = {
    undoManager.redoStep
    gameStatus = REDO
    publish(new CellChanged)
  }

  def statusText: String = GameStatus.message(gameStatus)

//  def cell(row: Int, col: Int) = field.cell(row, col)

  def cellIsBlack(row: Int, col: Int): Boolean = field.cellIsBlack(row, col)

  def cellContains(row: Int, col: Int): String = {
    var tmp = field.cellContains(row, col)
    if ( tmp.isDefined) tmp.get.toString else ""
  }

  def pawnPromoting: Option[String] = field.getFigures
}
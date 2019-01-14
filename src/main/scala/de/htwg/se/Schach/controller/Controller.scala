package de.htwg.se.Schach.controller

import de.htwg.se.Schach.controller.GameStatus.GameStatus
import de.htwg.se.Schach.model.{Cell, Colour, Field, Figure}
import de.htwg.se.Schach.util.{Command, Observable, UndoManager}

import scala.swing.Publisher

class Controller(var field: Field) extends Publisher {
  var gameStatus: GameStatus = GameStatus.IDLE
  private val undoManager = new UndoManager

  def newField(): Unit = {
    field = new Field()
    publish(new CellChanged)
  }

  def move(row: Int, col: Int, newRow: Int, newCol: Int): Unit = {
    undoManager.doStep(new MoveCommand(row, col, newRow, newCol, this))
    publish(new CellChanged)
    if (pawnPromoting.isDefined) publish(new ChooseFigure)

  }

  def choose(representation: String): Unit = {
    undoManager.doStep(new ChooseCommand(representation, this))
    publish(new CellChanged)
  }

  def getChangableFigures: String = Figure.CHANGABLE_BLACK_FIGURES + Figure.CHANGABLE_WHITE_FIGURES

  def fieldToString: String = field.toString

  def undo(): Unit = {
    undoManager.undoStep
    publish(new CellChanged)
  }

  def redo(): Unit = {
    undoManager.redoStep
    publish(new CellChanged)
  }

  def cell(row: Int, col: Int) = field.cell(row, col)

  def cellIsBlack(row: Int, col: Int): Boolean = {
    if (cell(row, col).colour == Colour.black) true else false
  }

  def cellContains(row: Int, col: Int): String = {
    if (cell(row, col).contains.isDefined) cell(row, col).contains.get.toString else ""
  }

  def pawnPromoting: Option[String] = field.getFigures
}
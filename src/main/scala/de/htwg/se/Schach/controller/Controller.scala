package de.htwg.se.Schach.controller

import de.htwg.se.Schach.controller.GameStatus.GameStatus
import de.htwg.se.Schach.model.Field
import de.htwg.se.Schach.model.Figure
import de.htwg.se.Schach.util.{Command, Observable, UndoManager}

class Controller(var field: Field) extends Observable {
  var gameStatus: GameStatus = GameStatus.IDLE
  private val undoManager = new UndoManager

  def newField(): Unit = {
    field = new Field()
    notifyObservers()
  }

  def move(row: Int, col: Int, newRow: Int, newCol: Int): Unit = {
    undoManager.doStep(new MoveCommand(row, col, newRow, newCol, this))
    notifyObservers()
  }

  def choose(representation: String): Unit = {
    undoManager.doStep(new ChooseCommand(representation, this))
    notifyObservers()
  }

  def getChangableFigures: String = Figure.CHANGABLE_BLACK_FIGURES + Figure.CHANGABLE_WHITE_FIGURES

  def fieldToString: String = field.toString

  def undo: Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep
    notifyObservers
  }

}
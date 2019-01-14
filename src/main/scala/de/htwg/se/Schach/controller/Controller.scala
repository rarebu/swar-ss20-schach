package de.htwg.se.Schach.controller

import de.htwg.se.Schach.model.Field
import de.htwg.se.Schach.model.Figure
import de.htwg.se.Schach.util.{Command, Observable, UndoManager}

class Controller(var field: Field) extends Observable {

  private val undoManager = new UndoManager

  def newField(): Unit = {
    field = new Field()
    notifyObservers()
  }

  def processInput(row: Int, col: Int, newRow: Int, newCol: Int): Unit = {
    undoManager.doStep(new MoveCommand(row, col, newRow, newCol))
    notifyObservers()
  }

  def choose(representation: String): Unit = {
    undoManager.doStep(new ChooseCommand(representation))
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

  class MoveCommand(row: Int, col: Int, newRow: Int, newCol: Int) extends Command {
    override def doStep: Boolean = {
      val tmp = field.processInput("" + row + col + newRow + newCol, false)
      if (tmp.isDefined) {
        field = tmp.get
        true
      } else false
    }

    override def undoStep: Unit = field = field.processInput("" + row + col + newRow + newCol, true).get

    override def redoStep: Unit = field = field.processInput("" + row + col + newRow + newCol, false).get
  }

  class ChooseCommand(input: String) extends Command {
    override def doStep: Boolean = {
      val tmp = field.processInput(input, false)
      if (tmp.isDefined) {
        field = tmp.get
        true
      } else false
    }

    override def undoStep: Unit = field = field.processInput(input, true).get

    override def redoStep: Unit = field = field.processInput(input, false).get
  }

}
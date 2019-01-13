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

  def processInput(input: String): Unit = {
    undoManager.doStep(new SetCommand(input))
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

  class SetCommand(input: String) extends Command {
    override def doStep: Unit = field = field.processInput(input, false)

    override def undoStep: Unit = field = field.processInput(input, true)

    override def redoStep: Unit = field = field.processInput(input, false)
  }

}
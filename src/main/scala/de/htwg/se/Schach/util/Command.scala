package de.htwg.se.Schach.util

trait Command {

  def doStep: Boolean

  def undoStep: Unit

  def redoStep: Unit

}

class UndoManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def doStep(command: Command) = {
    val v = command
    if (command.doStep) {
      redoStack = Nil
      undoStack = command :: undoStack
    }
  }

  def undoStep = {
    undoStack match {
      case Nil =>
      case head :: stack => {
        head.undoStep
        undoStack = stack
        redoStack = head :: redoStack
      }
    }
  }

  def redoStep = {
    redoStack match {
      case Nil =>
      case head :: stack => {
        head.redoStep
        redoStack = stack
        undoStack = head :: undoStack
      }
    }
  }
}

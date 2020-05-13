package de.htwg.se.Schach.util

import de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl.ControllerInterface

import scala.util.{Failure, Try}

trait Command {

  def doStep: Try[ControllerInterface]

  def undoStep: Unit

  def redoStep: Unit

}

class UndoManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def doStep(command: Command) = {
    val x = command.doStep
    if (x.isSuccess) {
      redoStack = Nil
      undoStack = command :: undoStack
    }
    x
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

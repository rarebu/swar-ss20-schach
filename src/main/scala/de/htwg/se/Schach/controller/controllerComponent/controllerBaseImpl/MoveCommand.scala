package de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.Schach.util.Command

import scala.util.{Failure, Success, Try}

class MoveCommand(row: Int, col: Int, newRow: Int, newCol: Int, controller: Controller) extends Command {
  override def doStep: Try[ControllerInterface] = {
    val tmp = controller.field.move(row, col, newRow, newCol)
    if (tmp.isDefined) {
      controller.field = tmp.get
      Success(controller)
    } else {
      Failure(new Exception)
    }
  }

  override def undoStep: Unit = controller.field = controller.field.unMove(newRow, newCol, row, col)

  override def redoStep: Unit = controller.field = controller.field.move(row, col, newRow, newCol).get
}

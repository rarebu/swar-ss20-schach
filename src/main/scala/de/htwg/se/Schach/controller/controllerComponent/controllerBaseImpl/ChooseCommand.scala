package de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.Schach.util.Command

import scala.util.{Failure, Success, Try}

class ChooseCommand(input: String, controller: Controller) extends Command {
  override def doStep: Try[ControllerInterface] = {
    val tmp = controller.field.changePawn(input)
    if (tmp.isDefined) {
      controller.field = tmp.get
      Success(controller)
    } else Failure(new Exception)
  }

  override def undoStep: Unit = controller.field = controller.field.undoChangePawn(input).get

  override def redoStep: Unit = controller.field = controller.field.changePawn(input).get
}

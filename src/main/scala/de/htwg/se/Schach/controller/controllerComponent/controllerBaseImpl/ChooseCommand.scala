package de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.Schach.util.Command

class ChooseCommand(input: String, controller: LogicController) extends Command {
  override def doStep: Boolean = {
    val tmp = controller.field.changePawn(input)
    if (tmp.isDefined) {
      controller.field = tmp.get
      true
    } else false
  }

  override def undoStep: Unit = controller.field = controller.field.undoChangePawn(input).get

  override def redoStep: Unit = controller.field = controller.field.changePawn(input).get
}

package de.htwg.se.schach.controller

import de.htwg.se.schach.util.Command

class MoveCommand(row: Int, col: Int, newRow: Int, newCol: Int, controller: Controller) extends Command {
  override def doStep: Boolean = {
    val tmp = controller.field.move(row, col, newRow, newCol)
    if (tmp.isDefined) {
      controller.field = tmp.get
      true
    } else false
  }

  override def undoStep: Unit = controller.field = controller.field.unMove(newRow, newCol, row, col)

  override def redoStep: Unit = controller.field = controller.field.move(row, col, newRow, newCol).get
}

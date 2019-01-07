package de.htwg.se.Schach.aview

import de.htwg.se.Schach.controller.Controller
import de.htwg.se.Schach.model.Field
import de.htwg.se.Schach.util.Observer

class TUI(controller: Controller) extends Observer {
  controller.add(this)

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "n" => controller.newField()
      case _ => input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
        case row :: column :: newRow :: newColumn :: Nil => controller.move(row, column, newRow, newColumn)
        case _ =>
      }
    }
  }

  override def update(): Unit = println(controller.fieldToString)
}

package de.htwg.se.Schach.aview

import de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl.{CellChanged, Controller, ControllerInterface}

import scala.swing.Reactor

class TUI(controller: ControllerInterface) extends Reactor {
  listenTo(controller)

  def processInputLine(input: String): Unit = {
    val pattern = {
      "[" + controller.getChangeableFigures + "]"
    }.r
    input match {
      case "q" =>
      case "n" => controller.newField
      case "z" => controller.undo
      case "y" => controller.redo
      case "f" => controller.save
      case "l" => controller.load
      case _ => input.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
        case row :: column :: newRow :: newColumn :: Nil => controller.move(row, column, newRow, newColumn)
        case _ => {
          pattern.findFirstIn(input) match {
            case Some(c) => controller.choose(c)
            case _ => println("Wrong input!")
          }
        }
      }
    }
  }

  reactions += {
    case event: CellChanged => printTui
  }

  def printTui: Unit = {
    println(controller.statusText)
    println(controller.fieldToString);
  }
}
package de.htwg.se.Schach.aview

import de.htwg.se.Schach.controller.{Controller, GameStatus}
import de.htwg.se.Schach.util.Observer

class TUI(controller: Controller) extends Observer {
  controller.add(this)

  def processInputLine(input: String): Unit = {
    val pattern = {
      "[" + controller.getChangableFigures + "]"
    }.r
    input match {
      case "q" =>
      case "n" => controller.newField()
      case "z" => controller.undo
      case "y" => controller.redo
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

  override def update(): Boolean = {
    println(GameStatus.message(controller.gameStatus))
    controller.gameStatus = GameStatus.IDLE
    println(controller.fieldToString);
    true
  }
}

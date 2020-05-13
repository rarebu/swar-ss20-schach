package de.htwg.se.Schach.aview

import de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl.{CellChanged, ControllerInterface}

import scala.swing.Reactor
import scala.util.{Failure, Success, Try}

class TUI(controller: Try[ControllerInterface]) extends Reactor {
  controller match {
    case Failure(exception) => Failure(exception)
    case Success(controller) => listenTo(controller)
  }

  def processInputLine(input: String): Unit = {
    controller match {
      case Success(controller) => {
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
            case row :: column :: newRow :: newColumn :: Nil => {
              controller.move(row, column, newRow, newColumn) match {
                case Failure(_) => println("Wrong input!")
                case _ => println("Good input")
              }
            } //potential error
            case _ => {
              pattern.findFirstIn(input) match {
                case Some(c) => {
                  controller.choose(c) match {
                    case Failure(_) => println("Can't choose this figure!")
                    case _ => println("Choosed this Figure")
                  }
                } //potential error
                case _ => println("Wrong input!")
              }
            }
          }
        }
      }
      case _ => Failure(new Exception)
    }

  }

  reactions += {
    case event: CellChanged => printTui
  }

  def printTui: Unit = {
    controller match {
      case Failure(exception) => Failure(exception)
      case Success(controller) => {
        println(controller.statusText)
        println(controller.fieldToString)
      }
    }

  }
}
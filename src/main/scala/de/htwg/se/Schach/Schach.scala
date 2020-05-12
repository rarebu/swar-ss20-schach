package de.htwg.se.Schach

import _root_.de.htwg.se.Schach.aview.TUI
import _root_.de.htwg.se.Schach.aview.gui.SwingGui
import de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl.{CellChanged, Controller}
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field

import scala.io.StdIn.readLine
import scala.util.Success

object Schach {
  val controller = new Controller(new Field())
  val tui = new TUI(Success(controller))
  val gui = new SwingGui(controller)
  controller.publish(new CellChanged)

  def main(args: Array[String]): Unit = {
    var input: String = ""
    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}

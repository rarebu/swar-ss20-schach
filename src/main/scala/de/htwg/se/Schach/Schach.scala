package de.htwg.se.Schach

import _root_.de.htwg.se.Schach.aview.TUI
import _root_.de.htwg.se.Schach.aview.gui.SwingGui
import de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl.{CellChanged, Controller}
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field

import scala.io.StdIn.readLine

object Schach {
  val controller = new Controller(new Field())
  val tui = new TUI(controller)
//  val gui = new SwingGui(controller)
  controller.publish(new CellChanged)

  def main(args: Array[String]): Unit = {
    //    if (args.size > 0) {
    //      var input: String = args(0)
    //      if (!input.isEmpty) tui.processInputLine(input)
    //    }
    var input: String = "" //args(0)
    //    if (!input.isEmpty) tui.processInputLine(input)
    //    else
    do {
      input = readLine()
      if(input!= null) tui.processInputLine(input)
    } while (input != "q")
  }
}

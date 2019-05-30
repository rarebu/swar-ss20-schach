package de.htwg.se.schach

import _root_.de.htwg.se.schach.controller.{CellChanged, Controller}
import _root_.de.htwg.se.schach.model.Field
import _root_.de.htwg.se.schach.aview.TUI
import _root_.de.htwg.se.schach.aview.gui.SwingGui

import scala.io.StdIn.readLine

object Schach {
  val controller = new Controller(new Field())
  val tui = new TUI(controller)
  val gui = new SwingGui(controller)
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
      tui.processInputLine(input)
    } while (input != "q")
  }
}

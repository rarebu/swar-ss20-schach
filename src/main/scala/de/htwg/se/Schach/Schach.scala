package de.htwg.se.Schach

import de.htwg.se.Schach.controller.Controller
import de.htwg.se.Schach.model.{Field, Player}
import de.htwg.se.Schach.aview.TUI
import de.htwg.se.Schach.aview.gui.SwingGui

import scala.io.StdIn.readLine

private[Schach] object Schach {
  def main(args: Array[String]): Unit = {
    val controller = new Controller(new Field())
    val tui = new TUI(controller)
    val gui = new SwingGui(controller)
    controller.notifyObservers()
    var input: String = ""
    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}

package de.htwg.se.Schach

import _root_.de.htwg.se.Schach.aview.TUI
import _root_.de.htwg.se.Schach.aview.gui.SwingGui
import de.htwg.se.Schach.aview.HttpServer.SchachLogicHttpServer
import de.htwg.se.Schach.controller.controllerComponent.CellChanged
import de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl.LogicController
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field

import scala.io.StdIn.readLine

object Schach {
  val controller = new LogicController(new Field())
  val tui = new TUI(controller)
//  val gui = new SwingGui(controller)
  val server = new SchachLogicHttpServer(controller)
  var shutdown = false
  controller.publish(new CellChanged)

  def main(args: Array[String]): Unit = {
    var input: String = ""
    do {
      input = readLine()
      tui.processInputLine(input)
    } while (!shutdown)
  }

  def shutdownServer():Unit = shutdown = true
}

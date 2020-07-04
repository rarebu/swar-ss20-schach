package de.htwg.se.Schach

import _root_.de.htwg.se.Schach.aview.TUI
import _root_.de.htwg.se.Schach.aview.gui.SwingGui
import de.htwg.se.Schach.aview.HttpServer.SchachLogicHttpServer
import de.htwg.se.Schach.controller.controllerComponent.CellChanged
import de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl.LogicController
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field


object Schach {
  val controller = new LogicController(new Field())
//  val tui = new TUI(controller)
//  val gui = new SwingGui(controller)
  val server = new SchachLogicHttpServer(controller)
  @volatile var shutdown = false
  controller.publish(new CellChanged)

  def main(args: Array[String]): Unit = {
    while ( {
      !shutdown
    }) Thread.sleep(1000)
    server.shutdownWebServer
  }

  def shutdownServer():Unit = shutdown = true
}

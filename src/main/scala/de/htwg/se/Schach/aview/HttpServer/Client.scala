package de.htwg.se.Schach.aview.HttpServer

import de.htwg.se.Schach.util.Observer

case class Client(uri: String) extends Observer {
  override def update(): Boolean = {
    false
  }
}

package de.htwg.se.Schach

import de.htwg.se.Schach.model.{Field, Player}
import de.htwg.se.Schach.aview.TUI

import scala.io.StdIn.readLine

private[Schach] object Schach {
  def main(args: Array[String]): Unit = {
    var field = new Field()
    val tui = new TUI
    var input: String = ""
    do {
      println("Grid :\n" + field.toString)
      input = readLine()
      field = tui.processInputLine(input, field)
    } while (input != "q")
  }
}

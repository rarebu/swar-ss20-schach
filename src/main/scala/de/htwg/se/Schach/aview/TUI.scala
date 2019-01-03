package de.htwg.se.Schach.aview

import de.htwg.se.Schach.model.Field

class TUI {
  def processInputLine(input: String, field:Field):Field = {
    input match {
      case "n"=> new Field()
      case _ => field
    }
  }
}

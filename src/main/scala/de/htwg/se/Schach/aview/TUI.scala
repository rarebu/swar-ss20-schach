package de.htwg.se.Schach.aview

import de.htwg.se.Schach.model.Field

class TUI {
  def processInputLine(input: String, field: Field): Field = {
    input match {
      case "n" => new Field()
      case _ => input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
        case row :: column :: newRow :: newColumn :: Nil => field.move(row, column, newRow, newColumn)
        case _ => field
      }
    }
  }
}

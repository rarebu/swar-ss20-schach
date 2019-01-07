package de.htwg.se.Schach.controller

import de.htwg.se.Schach.model.Field
import de.htwg.se.Schach.util.Observable

class Controller(var field: Field) extends Observable {
  def newField(): Unit = {
    new Field()
    notifyObservers()
  }

  def move(row: Int, column: Int, newRow: Int, newColumn: Int): Unit = {
    field = field.move(row, column, newRow, newColumn)
    notifyObservers()
  }

  def fieldToString: String = field.toString
}
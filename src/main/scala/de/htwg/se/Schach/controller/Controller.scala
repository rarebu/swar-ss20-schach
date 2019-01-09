package de.htwg.se.Schach.controller

import de.htwg.se.Schach.model.Field
import de.htwg.se.Schach.model.Figure
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

  def changePawn(representation: String): Unit = {
    field = field.changePawn(representation)
    notifyObservers()
  }

  def getChangableFigures: String = Figure.CHANGABLE_BLACK_FIGURES + Figure.CHANGABLE_WHITE_FIGURES

  def fieldToString: String = field.toString
}
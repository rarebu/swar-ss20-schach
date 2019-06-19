package de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl

import scala.swing.Publisher

trait ControllerInterface extends Publisher {
def newField: Unit
def undo: Unit
def redo: Unit
def pawnPromoting: Option[String]
def choose(representation: String): Unit
def move(row: Int, col: Int, newRow: Int, newCol: Int): Unit
def statusText: String
def getChangableFigures: String
def fieldToString: String
def cellContains(row: Int, col: Int): String
def cellIsBlack(row: Int, col: Int): Boolean
}

import scala.swing.event.Event

class CellChanged extends Event

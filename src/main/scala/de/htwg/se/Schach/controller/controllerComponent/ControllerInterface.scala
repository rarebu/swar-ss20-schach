package de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl

import play.api.libs.json.JsValue

import scala.swing.Publisher
import scala.util.Try

trait ControllerInterface extends Publisher {

def newField: Unit

def undo: Unit

def redo: Unit

def save: Unit

def load: Unit

def pawnPromoting: Option[String]

def choose(representation: String): Try[ControllerInterface]

def move(row: Int, col: Int, newRow: Int, newCol: Int): Try[ControllerInterface]

def statusText: String

def getChangeableFigures: String

def fieldToString: String

def cellContains(row: Int, col: Int): String

def cellIsBlack(row: Int, col: Int): Boolean

def toJson: JsValue
}

import scala.swing.event.Event

class CellChanged extends Event

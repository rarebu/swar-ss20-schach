package de.htwg.se.Schach.model

import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.{Coordinates, Field, Figure}

trait FieldInterface {
  def cellIsBlack(row: Int, col: Int): Boolean

  def cellContains(row: Int, col: Int): Option[String]

  def getFigures: Option[String]

  def move(row: Int, col: Int, newRow: Int, newCol: Int): Option[FieldInterface]

  def unMove(row: Int, col: Int, newRow: Int, newCol: Int): FieldInterface

  def changePawn(input: String): Option[FieldInterface]

  def undoChangePawn(input: String): Option[FieldInterface]

  def CHANGEABLE_BLACK_FIGURES: String

  def CHANGEABLE_WHITE_FIGURES: String

  def getSize: Int

  def getRoundCount: Int

  def getToChange: String
}

trait RemovedFiguresInterface {
  def getEntryList:List[EntryInterface]

  def append(entry: EntryInterface)

  def containsFigureThatGotRemovedThisRound(round: Int): Option[EntryInterface]
}

trait EntryInterface {
  def getFigure:FigureInterface

  def getCoordinate:CoordinatesInterface

  def getRound:Int
}

trait FigureInterface {
  def getRepresentation: String

  def isBlack: Boolean

  def getPossibleNewPositions(field: Field, coordinates: Coordinates): Vector[Vector[CoordinatesInterface]]

}

trait CoordinatesInterface {
  def getCoordinates:(Int, Int)
}

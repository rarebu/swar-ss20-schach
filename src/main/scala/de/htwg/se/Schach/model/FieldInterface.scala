package de.htwg.se.Schach.model

import play.api.libs.json.JsValue

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

  def getField:FieldDataInterface

  def toJson: JsValue
}

trait FieldDataInterface {
 def getField:FieldInterface

  def getFigurePositions:List[FigureInterface]

  def getToChange:Option[ToChangeInterface]

  def getRemovedFigures:List[RemovedFigureInterface]

  def getRoundCount:Int
}

trait FigureInterface {
  def isBlack:Boolean

  def getKind:String

  def getStepCount:Int

  def getPosition:(Int, Int)

}

trait RemovedFigureInterface {
  def getFigure:FigureInterface

  def getRound:Int
}

trait ToChangeInterface {
  def getFigure:FigureInterface

  def isBlack:Boolean
}

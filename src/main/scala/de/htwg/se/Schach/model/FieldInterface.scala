package de.htwg.se.Schach.model

import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.{PersistFigure, PersistRemovedFigure, PersistToChange}
import play.api.libs.json.JsValue

trait FieldInterface {
  def cellIsBlack(row: Int, col: Int): Boolean

  def cellContains(row: Int, col: Int): Option[String]

  def cellContentIsBlack(row: Int, col: Int): Option[Boolean]

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

  override def toString: String = isBlack.toString + " " + getKind + " " + getStepCount + " " + getPosition._1 + " " + getPosition._2

}

object FigureInterface {
  def fromString(figureAsString: String): FigureInterface = {
    val figureSplit = figureAsString.split(" ")
    val isBlack = figureSplit(0).toBoolean
    val kind = figureSplit(1)
    val stepCount = figureSplit(2).toInt
    val position = (figureSplit(3).toInt, figureSplit(4).toInt)
    PersistFigure(isBlack, kind, stepCount, position)
  }
}

trait RemovedFigureInterface {
  def getFigure:FigureInterface

  def getRound:Int

  override def toString: String = getFigure.toString + " " + getRound
}

object RemovedFigureInterface {
  def fromString(removedFigureAsString: String): RemovedFigureInterface = {
    val removedFigureSplit = removedFigureAsString.splitAt(removedFigureAsString.lastIndexOf(" "))
//    println("Removed Figure Field 1: " + removedFigureSplit._1 + " Removed Figure Field 1: " + removedFigureSplit._2)
    val figure = FigureInterface.fromString(removedFigureSplit._1)
//    println("Figure: " + figure)
    val round = removedFigureSplit._2.trim.toInt
//    println("Round: " + round)
    PersistRemovedFigure(figure, round)
  }
}

trait ToChangeInterface {
  def getFigure:FigureInterface

  def isBlack:Boolean

  override def toString: String = getFigure.toString + " " + isBlack.toString
}

object ToChangeInterface {
  def fromString(toChangeAsString: String): ToChangeInterface = {
    val toChangeSplit = toChangeAsString.splitAt(toChangeAsString.lastIndexOf(" "))
    val figure = FigureInterface.fromString(toChangeSplit._1)
    val isBlackColour = toChangeSplit._2.toBoolean
    PersistToChange(figure, isBlackColour)
  }
}

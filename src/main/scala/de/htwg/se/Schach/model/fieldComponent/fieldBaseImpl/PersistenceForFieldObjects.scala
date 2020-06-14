package de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl

import de.htwg.se.Schach.model.{FieldDataInterface, FieldInterface, FigureInterface, RemovedFigureInterface, ToChangeInterface}



case class PersistFigure(isBlackColour:Boolean, kind:String, stepCount:Int, position:(Int,Int)) extends FigureInterface {
  override def isBlack: Boolean = isBlackColour

  override def getKind: String = kind

  override def getStepCount: Int = stepCount

  override def getPosition: (Int, Int) = position
}



case class PersistRemovedFigure(figure:FigureInterface, round:Int) extends RemovedFigureInterface {
  override def getFigure: FigureInterface = figure

  override def getRound: Int = round
}

case class PersistToChange(figure:FigureInterface, isBlackColour:Boolean) extends ToChangeInterface {
  override def getFigure: FigureInterface = figure

  override def isBlack: Boolean = isBlackColour
}

case class PersistField(figurePositions:List[FigureInterface], toChange: Option[ToChangeInterface], removedFigures: List[RemovedFigureInterface], roundCount:Int) extends FieldDataInterface {
  override def getField: FieldInterface = new Field(figurePositions, toChange, removedFigures, roundCount)

  override def getFigurePositions: List[FigureInterface] = figurePositions

  override def getToChange: Option[ToChangeInterface] = toChange

  override def getRemovedFigures: List[RemovedFigureInterface] = removedFigures

  override def getRoundCount: Int = roundCount
}

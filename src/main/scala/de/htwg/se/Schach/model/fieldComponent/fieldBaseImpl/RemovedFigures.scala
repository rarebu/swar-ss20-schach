package de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl

import de.htwg.se.Schach.model.RemovedFigureInterface

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

case class RemovedFigures(storage: mutable.Buffer[Entry]) {
  def this() = this(new ListBuffer[Entry])

  def this(removedFigures:List[RemovedFigureInterface]) = {
    this({
      removedFigures.map(removedFigure => {
        val tmpFig = removedFigure.getFigure
        val figure = Figure.apply(tmpFig.getKind, Colour.getBlackColour(tmpFig.isBlack), tmpFig.getStepCount)
        val position = tmpFig.getPosition
        Entry(figure.get, Coordinates(position._1,position._2),removedFigure.getRound)
      }).toBuffer
    })
  }

  def append(entry: Entry) = storage += entry

  def containsFigureThatGotRemovedThisRound(round: Int): Option[Entry] = {
    storage.find(entry => entry.round == round) match {
      case Some(t) => {
        storage.remove(storage.indexOf(t))
        Option.apply(t)
      }
      case None => None
    }
  }

  def persistRemoveFigures: List[PersistRemovedFigure] = {
    storage.map(entry => {
      val tmpFigure = entry.figure
      val tmpFigureIsBlack = tmpFigure.colour == Colour.black
      val tmpCoordinates = entry.coordinates
      PersistRemovedFigure(PersistFigure(tmpFigureIsBlack, tmpFigure.getName, tmpFigure.getStepCount, tmpCoordinates.getCoordinates), entry.round)
    }).toList
  }
}

case class Entry(figure: Figure, coordinates: Coordinates, round: Int)

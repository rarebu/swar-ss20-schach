package de.htwg.se.schach.model

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

case class RemovedFigures(storage: mutable.Buffer[Entry]) {
  def this() = this(new ListBuffer[Entry])

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
}

case class Entry(figure: Figure, coordinates: Coordinates, round: Int)

package de.htwg.se.Schach.model.database

import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.PersistField
import de.htwg.se.Schach.model.{FieldDataInterface, FigureInterface, RemovedFigureInterface, ToChangeInterface}

import scala.util.Try

trait LogicDatabaseInterface {
  def create(name: String, field: FieldDataInterface): Try[Unit]
  def read(name: String): Try[FieldDataInterface]
  def update(name: String, field: FieldDataInterface): Try[Unit]
  def delete(name: String): Try[Unit]
  def initStorage: Try[Unit]
}

case class FieldDatabase(uniqueName: String, figurePositions: String, toChange: Option[String], removedFigures: String, roundCount: Int) {
  def this(uniqueName: String, field: FieldDataInterface) = this(
    uniqueName,
    field.getFigurePositions.map(figurePosition => figurePosition.toString).mkString(","),
    if(field.getToChange.isDefined) Some(field.getToChange.get.toString) else None,
    field.getRemovedFigures.map(removedFigure => removedFigure.toString).mkString(","),
    field.getRoundCount
  )

  def toPersistField: PersistField = {
    val figurePositions = this.figurePositions.split(",").map(figurePosition => FigureInterface.fromString(figurePosition)).toList
//    println("figurePositions: " + figurePositions)
    val toChange = this.toChange.map(x => ToChangeInterface.fromString(x))
//    println("toChange: " + toChange)
    val removedFigures = if(this.removedFigures.size > 0) {
      val removedFiguresArray = this.removedFigures.split(",")
//      println("removedFiguresArray: " + removedFiguresArray.deep.mkString("\n"))
      removedFiguresArray.map(removedFigure => {
//        println(removedFigure)
        RemovedFigureInterface.fromString(removedFigure)}).toList
    } else List.empty
//    println("removedFigures: " + removedFigures)
    PersistField(figurePositions, toChange, removedFigures, roundCount)
  }
}
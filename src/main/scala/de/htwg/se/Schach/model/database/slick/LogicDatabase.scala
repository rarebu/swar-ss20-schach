package de.htwg.se.Schach.model.database.slick

import de.htwg.se.Schach.model.{FieldDataInterface, FigureInterface, RemovedFigureInterface, ToChangeInterface}
import de.htwg.se.Schach.model.database.LogicDatabaseInterface
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.PersistField
import slick.driver.MySQLDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class LogicDatabase extends LogicDatabaseInterface {
  val fieldDatabase = TableQuery[PersistanceMapping]
  val db = Database.forConfig("mysql")

  ///SQL INSERT
  override def create(name: String, field: FieldDataInterface): Boolean = {
    val insertAction = DBIO.seq(
      fieldDatabase.schema.create,
      fieldDatabase += (new FieldDatabase(name, field))
    )
    Await.result(db.run(insertAction), Duration.Inf)
    true
  }

  ///SQL SELECT
  override def read(name: String): Option[FieldDataInterface] = {
    val readAction = fieldDatabase.filter(_.uniqueName === name).result
    Some(Await.result(db.run(readAction), Duration.Inf).map(x => x.toPersistField).head)
//    null
  }

  ///SQL UPDATE
  override def update(name: String, field: FieldDataInterface): Boolean = {
    val upsertAction = fieldDatabase.insertOrUpdate(new FieldDatabase(name, field))
    Await.result(db.run(upsertAction), Duration.Inf)
    true
  }

  ///SQL DELETE
  override def delete(name: String): Boolean = {
    val deleteAction = fieldDatabase.filter(_.uniqueName === name).delete
    Await.result(db.run(deleteAction), Duration.Inf)
    true
  }
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
    val toChange = this.toChange.map(x => ToChangeInterface.fromString(x))
    println("1 " + this.removedFigures)
    println("1 " + this.removedFigures.size)
//    println("1 " + this.removedFigures.)
    val removedFiguresArray = this.removedFigures.split(",")
    val removedFigures = if(removedFiguresArray.size > 0) removedFiguresArray.map(removedFigure => RemovedFigureInterface.fromString(removedFigure)).toList else List.empty
    PersistField(figurePositions, toChange, removedFigures, roundCount)
  }
}

class PersistanceMapping(tag:Tag) extends Table[FieldDatabase](tag, "logic") {
  def uniqueName = column[String]("name", O.PrimaryKey)
  def figurePositions = column[String]("figure_positions")
  def toChange = column[Option[String]]("to_change")
  def removedFigures = column[String]("removed_figures")
  def roundCount = column[Int]("round_count")

  override def * = (uniqueName, figurePositions, toChange, removedFigures, roundCount) <> (create, extract)
  def create(t: (String, String, Option[String], String, Int)): FieldDatabase = FieldDatabase(t._1, t._2, t._3, t._4, t._5)

  def extract(f: FieldDatabase): Option[(String, String, Option[String], String, Int)] = Some((f.uniqueName, f.figurePositions, f.toChange, f.removedFigures, f.roundCount))

}

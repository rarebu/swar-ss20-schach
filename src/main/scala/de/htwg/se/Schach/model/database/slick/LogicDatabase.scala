package de.htwg.se.Schach.model.database.slick

import akka.actor.Status.Success
import de.htwg.se.Schach.model.{FieldDataInterface, FigureInterface, RemovedFigureInterface, ToChangeInterface}
import de.htwg.se.Schach.model.database.LogicDatabaseInterface
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.PersistField
import slick.driver.MySQLDriver.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Try

class LogicDatabase extends LogicDatabaseInterface {
  val fieldDatabase = TableQuery[PersistanceMapping]
  val db = Database.forConfig("mysql")

  ///SQL INSERT
  override def create(name: String, field: FieldDataInterface): Try[Unit] = {
    val insertAction = DBIO.seq(
      fieldDatabase.insertOrUpdate(new FieldDatabase(name, field))
    )
    Try(Await.result(db.run(insertAction), Duration.Inf))
  }

  ///SQL SELECT
  override def read(name: String): Try[FieldDataInterface] = {
    val readAction = fieldDatabase.filter(_.uniqueName === name).result
    Try(Await.result(db.run(readAction), Duration.Inf).map(x => x.toPersistField).head)
  }

  ///SQL UPDATE
  override def update(name: String, field: FieldDataInterface): Try[Unit] = {
    val upsertAction = fieldDatabase.insertOrUpdate(new FieldDatabase(name, field))
    Try(Await.result(db.run(upsertAction), Duration.Inf))
  }

  ///SQL DELETE
  override def delete(name: String): Try[Unit] = {
    val deleteAction = fieldDatabase.filter(_.uniqueName === name).delete
    Try(Await.result(db.run(deleteAction), Duration.Inf))
  }

  override def initStorage: Try[Unit] = {
    val createTableAction = DBIO.seq(
      fieldDatabase.schema.create
    )
    Try(Await.result(db.run(createTableAction), Duration.Inf))
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
    val removedFigures = if(this.removedFigures.size > 0) {
      val removedFiguresArray = this.removedFigures.split(",")
      removedFiguresArray.map(removedFigure => RemovedFigureInterface.fromString(removedFigure)).toList
    } else List.empty
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

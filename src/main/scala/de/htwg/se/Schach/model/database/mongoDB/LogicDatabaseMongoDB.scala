package de.htwg.se.Schach.model.database.mongoDB

import de.htwg.se.Schach.model.FieldDataInterface
import de.htwg.se.Schach.model.database.{FieldDatabase, LogicDatabaseInterface}
import org.mongodb.scala._
import org.mongodb.scala.model.Filters._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Try

class LogicDatabaseMongoDB extends LogicDatabaseInterface {
  val client: MongoClient = MongoClient()
  val database: MongoDatabase = client.getDatabase("mongoDB")


  override def create(name: String, field: FieldDataInterface): Try[Unit] = {
    val collection: MongoCollection[Document] = database.getCollection("logic")
    val fieldDatabase = new FieldDatabase(name, field)
    val document: Document = Document("uniqueName" -> fieldDatabase.uniqueName, "figurePositions" -> fieldDatabase.figurePositions,
      "toChange" -> {if(fieldDatabase.toChange.isDefined) fieldDatabase.toChange.get else ""}, "removedFigures" -> fieldDatabase.removedFigures,
      "roundCount" -> fieldDatabase.roundCount)

    val g = Await.result(collection.countDocuments().toFuture(), Duration.Inf)
    println("size is: " + g)
    Try(if(g > 0)
      {
        println("create")
        Await.result(collection.insertOne(document).toFuture(), Duration.Inf)
      }
    else {
      println("Already created")
      val filterDocument: Document = Document("uniqueName" -> name)
      Await.result(collection.updateOne(filterDocument, document).toFuture(), Duration.Inf)
    })

  }

  override def read(name: String): Try[FieldDataInterface] = {
    val collection: MongoCollection[Document] = database.getCollection("logic")

    println("read") //DEBUG
    Try({
      val a = Await.result(collection.find(equal("uniqueName", name)).first().map(document =>
        FieldDatabase(document.get("uniqueName").get.asString().getValue, document.get("figurePositions").get.asString().getValue,
          if(document.get("toChange").get.asString().getValue.length > 0) Some(document.get("toChange").get.asString().getValue) else None,
          document.get("removedFigures").get.asString().getValue, document.get("roundCount").get.asInt32().getValue)).toFuture(), Duration.Inf)
      println(a + " " + a.size)
      a.head.toPersistField
    })
  }

  override def update(name: String, field: FieldDataInterface): Try[Unit] = {
    val collection: MongoCollection[Document] = database.getCollection("logic")
    val fieldDatabase = new FieldDatabase(name, field)
    val document: Document = Document("uniqueName" -> fieldDatabase.uniqueName, "figurePositions" -> fieldDatabase.figurePositions,
      "toChange" -> {if(fieldDatabase.toChange.isDefined) fieldDatabase.toChange.get else ""}, "removedFigures" -> fieldDatabase.removedFigures,
      "roundCount" -> fieldDatabase.roundCount)
    val filterDocument: Document = Document("uniqueName" -> name)

    Try(Await.result(collection.updateOne(filterDocument, document).toFuture(), Duration.Inf))
  }

  override def delete(name: String): Try[Unit] = {
    val collection: MongoCollection[Document] = database.getCollection("logic")
    val filterDocument: Document = Document("uniqueName" -> name)

    Try(Await.result(collection.deleteOne(filterDocument).toFuture(), Duration.Inf))
  }

  override def initStorage: Try[Unit] = {
    Try(Await.result(database.createCollection("logic").toFuture(), Duration.Inf))
  }
}

package de.htwg.se.Schach.model.database

import com.google.inject.AbstractModule
import de.htwg.se.Schach.model.database.mongoDB.LogicDatabaseMongoDB
import de.htwg.se.Schach.model.database.slick.LogicDatabaseMySQL
import net.codingwell.scalaguice.ScalaModule

class DatabaseTypeInjection  extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[LogicDatabaseInterface].to[LogicDatabaseMongoDB]
  }
}

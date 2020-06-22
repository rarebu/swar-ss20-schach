package de.htwg.se.Schach.model.database

import com.google.inject.Guice
import de.htwg.se.Schach.model.FieldDataInterface

object DataAccessObject {
  val injector = Guice.createInjector(new DatabaseTypeInjection)
  val database = injector.getInstance(classOf[LogicDatabaseInterface])
  database.initStorage

  def create(name: String, field: FieldDataInterface): Unit = {
    while(database.create(name, field).isFailure){}
  }

  def read(name: String): FieldDataInterface = {
    while(true) {
      val field = database.read(name)
      if(field.isSuccess) return field.get
    }
    null
  }

  def update(name: String, field: FieldDataInterface): Unit = while(database.update(name, field).isFailure){}

  def delete(name: String): Unit = while(database.delete(name).isFailure){}
}

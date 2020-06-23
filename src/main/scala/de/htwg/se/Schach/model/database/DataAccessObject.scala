package de.htwg.se.Schach.model.database

import com.google.inject.Guice
import de.htwg.se.Schach.model.FieldDataInterface

object DataAccessObject {
  val injector = Guice.createInjector(new DatabaseTypeInjection)
  val database = injector.getInstance(classOf[LogicDatabaseInterface])
  val x = database.initStorage
  if (x.isFailure)
    println(x.failed.get.getMessage)
  else
    println("Good")

  def create(name: String, field: FieldDataInterface): Unit = {
    while(database.create(name, field).isFailure){}
  }

  def read(name: String): FieldDataInterface = {
    while(true) {
      val field = database.read(name)
//      if(field.isSuccess) return field.get
      if(field.isSuccess) {
        println(field.get)
        return field.get
      } else {
        println("error: " + field.failed.get.getMessage)
      }
    }
    null
  }

  def update(name: String, field: FieldDataInterface): Unit = while(database.update(name, field).isFailure){}

  def delete(name: String): Unit = while(database.delete(name).isFailure){}
}

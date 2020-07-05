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
    while(true){
      val a = database.create(name, field)
      if(a.isSuccess) {
        println("Success")
        return
      } else {
        println("Failure: " + a.failed.get.getMessage)
//        System.exit(1)
        return
      }
    }
  }

  def read(name: String): FieldDataInterface = {
    while(true) {
      val field = database.read(name)
      if(field.isSuccess) return field.get
      if(field.isSuccess) {
        println("The field is: " + field.get)
        return field.get
      } else {
        println("error: " + field.failed.get.getMessage)
        System.exit(1)
      }
    }
    null
  }

  def update(name: String, field: FieldDataInterface): Unit = while(database.update(name, field).isFailure){}

  def delete(name: String): Unit = while(database.delete(name).isFailure){}
}

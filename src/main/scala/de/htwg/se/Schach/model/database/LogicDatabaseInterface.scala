package de.htwg.se.Schach.model.database

import de.htwg.se.Schach.model.FieldDataInterface

trait LogicDatabaseInterface {
  def create(name: String, field: FieldDataInterface): Boolean
  def read(name: String): Option[FieldDataInterface]
  def update(name: String, field: FieldDataInterface): Boolean
  def delete(name: String): Boolean
}

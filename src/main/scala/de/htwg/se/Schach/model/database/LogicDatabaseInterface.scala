package de.htwg.se.Schach.model.database

import de.htwg.se.Schach.model.FieldDataInterface

import scala.util.Try

trait LogicDatabaseInterface {
  def create(name: String, field: FieldDataInterface): Try[Unit]
  def read(name: String): Try[FieldDataInterface]
  def update(name: String, field: FieldDataInterface): Try[Unit]
  def delete(name: String): Try[Unit]
  def initStorage: Try[Unit]
}

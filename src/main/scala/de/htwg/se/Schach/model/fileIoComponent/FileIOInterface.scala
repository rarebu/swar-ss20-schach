package de.htwg.se.Schach.model.fileIoComponent

import de.htwg.se.Schach.model.FieldInterface

trait FileIOInterface {
  def load:FieldInterface

  def save(field:FieldInterface):Unit
}

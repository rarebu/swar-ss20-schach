package de.htwg.se.Schach.model.fileIoComponent

import de.htwg.se.Schach.model.FieldInterface

trait FileIO {
  def load:FieldInterface

  def save:Unit
}

package de.htwg.se.Schach.model.fileIoComponent.fileIoSlickImpl

import de.htwg.se.Schach.model.FieldInterface
import de.htwg.se.Schach.model.database.slick.LogicDatabase
import de.htwg.se.Schach.model.fileIoComponent.FileIO

class FileIOSlick extends FileIO {
  val dao = new LogicDatabase
  override def load: FieldInterface = dao.read("nameDesSpiels").get.getField

  override def save(field: FieldInterface): Unit = dao.create("nameDesSpiels", field.getField)
}

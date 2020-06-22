package de.htwg.se.Schach.model.fileIoComponent.fileIoDatabaseImpl

import de.htwg.se.Schach.model.FieldInterface
import de.htwg.se.Schach.model.database.DataAccessObject
import de.htwg.se.Schach.model.fileIoComponent.FileIO

class FileIODatabase extends FileIO {
  val dao = DataAccessObject
  override def load: FieldInterface = dao.read("nameDesSpiels").getField

  override def save(field: FieldInterface): Unit = dao.create("nameDesSpiels", field.getField)
}

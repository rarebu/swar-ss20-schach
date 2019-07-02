package de.htwg.se.Schach.model.fileIoComponent.fileIoXmlImpl

import de.htwg.se.Schach.model.{FieldDataInterface, FieldInterface}
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field
import org.scalatest.{Matchers, WordSpec}

class FileIOInterfaceSpec extends WordSpec with Matchers {
  "A Field" when {
    "a pawn was moved and a persistence structure is requested" should{
      val field = new Field
      val fieldMoved = field.move(1,0,3,0).get
      val fileIo = new FileIO
      fileIo.save(fieldMoved)
      val fieldLoaded = fileIo.load
      "have" in {
        fieldLoaded.move(3,0,4,0).get.cellContains(4,0).get should be("♟")
      }
    }
  }
}

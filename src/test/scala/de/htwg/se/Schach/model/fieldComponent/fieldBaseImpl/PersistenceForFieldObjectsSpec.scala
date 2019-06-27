package de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl

import de.htwg.se.Schach.model.{FieldDataInterface, FieldInterface}
import org.scalatest.{Matchers, WordSpec}

class PersistenceForFieldObjectsSpec extends WordSpec with Matchers {
  "A Field" when {
    "a pawn was moved and a persistence structure is requested" should{
      val field = new Field
      val fieldMoved = field.move(1,0,3,0).get
      val fieldMovedPersistenceStructure:FieldDataInterface = fieldMoved.getField
      val fieldMovedLoaded:FieldInterface = fieldMovedPersistenceStructure.getField
      "have" in {
        fieldMovedLoaded.move(3,0,4,0).get.cellContains(4,0).get should be("♟")
      }
    }
  }
}

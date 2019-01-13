package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FieldSpec extends WordSpec with Matchers {
  "A Field" when {
    "new" should {
      val field = new Field()
      "have 64 Cells" in {
        field.cells.size should be(8)
      }
      "have 8 Rows" in {
        field.cells.rows.size should be(8)
      }
      "have a String in a Cell" in {
        field.cells.cell(0, 0).contains.get.toString should be("♜")
      }
    }
  }
}
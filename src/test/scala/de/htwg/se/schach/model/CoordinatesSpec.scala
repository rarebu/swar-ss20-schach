package de.htwg.se.schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class CoordinatesSpec extends WordSpec with Matchers {
  "A Coordinate" when {
    "new" should {
      val coord = Coordinates(4, 3)
      "have a Column" in {
        coord.col should be(3)
      }
      "have a Row" in {
        coord.row should be(4)
      }
    }
  }
}

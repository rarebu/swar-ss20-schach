package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class KingSpec extends WordSpec with Matchers {
  "A black King" when {
    "new" should {
      val king = new King(Colour.black, true)
      val field = new Field()
      val coordinates = Coordinates(0, 4)
      "have a name" in {
        king.getName should be("King")
      }
      "have 5 possible new positions" in {
        king.getPossibleNewPositions(field, coordinates).size should be(0)
      }
    }
  }
}

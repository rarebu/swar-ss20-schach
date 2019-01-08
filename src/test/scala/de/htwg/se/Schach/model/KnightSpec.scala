package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class KnightSpec extends WordSpec with Matchers {
  "A black Knight" when {
    "new" should {
      val knight = new Knight(Colour.black)
      val field = new Field()
      val coordinates = Coordinates(0, 1)
      "have a name" in {
        knight.getName should be("Knight")
      }
      "have 3 possible new positions" in {
        knight.getPossibleNewPositions(field, coordinates).size should be(2)
      }
    }
  }
}

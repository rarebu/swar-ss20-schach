package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class KnightSpec extends WordSpec with Matchers {
  "A black Knight" when {
    "new" should {
      val knight = new Knight(Colour.black, Side.left)
      val field = new Field()
      "have a name" in {
        knight.getName should be("Knight")
      }
      "have 3 possible new positions" in {
        knight.getPossibleNewPositions(field, knight.coordinates).size should be(3)
      }
    }
  }
}

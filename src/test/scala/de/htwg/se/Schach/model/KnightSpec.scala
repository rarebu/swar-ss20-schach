package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class KnightSpec extends WordSpec with Matchers {
  "A black Knight" when {
    "new" should {
      val knight = new Knight(Colour.Black)
      "have a name" in {
        knight.getName should be("Knight")
      }
      "have 4 possible new positions" in {
        knight.getPossibleNewPositions().size should be(4)
      }
      "have an ability" in {
        knight.hasAbility should be(false)
      }
    }
  }
}

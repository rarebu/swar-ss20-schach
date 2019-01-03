package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class RookSpec extends WordSpec with Matchers {
  "A black Rook" when {
    "new" should {
      val rook = new Rook(Colour.Black)
      "have a name" in {
        rook.getName should be("Rook")
      }
      "have 24 possible new positions" in {
        rook.getPossibleNewPositions().size should be(14)
      }
      "have an ability" in {
        rook.hasAbility should be(true)
      }
    }
  }
}

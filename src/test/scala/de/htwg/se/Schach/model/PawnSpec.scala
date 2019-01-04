package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class PawnSpec extends WordSpec with Matchers {
  "A black Pawn" when {
    "new" should {
      val pawn = new Pawn(Colour.Black, 4)
      "have a name" in {
        pawn.getName should be("Pawn")
      }
      "have 6 possible new positions" in {
        pawn.getPossibleNewPositions.size should be(6)
      }
      "have an ability" in {
        pawn.hasAbility should be(false)
      }
    }
  }
}

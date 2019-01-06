package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class PawnSpec extends WordSpec with Matchers {
  "A black Pawn" when {
    "new" should {
      val pawn = new Pawn(Colour.black, 4)
      val field = new Field()
      "have a name" in {
        pawn.getName should be("Pawn")
      }
      "have 2 possible new positions" in {
        pawn.getPossibleNewPositions(field, pawn.coordinates)(0).size should be(2)
      }
    }
  }
}
package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class RookSpec extends WordSpec with Matchers {
  "A black Rook" when {
    "new" should {
      val rook = new Rook(Colour.black, Side.left)
      val field = new Field()
      "have a name" in {
        rook.getName should be("Rook")
      }
      "have 24 possible new positions" in {
        rook.getPossibleNewPositions(field, rook.coordinates).size should be(2)
      }
    }
  }
}

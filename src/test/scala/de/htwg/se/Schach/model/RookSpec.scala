package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class RookSpec extends WordSpec with Matchers {
  "A black Rook" when {
    "new" should {
      val rook = new Rook(Colour.black, true)
      val field = new Field()
      val coordinates = Coordinates(0, 0)
      "have a name" in {
        rook.getName should be("Rook")
      }
      "have 24 possible new positions" in {
        rook.getPossibleNewPositions(field, coordinates).size should be(0)
      }
    }
  }
}

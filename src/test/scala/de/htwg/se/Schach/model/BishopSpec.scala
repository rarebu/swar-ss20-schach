package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class BishopSpec extends WordSpec with Matchers {
  "A black Bishop" when {
    "new" should {
      val bishop = new Bishop(Colour.black)
      val field = new Field()
      val coordinates = Coordinates(4, 4)
      "have a name" in {
        bishop.getName should be("Bishop")
      }
      "have 7 possible new positions" in {
        bishop.getPossibleNewPositions(field, coordinates).size should be(4)
        bishop.getPossibleNewPositions(field, coordinates)(0).size should be(2)
        bishop.getPossibleNewPositions(field, coordinates)(1).size should be(2)
      }
    }
  }
}

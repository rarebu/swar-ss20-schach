package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class BishopSpec extends WordSpec with Matchers {
  "A black Bishop" when {
    "new" should {
      val bishop = new Bishop(Colour.black, Side.left)
      val field = new Field()
      "have a name" in {
        bishop.getName should be("Bishop")
      }
      "have 7 possible new positions" in {
        bishop.getPossibleNewPositions(field, bishop.coordinates).size should be(2)
        bishop.getPossibleNewPositions(field, bishop.coordinates)(0).size should be(5)
        bishop.getPossibleNewPositions(field, bishop.coordinates)(1).size should be(2)
      }
    }
  }
}

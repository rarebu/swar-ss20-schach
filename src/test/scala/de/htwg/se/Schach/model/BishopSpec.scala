package de.htwg.se.Schach.model

import de.htwg.se.Schach.model.Figure.Side
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class BishopSpec extends WordSpec with Matchers {
  "A black Bishop" when {
    "new" should {
      val bishop = new Bishop(Colour.Black, Side.Left)
      "have a name" in {
        bishop.getName should be("Bishop")
      }
      "have 7 possible new positions" in {
        bishop.getPossibleNewPositions.size should be(2)
        bishop.getPossibleNewPositions(0).size should be(5)
        bishop.getPossibleNewPositions(1).size should be(2)
      }
    }
  }
}

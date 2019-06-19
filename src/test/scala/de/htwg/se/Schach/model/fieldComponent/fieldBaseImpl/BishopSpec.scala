package de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl

import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.{Bishop, Colour}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class BishopSpec extends WordSpec with Matchers {
  "A Bishop" when {
    "new" should {
      val bishop = new Bishop(Colour.white, 0)
      "move" in {
        bishop.move.asInstanceOf[Bishop].stepCounter should be(1)
      }
    }
    "moved" should {
      val bishop = new Bishop(Colour.white, 2)
      "unMove" in {
        bishop.unMove.asInstanceOf[Bishop].stepCounter should be(1)
      }
    }
  }
  "A black Bishop" when {
    "new" should {
      val bishop = new Bishop(Colour.black, 0)
      val field = new Field()
      val coordinates = Coordinates(4, 4)
      "have a name" in {
        bishop.getName should be("Bishop")
      }
      "have 4 possible new directions and 8 new positions" in {
        bishop.getPossibleNewPositions(field, coordinates).size should be(4)
        bishop.getPossibleNewPositions(field, coordinates)(0).size should be(2)
        bishop.getPossibleNewPositions(field, coordinates)(1).size should be(2)
        bishop.getPossibleNewPositions(field, coordinates)(2).size should be(2)
        bishop.getPossibleNewPositions(field, coordinates)(3).size should be(2)
      }
      "have a symbol" in {
        bishop.toString should be("♝")
      }
      "have a colour" in {
        bishop.colour should be(Colour.black)
      }
    }
  }
  "A white Bishop" when {
    "new on 4,7" should {
      val bishop = new Bishop(Colour.white, 1)
      val field = new Field()
      val coordinates = Coordinates(4, 7)
      "have 2 possible new directions and 4 new positions" in {
        bishop.getPossibleNewPositions(field, coordinates).size should be(2)
        bishop.getPossibleNewPositions(field, coordinates)(0).size should be(3)
        bishop.getPossibleNewPositions(field, coordinates)(1).size should be(1)
      }
    }
    "new on 5,7" should {
      val bishop = new Bishop(Colour.white, 1)
      val field = new Field()
      val coordinates = Coordinates(5, 7)
      "have 1 possible new direction and 4 new positions" in {
        bishop.getPossibleNewPositions(field, coordinates).size should be(1)
        bishop.getPossibleNewPositions(field, coordinates)(0).size should be(4)
      }
    }
  }
}

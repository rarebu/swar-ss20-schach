package de.htwg.se.Schach.model

import de.htwg.se.Schach.model.Field.Field
import de.htwg.se.Schach.model.Figures.Rook
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class RookSpec extends WordSpec with Matchers {
  "A Rook" when {
    "new" should {
      val rook = new Rook(Colour.white, 0)
      "have an ability" in {
        rook.hasAbility should be(true)
      }
      "move" in {
        rook.move.asInstanceOf[Rook].stepCounter should be(1)
      }
    }
    "moved" should {
      val rook = new Rook(Colour.white, 2)
      "have no ability" in {
        rook.hasAbility should be(false)
      }
      "unMove" in {
        rook.unMove.asInstanceOf[Rook].stepCounter should be(1)
      }
    }
  }
  "A black Rook" when {
    "new" should {
      val rook = new Rook(Colour.black, 0)
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
  "A white Rook" when {
    "new on 4,7" should {
      val rook = new Rook(Colour.white, 1)
      val field = new Field()
      val coordinates = Coordinates(4, 7)
      "have 11 possible new possible positions in 3 directions" in {
        rook.getPossibleNewPositions(field, coordinates).size should be(3)
        rook.getPossibleNewPositions(field, coordinates)(0).size should be(3)
        rook.getPossibleNewPositions(field, coordinates)(1).size should be(7)
        rook.getPossibleNewPositions(field, coordinates)(2).size should be(1)
      }
    }
    "new on 4,4" should {
      val rook = new Rook(Colour.white, 1)
      val field = new Field()
      val coordinates = Coordinates(4, 4)
      "have 13 possible new possible positions in 4 directions" in {
        rook.getPossibleNewPositions(field, coordinates).size should be(4)
        rook.getPossibleNewPositions(field, coordinates)(0).size should be(3)
        rook.getPossibleNewPositions(field, coordinates)(1).size should be(4)
        rook.getPossibleNewPositions(field, coordinates)(2).size should be(3)
        rook.getPossibleNewPositions(field, coordinates)(2).size should be(3)
      }
    }
  }
}
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
  "A white Rook" when {
    "new on 4,7" should {
      val rook = new Rook(Colour.white, ability = false)
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
      val rook = new Rook(Colour.white, ability = false)
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

package de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl

import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.{Colour, Queen}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class QueenSpec extends WordSpec with Matchers {
  "A Queen" when {
    "new" should {
      val queen = new Queen(Colour.white, 0)
      "move" in {
        queen.move.asInstanceOf[Queen].stepCounter should be(1)
      }
    }
    "moved" should {
      val queen = new Queen(Colour.white, 2)
      "unMove" in {
        queen.unMove.asInstanceOf[Queen].stepCounter should be(1)
      }
    }
  }
  "A black Queen" when {
    "new on 0,3" should {
      val queen = new Queen(Colour.black, 0)
      val field = new Field()
      val coordinates = Coordinates(0, 3)
      "have a name" in {
        queen.getName should be("Queen")
      }
      "have 0 possible new positions" in {
        queen.getPossibleNewPositions(field, coordinates).size should be(0)
      }
    }
  }
  "A white Queen" when {
    "new on 4,4" should {
      val queen = new Queen(Colour.white, 0)
      val field = new Field()
      val coordinates = Coordinates(4, 4)
      "have 19 possible new positions in 8 directions" in {
        queen.getPossibleNewPositions(field, coordinates).size should be(8)
        queen.getPossibleNewPositions(field, coordinates)(0).size should be(3)
        queen.getPossibleNewPositions(field, coordinates)(1).size should be(4)
        queen.getPossibleNewPositions(field, coordinates)(2).size should be(3)
        queen.getPossibleNewPositions(field, coordinates)(3).size should be(1)
        queen.getPossibleNewPositions(field, coordinates)(4).size should be(3)
        queen.getPossibleNewPositions(field, coordinates)(5).size should be(3)
        queen.getPossibleNewPositions(field, coordinates)(6).size should be(1)
        queen.getPossibleNewPositions(field, coordinates)(7).size should be(1)

      }
    }
  }
}

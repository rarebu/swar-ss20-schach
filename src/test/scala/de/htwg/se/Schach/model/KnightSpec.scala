package de.htwg.se.Schach.model

import de.htwg.se.Schach.model.Field.Field
import de.htwg.se.Schach.model.Figures.Knight
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class KnightSpec extends WordSpec with Matchers {
  "A Knight" when {
    "new" should {
      val knight = new Knight(Colour.white, 0)
      "move" in {
        knight.move.asInstanceOf[Knight].stepCounter should be(1)
      }
    }
    "moved" should {
      val knight = new Knight(Colour.white, 2)
      "unMove" in {
        knight.unMove.asInstanceOf[Knight].stepCounter should be(1)
      }
    }
  }
  "A black Knight" when {
    "new" should {
      val knight = new Knight(Colour.black, 0)
      val field = new Field()
      val coordinates = Coordinates(0, 1)
      "have a name" in {
        knight.getName should be("Knight")
      }
      "have 3 possible new positions" in {
        knight.getPossibleNewPositions(field, coordinates).size should be(2)
      }
    }
  }
  "A white Knight" when {
    "new on 7,7" should {
      val knight = new Knight(Colour.white, 1)
      val field = new Field()
      val coordinates = Coordinates(7, 7)
      "have 1 possible new positions" in {
        knight.getPossibleNewPositions(field, coordinates).size should be(1)
        knight.getPossibleNewPositions(field, coordinates)(0).size should be(1)
      }
    }
    "new on 3,3" should {
      val knight = new Knight(Colour.white, 1)
      val field = new Field()
      val coordinates = Coordinates(5, 7)
      "have 2 possible new direction and 2 new positions" in {
        knight.getPossibleNewPositions(field, coordinates).size should be(2)
        knight.getPossibleNewPositions(field, coordinates)(0).size should be(1)
        knight.getPossibleNewPositions(field, coordinates)(1).size should be(1)
      }
    }
  }
}
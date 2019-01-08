package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class QueenSpec extends WordSpec with Matchers {
  "A black Queen" when {
    "new" should {
      val queen = new Queen(Colour.black)
      val field = new Field()
      val coordinates = Coordinates(0, 3)
      "have a name" in {
        queen.getName should be("Queen")
      }
      "have 21 possible new positions" in {
        queen.getPossibleNewPositions(field, coordinates).size should be(0)
      }
    }
  }
}
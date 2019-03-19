package de.htwg.se.Schach.model.rules

import de.htwg.se.Schach.model.Field.Field
import de.htwg.se.Schach.util.Coordinates
import de.htwg.se.Schach.util.{Colour, Coordinates, Utils}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class MovesSpec extends WordSpec with Matchers {
  "The Moves object" when {
    "used" should {
      val field = new Field()
      val colour = Colour.black
      "make queenMove steps" in {
        Moves.queenMove(field, colour, Coordinates(3, 3)).size should be(8)
      }
      "make a knightMove" in {
        Moves.knightMove(field, colour, Coordinates(4, 4)).size should be(8)
      }
      "make a kingMove" in {
        Moves.kingMove(field, colour, Coordinates(4, 4), true).size should be(8)
      }
      "make a rookMove" in {
        Moves.rookMove(field, colour, Coordinates(4, 4)).size should be(4)
      }
      "make a bishopMove" in {
        Moves.bishopMove(field, colour, Coordinates(4, 4)).size should be(4)
      }
      "make a pawnMove" in {
        Moves.pawnMove(field, colour, Coordinates(4, 4), false)(0).size should be(1)
        Moves.pawnMove(field, colour, Coordinates(1, 0), true)(0).size should be(2)
      }
    }
  }
}
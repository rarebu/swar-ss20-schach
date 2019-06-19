package de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.rules

import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.rules.Castling
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.{Colour, Coordinates, Field, King}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class CastilingSpec extends WordSpec with Matchers {
  "The Castling object" when {
    "used" should {
      val field = new Field()
      val castlingField = field.move(1, 1, 3, 1).get
        .move(0, 2, 1, 1).get.move(0, 1, 2, 0).get
        .move(1, 6, 3, 6).get.move(0, 5, 1, 6).get.move(0, 6, 2, 7).get
        .move(1, 3, 3, 3).get.move(0, 3, 1, 3).get
      val coordinates = Coordinates(0, 4)
      "check if castling is possible" in {
        Castling.castlingPossible(field, coordinates, 0, 1 to 3) should be(false)
        Castling.castlingPossible(castlingField, coordinates, 0, 1 to 3) should be(true)
        Castling.castlingPossible(castlingField, coordinates, 7, 5 to 6) should be(true)
      }
      "castle kingside" in {
        Castling.castleKingside(field, coordinates) should be(None)
        Castling.castleKingside(castlingField, coordinates) should be(Some(Coordinates(0, 6)))
      }
      "caslte queenside" in {
        Castling.castleQueenside(field, coordinates) should be(None)
        Castling.castleQueenside(castlingField, coordinates) should be(Some(Coordinates(0, 2)))
      }
      "do castling" in {
        Castling.doCastling(Coordinates(0, 4), Coordinates(0, 2), field, King(Colour.black, 0)) should not be (field)
      }
    }
  }
}

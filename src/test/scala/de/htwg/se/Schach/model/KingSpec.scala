package de.htwg.se.Schach.model

import de.htwg.se.Schach.model.Field.getFigure
import de.htwg.se.Schach.model.Figure.{ROW_BLACK, ROW_BLACK_PAWN, ROW_WHITE, ROW_WHITE_PAWN}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class KingSpec extends WordSpec with Matchers {
  "A black King" when {
    "new" should {
      val king = new King(Colour.black, false)
      val field = new Field()
      val coordinates = Coordinates(0, 4)
      "have a name" in {
        king.getName should be("King")
      }
      "have 5 possible new positions" in {
        king.getPossibleNewPositions(field, coordinates).size should be(0)
      }
    }
  }
  "A black King" when {
    "alone with a Rook" should {
      val cell = Cell(Colour.black, Option.empty)
      val matrix = new Matrix[Cell]((row, col) => {
        val a = (row, col) match {
          case (7, 3) => Option.apply(new King(Colour.black, true))
          case (7, 0) => Option.apply(new Rook(Colour.black, true))
          case _ => Option.empty
        }
        if (row % 2 == 0)
          if (col % 2 == 0) Cell(Colour.white, a) else Cell(Colour.black, a)
        else if (col % 2 == 0) Cell(Colour.black, a) else Cell(Colour.white, a)
      })
      val field = Field(matrix)
      "have the possibility to do a 'rochade'" in {
        field.move(7,3,7,1).toString should be() //to see whats in the field
      }

    }
  }
}

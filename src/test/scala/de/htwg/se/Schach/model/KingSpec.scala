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
  "A white King" when {
    "alone with a Rook" should {
      val matrix = new Matrix[Cell]((row, col) => {
        val a = (row, col) match {
          case (7, 4) => Option.apply(new King(Colour.white, true))
          case (7, 7) => Option.apply(new Rook(Colour.white, true))
          case _ => Option.empty
        }
        if (row % 2 == 0)
          if (col % 2 == 0) Cell(Colour.white, a) else Cell(Colour.black, a)
        else if (col % 2 == 0) Cell(Colour.black, a) else Cell(Colour.white, a)
      })
      var field = Field(matrix)
      "be able to do a 'rochade'" in {
        field.cells.cell(7,4).toString should be("#♔#")
        field.cells.cell(7,5).toString should be("♦⁕⁕♦")
        field.cells.cell(7,6).toString should be("♦##♦")
        field.cells.cell(7,7).toString should be("⁕♖⁕")
        field = field.move(7,4,7,6)
        field.cells.cell(7,4).toString should be("♦##♦")
        field.cells.cell(7,5).toString should be("⁕♖⁕")
        field.cells.cell(7,6).toString should be("#♔#")
        field.cells.cell(7,7).toString should be("♦⁕⁕♦")
      }

    }
  }
}

package de.htwg.se.schach.model.rules

import de.htwg.se.schach.model._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class PawnPromotionSpec extends WordSpec with Matchers {
  "A Pawn Promotion Object" when {
    "new" should {
      val coordinates = Coordinates(6, 0)
      val newCoordinates = Coordinates(7, 0)
      val pawn = Pawn(Colour.black, 1)
      val input = "♝"
      val matrix = new Matrix[Cell]((row, col) => {
        val a = (row, col) match {
          case (6, 0) => Option.apply(Pawn(Colour.black, 1))
          case _ => Option.empty
        }
        if (row % 2 == 0)
          if (col % 2 == 0) Cell(Colour.white, a) else Cell(Colour.black, a)
        else if (col % 2 == 0) Cell(Colour.black, a) else Cell(Colour.white, a)
      })
      val field = Field(matrix, None, 3, new RemovedFigures())
      val changeFigure = Option.apply(ToChange(Coordinates(7, 0), field.cell(7, 0), pawn))
      "be asked what figure to switch into" in {
        PawnPromotion.pawnChange(Colour.black) should be("♛♜♝♞")
      }
      "do pawn promotion" in {
        PawnPromotion.doPawnPromotion(coordinates, newCoordinates, pawn, field) should not be (field)
      }
      "find figure" in {
        PawnPromotion.findFigure(Colour.black, input) should be(Option.apply(Bishop(Colour.black, 1)))
      }
      "change pawn" in {
        PawnPromotion.changePawn(field, changeFigure, input) should not be (field)
      }
    }
  }
}

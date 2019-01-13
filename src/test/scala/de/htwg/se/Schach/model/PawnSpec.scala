package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class PawnSpec extends WordSpec with Matchers {
  "A black Pawn" when {
    "new" should {
      val pawn = Pawn(Colour.black, 0)
      val field = new Field()
      val coordinates = Coordinates(1, 4)
      "have a name" in {
        pawn.getName should be("Pawn")
      }
      "have 2 possible new positions" in {
        pawn.getPossibleNewPositions(field, coordinates)(0).size should be(2)
      }
    }
  }
  "A black Pawn" when {
    "hitting the opposite edge" should {
      val matrix = new Matrix[Cell]((row, col) => {
        val a = (row, col) match {
          case (6, 1) => Option.apply(Pawn(Colour.black, 1))
          case _ => Option.empty
        }
        if (row % 2 == 0)
          if (col % 2 == 0) Cell(Colour.white, a) else Cell(Colour.black, a)
        else if (col % 2 == 0) Cell(Colour.black, a) else Cell(Colour.white, a)
      })
      val field = Field(matrix, None, 3, new RemovedFigures())
      field.cells.cell(6, 1).toString should be("#♟#")
      var newfield = field.move(6, 1, 7, 1, false).get
      "be able to switch itself to a queen" in {
        newfield = newfield.changePawn("♜").get
        newfield.cells.cell(7, 1).toString should be("⁕♜⁕")
      }
    }
  }
  "A white Pawn" when {
    "new on 5,2" should {
      val pawn = Pawn(Colour.white, 0)
      val field = new Field()
      val coordinates = Coordinates(5, 2)
      "have 2 possible new positions in one direction" in {
        pawn.getPossibleNewPositions(field, coordinates).size should be(1)
        pawn.getPossibleNewPositions(field, coordinates)(0).size should be(2)
      }
    }
    "used on 5,2" should {
      val pawn = Pawn(Colour.white, 1)
      val field = new Field()
      val coordinates = Coordinates(5, 2)
      "have 1 possible new position in one dircetion" in {
        pawn.getPossibleNewPositions(field, coordinates).size should be(1)
        pawn.getPossibleNewPositions(field, coordinates)(0).size should be(1)
      }
    }
  }
}
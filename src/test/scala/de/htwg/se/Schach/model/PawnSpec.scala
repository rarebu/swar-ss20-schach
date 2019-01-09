package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class PawnSpec extends WordSpec with Matchers {
  "A black Pawn" when {
    "new" should {
      val pawn = Pawn(Colour.black, ability = true)
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
          case (6, 1) => Option.apply(Pawn(Colour.black, ability = false))
          case _ => Option.empty
        }
        if (row % 2 == 0)
          if (col % 2 == 0) Cell(Colour.white, a) else Cell(Colour.black, a)
        else if (col % 2 == 0) Cell(Colour.black, a) else Cell(Colour.white, a)
      })
      var field = Field(matrix)
      "be able to switch itself to a queen" in {
        field.cells.cell(6,1).toString should be("#♟#")
        field = field.move(6,1,7,1)
        field.cells.cell(7,1).toString should be("⁕♟⁕")
        //TODO

      }

    }
  }
  "A white Pawn" when {
    "new on 5,2" should {
      val pawn = Pawn(Colour.white, ability = true)
      val field = new Field()
      val coordinates = Coordinates(5, 2)
      "have 2 possible new positions in one direction" in {
        pawn.getPossibleNewPositions(field, coordinates).size should be(1)
        pawn.getPossibleNewPositions(field, coordinates)(0).size should be(2)
      }
    }
    "used on 5,2" should {
      val pawn = Pawn(Colour.white, ability = false)
      val field = new Field()
      val coordinates = Coordinates(5, 2)
      "have 1 possible new position in one dircetion" in {
        pawn.getPossibleNewPositions(field, coordinates).size should be(1)
        pawn.getPossibleNewPositions(field, coordinates)(0).size should be(1)
      }
    }
  }
}

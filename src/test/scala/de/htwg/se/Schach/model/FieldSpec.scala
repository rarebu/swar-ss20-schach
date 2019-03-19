package de.htwg.se.Schach.model

import de.htwg.se.Schach.model.Field.{Cell, Field}
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FieldSpec extends WordSpec with Matchers {
  "A Field" when {
    "new" should {
      val field = new Field()
      val coordinates = Coordinates(0, 0)
      val coordinates1 = Coordinates(1, 0)
      val cell = field.cell(0, 0)
      val cellReplace = Cell(cell.colour, None)
      val figure = cell.contains.get
      val cell1 = field.cell(1, 0)
      val cellReplace1 = Cell(cell1.colour, None)
      val figure1 = cell1.contains.get

      "have 64 Cells" in {
        field.cells.size should be(8)
      }
      "have 8 Rows" in {
        field.cells.rows.size should be(8)
      }
      "have a String in a Cell" in {
        field.cells.cell(0, 0).contains.get.toString should be("♜")
      }
      "can get a Cell" in {
        field.cell(0, 0).toString should be("⁕♜⁕")
      }
      "move two figures" in {
        field.moveTwoFigures(coordinates, coordinates1, figure, coordinates1, coordinates, figure1, false).roundCounter should be(1)
      }
      "move one figure" in {
        field.moveOneFigure(coordinates, coordinates1, None, figure).roundCounter should be(1)
      }
      "replace a cell" in {
        field.replace(coordinates, cellReplace, None, false).cell(0, 0).toString should be("♦⁕⁕♦")
      }
      "replace two cells" in {
        field.replaceTwo(coordinates, cellReplace, coordinates1, cellReplace1, None, false).cell(1, 0).toString should be("♦##♦")
      }
      "replace four cells" in {
        field.replaceFour(coordinates, cellReplace, coordinates1, cellReplace1, Coordinates(0, 1),
          Cell(field.cell(0, 1).colour, None), Coordinates(1, 1), Cell(field.cell(1, 1).colour, None), false).cell(1, 1).toString should be("♦⁕⁕♦")
      }
      "move" in {
        field.move(1, 0, 3, 0).get.roundCounter should be(1)
      }
      "unmove" in {
        val movedField = field.move(1, 0, 3, 0).get
        movedField.unMove(3, 0, 1, 0).roundCounter should be(0)
      }
      "change pawn" in {
        //TODO
        //field.changePawn()
      }
      "undo change pawn" in {
        //TODO
        //field.undoChangePawn()
      }
    }
  }
}
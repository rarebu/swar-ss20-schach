package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CellSpec extends WordSpec with Matchers {
  "A Cell" when {
    "new" should {
      val cell = Cell(Colour.black, Option.empty)
      "have a colour" in {
        cell.colour should be(Colour.black)
      }
      "have no Figure" in {
        cell.contains should be(None)
      }
      "have a String for TUI" in {
        cell.toString should be("♦##♦")
      }
    }
  }

  def cellContainsFigure(field: Field, coordinates: Coordinates): Boolean = field.cell(coordinates.row, coordinates.col).contains.isDefined

  "A Cell" when {
    "used" should {
      val cell = Cell(Colour.white, Option.apply(new Rook(Colour.white, 1)))
      "have a colour" in {
        cell.colour should be(Colour.white)
      }
      "have a Figure" in {
        cellContainsFigure(new Field, Coordinates(0, 0)) should be(true)
      }
      "have a String for TUI" in {
        cell.toString should be("⁕♖⁕")
      }
    }
  }
}
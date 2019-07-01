package de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl

import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.{Cell, Colour}
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field.getFigure
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Figure.{ROW_BLACK, ROW_BLACK_PAWN, ROW_WHITE, ROW_WHITE_PAWN}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class MatrixSpec extends WordSpec with Matchers {
  "A Matrix is a tailor-made immutable data type that contains a two-dimentional Vector of Cells. A Matrix" when {
    "empty " should {
      "be created by using a dimension and a empty cell" in {
        val matrix = new Matrix[Cell]((row, col) => {
          val a = (row, col) match {
            case _ => Option.empty
          }
          if (row % 2 == 0)
            if (col % 2 == 0) Cell(Colour.white, a) else Cell(Colour.black, a)
          else if (col % 2 == 0) Cell(Colour.black, a) else Cell(Colour.white, a)
        })
        matrix.size should be(Field.SIZE)
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val testMatrix = Matrix(Vector(Vector(Cell(Colour.white, Option.empty))))
        testMatrix.size should be(1)
      }

    }
    "filled" should {
      val matrix = new Matrix[Cell]((row, col) => {
        val a = (row, col) match {
          case (ROW_BLACK, column) => getFigure(Colour.black, column)
          case (ROW_WHITE, column) => getFigure(Colour.white, column)
          case (ROW_BLACK_PAWN, column) => Option.apply(Pawn(Colour.black, 0))
          case (ROW_WHITE_PAWN, column) => Option.apply(Pawn(Colour.white, 0))
          case _ => Option.empty
        }
        if (row % 2 == 0)
          if (col % 2 == 0) Cell(Colour.white, a) else Cell(Colour.black, a)
        else if (col % 2 == 0) Cell(Colour.black, a) else Cell(Colour.white, a)
      })
      "give access to its cells" in {
        matrix.cell(0, 0).toString should be("⁕♜⁕")
      }
      "replace cells and return a new data structure" in {
        val returnedMatrix = matrix.replaceCell(0, 0, Cell(Colour.white, Option.empty))
        matrix.cell(0, 0).toString should be("⁕♜⁕")
        returnedMatrix.cell(0, 0).toString should be("♦⁕⁕♦")
      }
      /*"be filled" in { //todo
        matrix should be(Matrix(Vector(Vector("⁕♜⁕", "#♞#", "⁕♝⁕", "#♛#", "⁕♚⁕", "#♝#", "⁕♞⁕", "#♜#"), Vector("#♟#", "⁕♟⁕", "#♟#", "⁕♟⁕", "#♟#", "⁕♟⁕", "#♟#", "⁕♟⁕"), Vector("♦⁕⁕♦", "♦##♦", "♦⁕⁕♦", "♦##♦", "♦⁕⁕♦", "♦##♦", "♦⁕⁕♦", "♦##♦"), Vector("♦##♦", "♦⁕⁕♦", "♦##♦", "♦⁕⁕♦", "♦##♦", "♦⁕⁕♦", "♦##♦", "♦⁕⁕♦"), Vector("♦⁕⁕♦", "♦##♦", "♦⁕⁕♦", "♦##♦", "♦⁕⁕♦", "♦##♦", "♦⁕⁕♦", "♦##♦"), Vector("♦##♦", "♦⁕⁕♦", "♦##♦", "♦⁕⁕♦", "♦##♦", "♦⁕⁕♦", "♦##♦", "♦⁕⁕♦"), Vector("⁕♙⁕", "#♙#", "⁕♙⁕", "#♙#", "⁕♙⁕", "#♙#", "⁕♙⁕", "#♙#"), Vector("#♖#", "⁕♘⁕", "#♗#", "⁕♕⁕", "#♔#", "⁕♗⁕", "#♘#", "⁕♖⁕"))))
      }*/
    }
  }
  "A Matrix" when {
    "new" should {
      val matrix = new Field().cells
      "be black at 0:0" in {
        matrix.rows(0)(0).colour should be(Colour.white)
      }
      "be white at 0:1" in {
        matrix.rows(0)(1).colour should be(Colour.black)
      }
      "be black at 7:7" in {
        matrix.rows(7)(7).colour should be(Colour.white)
      }
      "be white at 7:6" in {
        matrix.rows(7)(6).colour should be(Colour.black)
      }
      "should have a size of 8" in {
        matrix.size should be(Field.SIZE)
      }
      "should be able to replace a Cell" in {
        matrix.replaceCell(3, 3, Cell(Colour.white, Option.empty))
      }
    }
  }
}

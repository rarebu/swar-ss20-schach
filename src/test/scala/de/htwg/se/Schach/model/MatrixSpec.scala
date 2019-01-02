package de.htwg.se.Schach.model

package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner
import King._
import Figure._

@RunWith(classOf[JUnitRunner])
class MatrixSpec extends WordSpec with Matchers {
  "A Matrix" when {
    "new" should {
      val matrix = new Matrix[Cell]((row, col) => {
        val a = (row, col) match {
          case (COL_FIGURE, ROW_BLACK) => Option.apply(new King(Colour.Black))
          case _ => Option.empty
        }
        if (row % 2 == 0)
          if (col % 2 == 0) Cell(Colour.Black, Option.empty) else Cell(Colour.White, Option.empty)
        else if (col % 2 == 0) Cell(Colour.White, Option.empty) else Cell(Colour.Black, Option.empty)
      })
      "be black at 0:0" in {
        matrix.rows(0)(0).colour should be(Colour.Black)
      }
      "be white at 0:1" in {
        matrix.rows(0)(1).colour should be(Colour.White)
      }
      "be black at 7:7" in {
        matrix.rows(7)(7).colour should be(Colour.Black)
      }
      "be white at 7:6" in {
        matrix.rows(7)(6).colour should be(Colour.White)
      }
      "should have a size of 8" in {
        matrix.size should be(8)
      }
    }
  }
}


package de.htwg.se.schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class FigureSpec extends WordSpec with Matchers {
  "A Figure" when {
    "new" should {
      val fig = Figure
      val figure = Figure.applyNew("King", Colour.black).get
      "bla" in {
        figure.isInstanceOf[King] should be(true)
      }
      "have a black row" in {
        fig.ROW_BLACK.toString() should be("0")
      }
      "have a white row" in {
        fig.ROW_WHITE.toString() should be("7")
      }
      "have a black Pawn row" in {
        fig.ROW_BLACK_PAWN.toString() should be("1")
      }
      "have a white Pawn row" in {
        fig.ROW_WHITE_PAWN.toString() should be("6")
      }
    }
  }
}
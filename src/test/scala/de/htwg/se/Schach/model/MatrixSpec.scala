package de.htwg.se.Schach.model


import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MatrixSpec extends WordSpec with Matchers {
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
        matrix.size should be(8)
      }
    }
  }
}


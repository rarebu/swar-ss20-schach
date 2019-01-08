package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class ColourSpec extends WordSpec with Matchers {
  "A Colour" when {
    "new" should {
      val colourblack = Colour.black
      val colourwhite = Colour.white
      "be black" in {
        colourblack.leftSideValue should be(Colour.black)
      }
      "be white" in {
        colourwhite.leftSideValue should be(Colour.white)
      }
    }
  }
}

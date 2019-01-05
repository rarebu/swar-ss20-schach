package de.htwg.se.Schach.model

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CellSpec extends WordSpec with Matchers {
  "A Cell" when { "new" should {
    val cell = Cell(Colour.black, Option.empty)
    "have a colour"  in {
      cell.colour should be(Colour.black)
    }
    "have no Figure" in {
      cell.contains should be(None)
    }
  }}
}

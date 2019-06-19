package de.htwg.se.Schach.util

import de.htwg.se.Schach.model.fieldBaseImpl.{Colour, Field}
import de.htwg.se.Schach.model.fieldBaseImpl.Coordinates
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class ValidationSpec extends WordSpec with Matchers {
  "The Validation object" when {
    "used" should {
      val vector = Vector(Coordinates(0, 0), Coordinates(0, -1), Coordinates(2, 2))
      val multivector = Vector(vector, Vector(Coordinates(4, 4), Coordinates(2, 3), Coordinates(3, -1)))
      val field = new Field()
      val colour = Colour.black
      "validate Coordinates" in {
        Validation.validCoordinate(Coordinates(0, 4)) should be(true)
        Validation.validCoordinate(Coordinates(-1, 4)) should be(false)
        Validation.validCoordinate(Coordinates(2, 8)) should be(false)
      }
      "remove invalids from Vector" in {
        Validation.removeInvalidsFromVector(vector).size should be(1)
      }
      "remove invalids from Multi-Vector" in {
        Validation.removeInvalidsFromMultiVector(field, colour, multivector)(0).size should be(2)
      }
      "validate values" in {
        Validation.isAValidValueInsideTheField(2) should be(true)
        Validation.isAValidValueInsideTheField(-1) should be(false)
        Validation.isAValidValueInsideTheField(8) should be(false)
      }
    }
  }
}

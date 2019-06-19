package de.htwg.se.Schach.util

import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.{Colour, Field}
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Coordinates
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class UtilsSpec extends WordSpec with Matchers {
  "The Utils object" when {
    "used" should {
      val vector = Vector(Coordinates(0, 0), Coordinates(0, -1), Coordinates(2, 2))
      val multivector = Vector(vector, Vector(Coordinates(4, 4), Coordinates(2, 3), Coordinates(3, -1)))
      val field = new Field()
      val colour = Colour.black
      "go single step" in {
        Utils.goOneStepUp(Coordinates(0, 1)) should be(Coordinates(1, 1))
        Utils.goOneStepDown(Coordinates(1, 1)) should be(Coordinates(0, 1))
        Utils.goOneStepRight(Coordinates(1, 1)) should be(Coordinates(1, 2))
        Utils.goOneStepLeft(Coordinates(1, 1)) should be(Coordinates(1, 0))
        Utils.goOneStepLeftUp(Coordinates(1, 1)) should be(Coordinates(2, 0))
        Utils.goOneStepLeftDown(Coordinates(1, 1)) should be(Coordinates(0, 0))
        Utils.goOneStepRightUp(Coordinates(1, 1)) should be(Coordinates(2, 2))
        Utils.goOneStepRightDown(Coordinates(1, 1)) should be(Coordinates(0, 2))
      }
      "make complex single steps" in {
        Utils.oneStepCross(field, colour, Coordinates(1, 1)).size should be(1)
        Utils.oneStepCross(field, colour, Coordinates(0, 0)).size should be(0)
        Utils.oneStepDiagonal(field, colour, Coordinates(4, 4)).size should be(4)
        Utils.oneStepDiagonal(field, colour, Coordinates(2, 3)).size should be(2)
        Utils.goOneStepInAllDirections(field, colour, Coordinates(2, 4)).size should be(5)
      }
      "make two steps" in {
        Utils.twoStepsDown(Coordinates(2, 2)) should be(Coordinates(0, 2))
        Utils.twoStepsUp(Coordinates(2, 2)) should be(Coordinates(4, 2))
        Utils.twoStepsLeft(Coordinates(2, 2)) should be(Coordinates(2, 0))
        Utils.twoStepsRight(Coordinates(2, 2)) should be(Coordinates(2, 4))
      }
      "make two steps OR one step" in {
        Utils.goTwoStepsUpOrOneStepUp(field, colour, Coordinates(3, 3)).size should be(1)
      }
      "make multiple steps" in {
        Utils.goMultiStepsCross(field, colour, Coordinates(3, 3)).size should be(4)
        Utils.goMultiStepsDiagonal(field, colour, Coordinates(3, 3)).size should be(4)
      }
    }
  }
}

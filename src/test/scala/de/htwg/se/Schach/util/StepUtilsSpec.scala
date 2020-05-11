package de.htwg.se.Schach.util

import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.{Colour, Field}
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Coordinates
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class StepUtilsSpec extends WordSpec with Matchers {
  "The StepUtils object" when {
    "used" should {
      val vector = Vector(Coordinates(0, 0), Coordinates(0, -1), Coordinates(2, 2))
      val multivector = Vector(vector, Vector(Coordinates(4, 4), Coordinates(2, 3), Coordinates(3, -1)))
      val field = new Field()
      val colour = Colour.black
      "go single step" in {
        StepUtils.goOneStepUp(Coordinates(0, 1)) should be(Coordinates(1, 1))
        StepUtils.goOneStepDown(Coordinates(1, 1)) should be(Coordinates(0, 1))
        StepUtils.goOneStepRight(Coordinates(1, 1)) should be(Coordinates(1, 2))
        StepUtils.goOneStepLeft(Coordinates(1, 1)) should be(Coordinates(1, 0))
        StepUtils.goOneStepLeftUp(Coordinates(1, 1)) should be(Coordinates(2, 0))
        StepUtils.goOneStepLeftDown(Coordinates(1, 1)) should be(Coordinates(0, 0))
        StepUtils.goOneStepRightUp(Coordinates(1, 1)) should be(Coordinates(2, 2))
        StepUtils.goOneStepRightDown(Coordinates(1, 1)) should be(Coordinates(0, 2))
      }
      "make complex single steps" in {
        StepUtils.oneStepCross(field, colour, Coordinates(1, 1)).size should be(1)
        StepUtils.oneStepCross(field, colour, Coordinates(0, 0)).size should be(0)
        StepUtils.oneStepDiagonal(field, colour, Coordinates(4, 4)).size should be(4)
        StepUtils.oneStepDiagonal(field, colour, Coordinates(2, 3)).size should be(2)
        StepUtils.goOneStepInAllDirections(field, colour, Coordinates(2, 4)).size should be(5)
      }
      "make two steps" in {
        StepUtils.twoStepsDown(Coordinates(2, 2)) should be(Coordinates(0, 2))
        StepUtils.twoStepsUp(Coordinates(2, 2)) should be(Coordinates(4, 2))
        StepUtils.twoStepsLeft(Coordinates(2, 2)) should be(Coordinates(2, 0))
        StepUtils.twoStepsRight(Coordinates(2, 2)) should be(Coordinates(2, 4))
      }
      "make two steps OR one step" in {
        StepUtils.goTwoStepsUpOrOneStepUp(field, colour, Coordinates(3, 3)).size should be(1)
      }
      "make multiple steps" in {
        StepUtils.goMultiStepsCross(field, colour, Coordinates(3, 3)).size should be(4)
        StepUtils.goMultiStepsDiagonal(field, colour, Coordinates(3, 3)).size should be(4)
      }
    }
  }
}

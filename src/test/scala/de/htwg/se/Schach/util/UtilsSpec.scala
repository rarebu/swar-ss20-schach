package de.htwg.se.Schach.util

import de.htwg.se.Schach.model.Coordinates
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class UtilsSpec extends WordSpec with Matchers {
  "The Utils object" when {
    "used" should {
      val vector = Vector(Coordinates(0, 0), Coordinates(0, -1), Coordinates(2, 2))
      val multivector = Vector(vector, Vector(Coordinates(4, 4), Coordinates(2, 3), Coordinates(3, -1)))
      "validate Coordinates" in {
        Utils.validCoordinate(Coordinates(0, 4)) should be(true)
        Utils.validCoordinate(Coordinates(-1, 4)) should be(false)
        Utils.validCoordinate(Coordinates(2, 8)) should be(false)
      }
      "remove invalids from Vector" in {
        Utils.removeInvalidsFromVector(vector).size should be(1)
      }
      "remove invalids from Multi-Vector" in {
        Utils.removeInvalidsFromMultiVector(multivector).size should be(2)
      }
      "validate values" in {
        Utils.isAValidValueInsideTheField(2) should be(true)
        Utils.isAValidValueInsideTheField(-1) should be(false)
        Utils.isAValidValueInsideTheField(8) should be(false)
      }
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
        Utils.oneStepCross(Coordinates(1, 1)).size should be(4)
        Utils.oneStepCross(Coordinates(0, 0)).size should be(2)
        Utils.oneStepDiagonal(Coordinates(1, 1)).size should be(4)
        Utils.oneStepDiagonal(Coordinates(0, 0)).size should be(1)
        Utils.goOneStepInAllDirections(Coordinates(0, 4)).size should be(5)
      }
      "make two steps" in {
        Utils.twoStepsDown(Coordinates(2, 2)) should be(Coordinates(0, 2))
        Utils.twoStepsUp(Coordinates(2, 2)) should be(Coordinates(4, 2))
        Utils.twoStepsLeft(Coordinates(2, 2)) should be(Coordinates(2, 0))
        Utils.twoStepsRight(Coordinates(2, 2)) should be(Coordinates(2, 4))
      }
      "make two steps OR one step" in {
        Utils.goTwoStepsUpOrOneStepUp(Coordinates(3, 3)).size should be(1)
      }
      "make multiple steps" in {
        Utils.goMultiStepsCross(Coordinates(3, 3)).size should be(4)
        Utils.goMultiStepsDiagonal(Coordinates(3, 3)).size should be(4)
        Utils.goMultiStepsInAllDirections(Coordinates(3, 3)).size should be(8)
      }
      "make a knightJump" in {
        Utils.goKnightJump(Coordinates(3, 3)).size should be(8)
      }
    }
  }
}


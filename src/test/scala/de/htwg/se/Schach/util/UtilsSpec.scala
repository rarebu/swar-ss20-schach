package de.htwg.se.Schach.util

import de.htwg.se.Schach.model.Coordinates

class UtilsSpec {
  val vector = Vector(Coordinates(0, 0), Coordinates(0, -1), Coordinates(2, 2))
  val multivector = Vector(vector, Vector(Coordinates(4, 4), Coordinates(2, 3), Coordinates(3, -1)))
  Utils.validCoordinate(Coordinates(0, 4))
  !Utils.validCoordinate(Coordinates(-1, 4))
  !Utils.validCoordinate(Coordinates(2, 8))
  Utils.removeInvalidsFromVector(vector).size == 1
  Utils.removeInvalidsFromMultiVector(multivector).size == 2
  Utils.isAValidValueInsideTheField(2)
  !Utils.isAValidValueInsideTheField(-1)
  !Utils.isAValidValueInsideTheField(8)
  Utils.goOneStepUp(Coordinates(0, 1)) == Coordinates(0, 2)
  Utils.goOneStepDown(Coordinates(0, 1)) == Coordinates(0, 0)
  Utils.goOneStepRight(Coordinates(1, 1)) == Coordinates(2, 1)
  Utils.goOneStepLeft(Coordinates(1, 1)) == Coordinates(0, 1)
  Utils.goOneStepLeftUp(Coordinates(1, 1)) == Coordinates(0, 2)
  Utils.goOneStepLeftDown(Coordinates(1, 1)) == Coordinates(0, 0)
  Utils.goOneStepRightUp(Coordinates(1, 1)) == Coordinates(2, 2)
  Utils.goOneStepRightDown(Coordinates(1, 1)) == Coordinates(2, 0)
  Utils.oneStepCross(Coordinates(1, 1)).size == 4
  Utils.oneStepCross((Coordinates(0, 0))).size == 2
  Utils.oneStepDiagonal(Coordinates(1, 1)).size == 4
  Utils.oneStepDiagonal(Coordinates(0, 0)).size == 1
  Utils.goOnStepInAllDirections(Coordinates(0, 4)).size == 5
}

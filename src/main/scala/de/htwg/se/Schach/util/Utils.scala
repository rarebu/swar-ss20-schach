
package de.htwg.se.Schach.util

import de.htwg.se.Schach.model.Coordinates

object Utils {
  def validCoordinate(coordinates: Coordinates): Boolean =
    isAValidValueInsideTheField(coordinates.row) && isAValidValueInsideTheField(coordinates.col)

  def removeInvalidsFromVector(vector: Vector[Coordinates]): Vector[Coordinates] = {
    vector.zipWithIndex foreach { case (coord, index) =>
      if (!validCoordinate(coord)) {
        return vector.slice(0, index)
      }
    }
    vector
  }

  def removeInvalidsFromMultiVector(multiVector: Vector[Vector[Coordinates]]): Vector[Vector[Coordinates]] = {
    var newMultiVector: Vector[Vector[Coordinates]] = Vector()
    multiVector foreach {
      vector =>
        val b = removeInvalidsFromVector(vector)
        if (b.nonEmpty) newMultiVector = newMultiVector :+ b
    }
    newMultiVector
  }

  def isAValidValueInsideTheField(value: Int): Boolean = value >= 0 && value < 8

  def goOneStepUp(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row + 1, coordinates.col)

  def goOneStepDown(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row - 1, coordinates.col)

  def goOneStepRight(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row, coordinates.col + 1)

  def goOneStepLeft(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row, coordinates.col - 1)

  def goOneStepLeftUp(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row + 1, coordinates.col - 1)

  def goOneStepLeftDown(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row - 1, coordinates.col - 1)

  def goOneStepRightUp(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row + 1, coordinates.col + 1)

  def goOneStepRightDown(coordinates: Coordinates): Coordinates = Coordinates(coordinates.row - 1, coordinates.col + 1)

  def twoStepsUp(coordinates: Coordinates): Coordinates = goOneStepUp(goOneStepUp(coordinates))

  def twoStepsDown(coordinates: Coordinates): Coordinates = goOneStepDown(goOneStepDown(coordinates))

  def twoStepsRight(coordinates: Coordinates): Coordinates = goOneStepRight(goOneStepRight(coordinates))

  def twoStepsLeft(coordinates: Coordinates): Coordinates = goOneStepLeft(goOneStepLeft(coordinates))

  def oneStepCross(coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(
    Vector(Vector(goOneStepUp(coordinates)), Vector(goOneStepDown(coordinates)), Vector(goOneStepRight(coordinates)),
      Vector(goOneStepLeft(coordinates))))

  def oneStepDiagonal(coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(
    Vector(Vector(goOneStepLeftUp(coordinates)), Vector(goOneStepLeftDown(coordinates)),
      Vector(goOneStepRightUp(coordinates)), Vector(goOneStepRightDown(coordinates))))

  def goOneStepInAllDirections(coordinates: Coordinates): Vector[Vector[Coordinates]] =
    oneStepCross(coordinates) ++ oneStepDiagonal(coordinates)

  def goKnightJump(coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    val twoUp = twoStepsUp(coordinates)
    val twoDown = twoStepsDown(coordinates)
    val twoLeft = twoStepsLeft(coordinates)
    val twoRight = twoStepsRight(coordinates)
    val multivectorlist1 = Vector(Vector(goOneStepRight(twoUp)), Vector(goOneStepRight(twoDown)),
      Vector(goOneStepLeft(twoUp)), Vector(goOneStepLeft(twoDown)))
    val multivectorlist2 = Vector(Vector(goOneStepUp(twoRight)), Vector(goOneStepUp(twoLeft)),
      Vector(goOneStepDown(twoRight)), Vector(goOneStepDown(twoLeft)))
    removeInvalidsFromMultiVector(multivectorlist1 ++ multivectorlist2)
  }

  def goMultiStepsDiagonal(coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    val lu1 = goOneStepLeftUp(coordinates)
    val lu2 = goOneStepLeftUp(lu1)
    val lu3 = goOneStepLeftUp(lu2)
    val lu4 = goOneStepLeftUp(lu3)
    val lu5 = goOneStepLeftUp(lu4)
    val lu6 = goOneStepLeftUp(lu5)
    val lu7 = goOneStepLeftUp(lu6)
    val lu8 = goOneStepLeftUp(lu7)
    val listlu1 = Vector(Vector(lu1), Vector(lu2), Vector(lu3), Vector(lu4))
    val listlu2 = Vector(Vector(lu5), Vector(lu6), Vector(lu7), Vector(lu8))

    val ru1 = goOneStepRightUp(coordinates)
    val ru2 = goOneStepRightUp(ru1)
    val ru3 = goOneStepRightUp(ru2)
    val ru4 = goOneStepRightUp(ru3)
    val ru5 = goOneStepRightUp(ru4)
    val ru6 = goOneStepRightUp(ru5)
    val ru7 = goOneStepRightUp(ru6)
    val ru8 = goOneStepRightUp(ru7)
    val listru1 = Vector(Vector(ru1), Vector(ru2), Vector(ru3), Vector(ru4))
    val listru2 = Vector(Vector(ru5), Vector(ru6), Vector(ru7), Vector(ru8))

    val rd1 = goOneStepRightDown(coordinates)
    val rd2 = goOneStepRightDown(rd1)
    val rd3 = goOneStepRightDown(rd2)
    val rd4 = goOneStepRightDown(rd3)
    val rd5 = goOneStepRightDown(rd4)
    val rd6 = goOneStepRightDown(rd5)
    val rd7 = goOneStepRightDown(rd6)
    val rd8 = goOneStepRightDown(rd7)
    val listrd1 = Vector(Vector(rd1), Vector(rd2), Vector(rd3), Vector(rd4))
    val listrd2 = Vector(Vector(rd5), Vector(rd6), Vector(rd7), Vector(rd8))

    val ld1 = goOneStepLeftDown(coordinates)
    val ld2 = goOneStepLeftDown(ld1)
    val ld3 = goOneStepLeftDown(ld2)
    val ld4 = goOneStepLeftDown(ld3)
    val ld5 = goOneStepLeftDown(ld4)
    val ld6 = goOneStepLeftDown(ld5)
    val ld7 = goOneStepLeftDown(ld6)
    val ld8 = goOneStepLeftDown(ld7)
    val listld1 = Vector(Vector(ld1), Vector(ld2), Vector(ld3), Vector(ld4))
    val listld2 = Vector(Vector(ld5), Vector(ld6), Vector(ld7), Vector(ld8))

    removeInvalidsFromMultiVector(listld1 ++ listld2 ++ listrd1 ++ listrd2 ++ listru1 ++ listru2 ++ listlu1 ++ listlu2)
  }

  def goMultiStepsCross(coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    val l1 = goOneStepLeft(coordinates)
    val l2 = goOneStepLeft(l1)
    val l3 = goOneStepLeft(l2)
    val l4 = goOneStepLeft(l3)
    val l5 = goOneStepLeft(l4)
    val l6 = goOneStepLeft(l5)
    val l7 = goOneStepLeft(l6)
    val l8 = goOneStepLeft(l7)
    val listl1 = Vector(Vector(l1), Vector(l2), Vector(l3), Vector(l4))
    val listl2 = Vector(Vector(l5), Vector(l6), Vector(l7), Vector(l8))

    val r1 = goOneStepRight(coordinates)
    val r2 = goOneStepRight(r1)
    val r3 = goOneStepRight(r2)
    val r4 = goOneStepRight(r3)
    val r5 = goOneStepRight(r4)
    val r6 = goOneStepRight(r5)
    val r7 = goOneStepRight(r6)
    val r8 = goOneStepRight(r7)
    val listr1 = Vector(Vector(r1), Vector(r2), Vector(r3), Vector(r4))
    val listr2 = Vector(Vector(r5), Vector(r6), Vector(r7), Vector(r8))

    val d1 = goOneStepDown(coordinates)
    val d2 = goOneStepDown(d1)
    val d3 = goOneStepDown(d2)
    val d4 = goOneStepDown(d3)
    val d5 = goOneStepDown(d4)
    val d6 = goOneStepDown(d5)
    val d7 = goOneStepDown(d6)
    val d8 = goOneStepDown(d7)
    val listd1 = Vector(Vector(d1), Vector(d2), Vector(d3), Vector(d4))
    val listd2 = Vector(Vector(d5), Vector(d6), Vector(d7), Vector(d8))

    val u1 = goOneStepUp(coordinates)
    val u2 = goOneStepUp(u1)
    val u3 = goOneStepUp(u2)
    val u4 = goOneStepUp(u3)
    val u5 = goOneStepUp(u4)
    val u6 = goOneStepUp(u5)
    val u7 = goOneStepUp(u6)
    val u8 = goOneStepUp(u7)
    val listu1 = Vector(Vector(u1), Vector(u2), Vector(u3), Vector(u4))
    val listu2 = Vector(Vector(u5), Vector(u6), Vector(u7), Vector(u8))

    removeInvalidsFromMultiVector(listl1 ++ listl2 ++ listr1 ++ listr2 ++ listu1 ++ listu2 ++ listd1 ++ listd2)
  }

  def goMultiStepsInAllDirections(coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    val listdiagonal = goMultiStepsDiagonal(coordinates)
    val listcross = goMultiStepsCross(coordinates)
    val list = listcross ++ listdiagonal

    removeInvalidsFromMultiVector(list)
  }

  def goTwoStepsUpOrOneStepUp(coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    Vector(Vector(goOneStepUp(coordinates))) ++ Vector(Vector(twoStepsUp(coordinates)))
  }
}



object Colour extends Enumeration {
  type Colour = Value
  val Black, White = Value
}

case class Coordinates(x: Int, y: Int)

object Utils {
  def validCoordinate(coordinates: Coordinates): Boolean =
    isAValidValueInsideTheField(coordinates.x) && isAValidValueInsideTheField(coordinates.y)

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
      case vector =>
        val b = removeInvalidsFromVector(vector)
        if (b.size > 0) newMultiVector = newMultiVector :+ b
    }
    newMultiVector
  }

  def isAValidValueInsideTheField(value: Int): Boolean = value >= 0 && value < 8

  def goOneStepUp(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x, coordinates.y + 1)

  def goOneStepDown(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x, coordinates.y - 1)

  def goOneStepRight(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x + 1, coordinates.y)

  def goOneStepLeft(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x - 1, coordinates.y)

  def goOneStepLeftUp(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x - 1, coordinates.y + 1)

  def goOneStepLeftDown(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x - 1, coordinates.y - 1)

  def goOneStepRightUp(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x + 1, coordinates.y + 1)

  def goOneStepRightDown(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x + 1, coordinates.y - 1)

  def goTwoStepsUp(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x, coordinates.y + 2)

  def goTwoStepsDown(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x, coordinates.y - 2)

  def goTwoStepsRight(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x + 2, coordinates.y)

  def goTwoStepsLeft(coordinates: Coordinates): Coordinates = Coordinates(coordinates.x - 2, coordinates.y)

  def oneStepCross(coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(
    Vector(Vector(goOneStepUp(coordinates)), Vector(goOneStepDown(coordinates)), Vector(goOneStepRight(coordinates)),
      Vector(goOneStepLeft(coordinates))))

  def oneStepDiagonal(coordinates: Coordinates): Vector[Vector[Coordinates]] = removeInvalidsFromMultiVector(
    Vector(Vector(goOneStepLeftUp(coordinates)), Vector(goOneStepLeftDown(coordinates)),
      Vector(goOneStepRightUp(coordinates)), Vector(goOneStepRightDown(coordinates))))

  def goOnStepInAllDirections(coordinates: Coordinates): Vector[Vector[Coordinates]] =
    oneStepCross(coordinates) ++ oneStepDiagonal(coordinates)

  def horseJump(coordinates: Coordinates): Vector[Vector[Coordinates]] = {
    val twoUp = goTwoStepsUp(coordinates)
    val twoDown = goTwoStepsDown(coordinates)
    val twoLeft = goTwoStepsUp(coordinates)
    val twoRight = goTwoStepsDown(coordinates)
    val l1 = removeInvalidsFromMultiVector(Vector(Vector(goOneStepRight(twoUp)) ++ Vector(goOneStepRight(twoDown))))
    val l2 = removeInvalidsFromMultiVector(Vector(Vector(goOneStepLeft(twoUp)) ++ Vector(goOneStepLeft(twoDown))))
    val l3 = removeInvalidsFromMultiVector(Vector(Vector(goOneStepUp(twoRight)) ++ Vector(goOneStepUp(twoLeft))))
    val l4 = removeInvalidsFromMultiVector(Vector(Vector(goOneStepDown(twoRight)) ++ Vector(goOneStepDown(twoLeft))))
    l1 ++ l2 ++ l3 ++ l4 //concatenate l1 and l2 and l3 and l4
  }

}

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
Utils.goTwoStepsUp(Coordinates(0, 1)) == Coordinates(0, 3)
Utils.goTwoStepsDown(Coordinates(0, 2)) == Coordinates(0, 0)
Utils.goTwoStepsRight(Coordinates(1, 1)) == Coordinates(3, 1)
Utils.goTwoStepsLeft(Coordinates(2, 1)) == Coordinates(0, 1)
Utils.oneStepCross(Coordinates(1, 1)).size == 4
Utils.oneStepCross(Coordinates(0, 0)).size == 2
Utils.oneStepDiagonal(Coordinates(1, 1)).size == 4
Utils.oneStepDiagonal(Coordinates(0, 0)).size == 1
Utils.goOnStepInAllDirections(Coordinates(0, 4)).size == 5
Utils.horseJump(Coordinates(3, 0)).contains(Vector(Coordinates(4,2)))
Utils.horseJump(Coordinates(3, 0)).contains(Vector(Coordinates(5,1)))
Utils.horseJump(Coordinates(3, 0)).contains(Vector(Coordinates(1,1)))
Utils.horseJump(Coordinates(3, 0)).contains(Vector(Coordinates(2,2)))


import Colour.Colour

trait Figure {
  val colour: Colour
  val coordinates: Coordinates

  def getName: String

  def getPossibleNewPositions(): Vector[Vector[Coordinates]]

  def hasAbility: Boolean

}

object Figure {
  val ROW_WHITE = 7
  val ROW_BLACK = 0
}

import Utils._


case class King(colour: Colour, coordinates: Coordinates) extends Figure {
  def this(colour: Colour) = this(colour, {
    import King._
    if (colour == Colour.Black) COORDINATES_BLACK else COORDINATES_WHITE
  })

  override def getName: String = "King"

  override def getPossibleNewPositions(): Vector[Vector[Coordinates]] = goOnStepInAllDirections(coordinates)

  override def hasAbility: Boolean = true

}

object King {

  import Figure._

  val COL_FIGURE = 4
  val COORDINATES_BLACK: Coordinates = Coordinates(ROW_BLACK, COL_FIGURE)
  val COORDINATES_WHITE: Coordinates = Coordinates(ROW_WHITE, COL_FIGURE)
}


val king1 = new King(Colour.Black)
king1.getName == "King"
king1.getPossibleNewPositions.size == 5
king1.hasAbility

case class Cell(colour: Colour, contains: Option[Figure])


val cell1 = Cell(Colour.Black, Option.empty)

cell1.colour == Colour.Black
cell1.contains == None

case class Matrix[T](rows: Vector[Vector[Cell]]) {
  val size = rows.size

  def this() = this({
    import Matrix._
    import King._
    import Figure._
    Vector.tabulate(SIZE_CHESSFIELD, SIZE_CHESSFIELD) { (row, col) => {
      val a = (row, col) match {
        case (COL_FIGURE, ROW_BLACK) => Option.apply(new King(Colour.Black))
        case _ => Option.empty
      }
      if (row % 2 == 0)
        if (col % 2 == 0) Cell(Colour.Black, Option.empty) else Cell(Colour.White, Option.empty)
      else if (col % 2 == 0) Cell(Colour.White, Option.empty) else Cell(Colour.Black, Option.empty)
    }
    }
  })
}

object Matrix {
  val SIZE_CHESSFIELD = 8
}


val matrix1 = new Matrix[Cell]

matrix1.rows(0)(0).colour == Colour.Black
matrix1.rows(0)(1).colour == Colour.White
matrix1.rows(7)(7).colour == Colour.Black
matrix1.rows(7)(6).colour == Colour.White
matrix1.size == 8


case class Field(cells: Matrix[Cell])

val field1 = Field(matrix1)

field1.cells.size == 8



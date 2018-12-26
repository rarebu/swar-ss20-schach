

object Colour extends Enumeration {
  type Colour = Value
  val Black, White = Value
}

trait Walker {
  def possibleCoordinates:(Int, Int)
}

import Colour.Colour

trait Figure {
  def getName:String
  def getPossibleDirections:Walker
  def hasAbility:Boolean
}

case class King() extends Figure {
  case class SimpleWalker() extends Walker {
    override def possibleCoordinates = (1, 1)
  }

  override def getName = "King"

  override def getPossibleDirections = SimpleWalker()

  override def hasAbility = true
}

val king1 = King()
king1.getName
king1.getPossibleDirections.possibleCoordinates
king1.hasAbility

case class Cell(colour:Colour, contains:Figure, coordinates:(Int, Int))


val cell1 = Cell(Colour.Black, null, (2, 3))

cell1.colour
cell1.contains
cell1.coordinates

case class Matrix[T] (rows:Vector[Vector[T]])

val matrix1 = Matrix(Vector(Vector(cell1)))

matrix1.rows

case class Field(cells: Matrix[Cell])

val field1 = Field(matrix1)

field1.cells



object Colour extends Enumeration {
  type Colour = Value
  val Black, White = Value
}

case class Coordinates(x:Int, y:Int)

import Colour.Colour

trait Figure {
  def getName:String
  def getPossibleNewPositions:List[Coordinates]
  def hasAbility:Boolean
}

case class King() extends Figure {
  override def getName = "King"

  override def getPossibleNewPositions = List(Coordinates(1, 1))

  override def hasAbility = true
}

val king1 = King()
king1.getName
king1.getPossibleNewPositions
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

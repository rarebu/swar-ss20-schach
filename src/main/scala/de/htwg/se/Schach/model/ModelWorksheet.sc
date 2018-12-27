

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

case class Cell(colour:Colour, contains:Figure)


val cell1 = Cell(Colour.Black, null)

cell1.colour
cell1.contains

case class Matrix[T] (rows:Vector[Vector[Cell]]) {
  def this() = this(Vector.tabulate(8, 8){(row, col) => {
    if (row % 2 == 0) {
      if (col % 2 == 0)
        Cell(Colour.Black, null)
      else
        Cell(Colour.White, null)
    } else {
      if (col % 2 == 0)
        Cell(Colour.White, null)
      else
        Cell(Colour.Black, null)
    }
  }})
  val size = rows.size
}

val matrix1 = new Matrix[Cell]

matrix1.rows(0)(0).colour == Colour.Black
matrix1.rows(0)(1).colour == Colour.White
matrix1.rows(7)(7).colour == Colour.Black
matrix1.rows(7)(6).colour == Colour.White
matrix1.size


case class Field(cells: Matrix[Cell])

val field1 = Field(matrix1)

field1.cells

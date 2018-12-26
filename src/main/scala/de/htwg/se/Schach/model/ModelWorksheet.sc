

object Colour extends Enumeration {
  type Colour = Value
  val Black, White = Value
}

object Direction extends Enumeration {
  type Direction = Value
  val Up, Down, Left, Right, UpLeft, UpRight, DownLeft, DownRight = Value
}

import Colour.Colour
import Direction.Direction

case class Figure(name:String, ability:Boolean, direction:Direction)

val fig1 = Figure("King", true, Direction.Down)
fig1.name
fig1.ability
fig1.direction

case class Cell(colour:Colour, contains:Figure, coordinates:(Int, Int))

//case class A()

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

package de.htwg.se.Schach.model

import Matrix._

case class Matrix[T](rows: Vector[Vector[T]]) {
  def this(func: (Int, Int) => T) = this({
    Vector.tabulate(SIZE_CHESSFIELD, SIZE_CHESSFIELD) { (row, col) => func(row, col)
    }
  })

  val size: Int = rows.size

  def cell(row: Int, col: Int): T = rows(row)(col)

  def replaceCell(row: Int, col: Int, cell: T): Matrix[T] = copy(rows.updated(row, rows(row).updated(col, cell)))
}

object Matrix {
  val SIZE_CHESSFIELD = 8
}

package de.htwg.se.Schach.model


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

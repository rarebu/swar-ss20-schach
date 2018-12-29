package de.htwg.se.Schach.model

class MatrixSpec {
  val matrix1 = new Matrix[Cell]

  matrix1.rows(0)(0).colour == Colour.Black
  matrix1.rows(0)(1).colour == Colour.White
  matrix1.rows(7)(7).colour == Colour.Black
  matrix1.rows(7)(6).colour == Colour.White
  matrix1.size == 8
}

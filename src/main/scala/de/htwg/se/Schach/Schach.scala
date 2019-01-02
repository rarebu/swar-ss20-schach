package de.htwg.se.Schach

import de.htwg.se.Schach.model.{Cell, Field, Matrix, Player}

import scala.io.StdIn.readLine

object Schach {
//  var field = Field
  //  val tui = new Tui
  val black = "##"
  val white = "⁕⁕"
  def getEmptyBlackCell():String = "♦##♦"
  def getEmptyWhiteCell():String = "♦⁕⁕♦"
  def getInHouse(vector: Vector[Cell]): String = {
//    var x
//    var index = 0
//    val count = 8
//    val a = for ( index <- 0 to count ) {
//      print(index)
//    }
    for ( xk <- vector) {
      xk.toString
    }
    ""
  }
  def replacer(row:Int, col:Int): String = {
    if (row % 2 == 0) if (col % 2 == 0) return getEmptyBlackCell() else return getEmptyWhiteCell()
    else if (col % 2 == 0) return getEmptyWhiteCell() else return getEmptyBlackCell()

  }
  def getHouse(): String = {
  val SIZE = 8
    val barrier = "|"+ "♦––♦|" * SIZE + "\n"
    val line = "|" + "X|" * SIZE + "\n"
    var box = barrier + (line + barrier) * 8
    for {
      row <- 0 until SIZE
      col <- 0 until SIZE
    } box = box.replaceFirst("X", replacer(row, col))
//    val field = new Field(new Matrix)
//    val a = getInHouse(new Matrix)
//      getEmptyBlackCell() + getEmptyWhiteCell() + getEmptyBlackCell() + getEmptyWhiteCell() + getEmptyBlackCell() + getEmptyWhiteCell() + getEmptyBlackCell() + getEmptyWhiteCell() + "\n"
    box
  }
  def main(args: Array[String]): Unit = {
    val student = Player("Your Name")
    println("Hello, " + student.name)
    val rook = "♜"
    println()
    println(getHouse())
//    println()
//    println("Filled cell: " + "⁕" + rook + "⁕")
//    println("Empty cell: " + "♦⁕⁕♦")
    println()
    println("|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|⁕♜⁕|#♞#|⁕♝⁕|#♛#|⁕♚⁕|#♝#|⁕♞⁕|#♜#|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|#♟#|⁕♟⁕|#♟#|⁕♟⁕|#♟#|⁕♟⁕|#♟#|⁕♟⁕|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|⁕♙⁕|#♙#|⁕♙⁕|#♙#|⁕♙⁕|#♙#|⁕♙⁕|#♙#|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|#♖#|⁕♘⁕|#♗#|⁕♕⁕|#♔#|⁕♗⁕|#♘#|⁕♖⁕|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|")
    var input: String = ""

//    do {
//      println("Grid : " + field.toString)
//      input = readLine()
//      field = tui.processInputLine(input, field)
//    } while (input != "q")
  }
}

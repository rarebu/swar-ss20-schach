package de.htwg.se.Schach

import de.htwg.se.Schach.model.{Field, Player}

import scala.io.StdIn.readLine

object Schach {
  def main(args: Array[String]): Unit = {
    val student = Player("Your Name")
    println("Hello, " + student.name)
    val field = new Field()
    println()
    println("Field:\n" + field.toString)
    println()
    var input: String = ""

    //    do {
    //      println("Grid : " + field.toString)
    //      input = readLine()
    //      field = tui.processInputLine(input, field)
    //    } while (input != "q")
  }
}

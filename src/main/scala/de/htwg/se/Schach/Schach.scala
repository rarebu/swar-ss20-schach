package de.htwg.se.Schach

import de.htwg.se.Schach.model.Player

object Schach {
  def main(args: Array[String]): Unit = {
    val student = Player("Your Name")
    println("Hello, " + student.name)
  }
}

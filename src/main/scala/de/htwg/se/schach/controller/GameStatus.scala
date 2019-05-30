package de.htwg.se.schach.controller

object GameStatus extends Enumeration {
  type GameStatus = Value
  val IDLE, NEW, MOVE, CHOOSE, UNDO, REDO, BLACK_WON, WHITE_WON = Value

  val map = Map[GameStatus, String](
    IDLE -> "",
    NEW -> "A new game was created",
    MOVE -> "A figure moved",
    CHOOSE -> "A figure was choosed",
    UNDO -> "Undone one step",
    REDO -> "Redone one step",
    BLACK_WON -> "Black won",
    WHITE_WON -> "White won")

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }
}

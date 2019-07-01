package de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl

object GameStatus extends Enumeration {
  type GameStatus = Value
  val IDLE, NEW, MOVE, CHOOSE, UNDO, REDO, BLACK_WON, WHITE_WON, LOADED, SAVED = Value

  val map = Map[GameStatus, String](
    IDLE -> "",
    NEW -> "A new game was created",
    MOVE -> "A figure moved",
    CHOOSE -> "A figure was choosed",
    UNDO -> "Undone one step",
    REDO -> "Redone one step",
    BLACK_WON -> "Black won",
    WHITE_WON -> "White won",
    LOADED -> "A new Game was loaded",
    SAVED ->  "The Game was saved")

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }
}

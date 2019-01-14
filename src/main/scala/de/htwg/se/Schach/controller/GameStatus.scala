package de.htwg.se.Schach.controller

object GameStatus extends Enumeration{
  type GameStatus = Value
  val IDLE, BLACK_WON, WHITE_WON = Value

  val map = Map[GameStatus, String](
    IDLE -> "",
    BLACK_WON ->"Black wins",
    WHITE_WON ->"White wins")

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }

}

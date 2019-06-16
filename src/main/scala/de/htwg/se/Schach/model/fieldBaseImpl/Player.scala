package de.htwg.se.Schach.model.fieldBaseImpl

private[model] case class Player(name: String) {
  override def toString: String = name
}

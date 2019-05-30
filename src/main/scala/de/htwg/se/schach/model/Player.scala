package de.htwg.se.schach.model

private[model] case class Player(name: String) {
  override def toString: String = name
}

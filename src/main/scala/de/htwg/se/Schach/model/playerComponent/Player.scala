package de.htwg.se.Schach.model.playerComponent

private[model] case class Player(name: String) {
  override def toString: String = name
}

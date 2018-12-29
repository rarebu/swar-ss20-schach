package de.htwg.se.Schach.model

class KingSpec {
  val king1 = new King(Colour.Black)
  king1.getName == "King"
  king1.getPossibleNewPositions.size == 5
  king1.hasAbility
}

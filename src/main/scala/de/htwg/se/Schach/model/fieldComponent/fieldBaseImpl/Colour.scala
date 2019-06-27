package de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl

object Colour extends Enumeration {

  type Colour = Value
  val black, white = Value

  def getBlackColour(isBlack:Boolean) = if(isBlack) Colour.black else Colour.white
}

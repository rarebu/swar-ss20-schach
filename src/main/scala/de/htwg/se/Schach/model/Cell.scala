package de.htwg.se.Schach.model

import _root_.de.htwg.se.Schach.model.Colour.Colour

case class Cell(colour: Colour, contains: Option[Figure])

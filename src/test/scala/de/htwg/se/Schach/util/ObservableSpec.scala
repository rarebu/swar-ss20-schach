package de.htwg.se.Schach.util

import de.htwg.se.Schach.model.{Bishop, Colour, Coordinates, Field}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class ObservableSpec extends WordSpec with Matchers {
  "The Observable object" when {
    "new" should {
      val observable = new Observable
      "have a Vector as subscriber" in {
        observable.subscribers should be(Vector())
      }
    }
  }
}

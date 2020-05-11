package de.htwg.se.Schach.util

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class UtilsSpec extends WordSpec with Matchers {
  "The Utils object" when {
    "used" should {
      "be even" in {
        Utils.isEven(2)
      }
    }
  }
}

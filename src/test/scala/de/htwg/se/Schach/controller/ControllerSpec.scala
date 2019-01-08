package de.htwg.se.Schach.controller

import de.htwg.se.Schach.model.Field
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {
  "The TUI" when {
    "new" should {
      val field = new Field
      val controller = new Controller(field)
      "process a 'q" in {
        controller.newField().toString should be("()")
      }
      "process a 'qf" in {
        controller.move(1,2,3,4) should be()
      }
    }
  }
}

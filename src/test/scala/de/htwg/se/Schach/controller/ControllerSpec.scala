package de.htwg.se.Schach.controller

import de.htwg.se.Schach.model.Field
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {
  "A Controller" when {
    "empty" should {
      val field = new Field
      val controller = new Controller(field)
      "handle" in {
        controller.field.cells.size should be(8)
        controller.move(0,0,1,1) should be()
        controller.newField() should be()
      }
    }
  }
}

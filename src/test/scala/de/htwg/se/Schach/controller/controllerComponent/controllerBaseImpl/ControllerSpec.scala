package de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends WordSpec with Matchers {
  "A Controller" when {
    val field = new Field
    val controller = new Controller(field)
    "empty" should {
      "moving on and handle undo/redo of a field correctly, choose should fail with a and some stuff" in {
        controller.cellContains(0, 0) should be("♜")
        controller.cellIsBlack(0, 0) should be(false)
        controller.choose("a") should be()
        controller.fieldToString should be("Wrong Input!")
        controller.getChangeableFigures should be("♛♜♝♞♕♖♗♘")
        controller.move(1, 0, 3, 0) should be()
        controller.statusText should be("A figure moved")
        controller.undo should be()
        controller.redo should be()
        controller.newField should be()
      }
    }
  }
}

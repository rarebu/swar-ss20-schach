package de.htwg.se.Schach.aview

import de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl.LogicController
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class TUISpec extends WordSpec with Matchers {
  "The TUI" when {
    "new" should {
      val field = new Field
      val controller = new LogicController(field)
      val tui = new TUI(controller)
      "do nothing on input 'q" in {
        tui.processInputLine("q")
      }
      "create a new board on input 'n'" in {
        tui.processInputLine("n")
        controller.field should be(new Field)
      }
      "move on input '1030'" in {
        tui.processInputLine("1030")
        controller.field.cellContains(3, 0).get should be("♟")
      }
      "undo on input 'z" in {
        tui.processInputLine("z")
        controller.field.cellContains(1, 0).get should be("♟")
      }
      "redo on input 'y" in {
        tui.processInputLine("y")
        controller.field.cellContains(3, 0).get should be("♟")
      }
      "do nothing on bad input like '9999999'" in {
        val old = controller.fieldToString
        tui.processInputLine("9999999")
        controller.fieldToString should be(old)
      }
    }
  }
}

package de.htwg.se.schach.aview

import de.htwg.se.schach.controller.Controller
import de.htwg.se.schach.model.Field
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class TUISpec extends WordSpec with Matchers {
  "The TUI" when {
    "new" should {
      val field = new Field
      val controller = new Controller(field)
      val tui = new TUI(controller)
      "do nothing on input 'q" in {
        tui.processInputLine("q")
      }
      "create a new board on input 'n'" in {
        tui.processInputLine("n")
        controller.field should be(new Field())
      }
      "move on input '1030'" in {
        tui.processInputLine("1030")
        controller.field.cell(3, 0).toString should be("#♟#")
      }
      "undo on input 'z" in {
        tui.processInputLine("z")
        controller.field.cell(1, 0).toString should be("#♟#")
      }
      "redo on input 'y" in {
        tui.processInputLine("y")
        controller.field.cell(3, 0).toString should be("#♟#")
      }
      "do nothing on bad input like '9999999'" in {
        val old = controller.fieldToString
        tui.processInputLine("9999999")
        controller.fieldToString should be(old)
      }
    }
  }
}
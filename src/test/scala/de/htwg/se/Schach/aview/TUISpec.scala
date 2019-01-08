package de.htwg.se.Schach.aview

import de.htwg.se.Schach.controller.Controller
import de.htwg.se.Schach.model.Field
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
      "process a 'q" in {
        tui.processInputLine("q").toString should be("()")
      }
      "process a 'n'" in {
        tui.processInputLine("n").toString should be("()")
      }
    }
  }
}

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
      "have a field of size 8" in {
        controller.field.cells.size should be(8)
      }
      "have a field with string representation in each cell" in {
        controller.fieldToString should be("|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|⁕♜⁕|#♞#|⁕♝⁕|#♛#|⁕♚⁕|#♝#|⁕♞⁕|#♜#|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|#♟#|⁕♟⁕|#♟#|⁕♟⁕|#♟#|⁕♟⁕|#♟#|⁕♟⁕|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|♦##♦|♦⁕⁕♦|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|⁕♙⁕|#♙#|⁕♙⁕|#♙#|⁕♙⁕|#♙#|⁕♙⁕|#♙#|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n|#♖#|⁕♘⁕|#♗#|⁕♕⁕|#♔#|⁕♗⁕|#♘#|⁕♖⁕|\n|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|♦––♦|\n")
      }
      //todo
    }
  }
}

package de.htwg.se.schach.util

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

class incrCommand extends Command {
  var state: Int = 0

  override def doStep: Boolean = {
    state += 1; true
  }

  override def undoStep: Unit = state -= 1

  override def redoStep: Unit = state += 1
}


@RunWith(classOf[JUnitRunner])
class CommandSpec extends WordSpec with Matchers {
  "A Command" should {

    "have a do step" in {
      val command = new incrCommand
      command.state should be(0)
      command.doStep
      command.state should be(1)
      command.doStep
      command.state should be(2)

    }
    "have an undo step" in {
      val command = new incrCommand
      command.state should be(0)
      command.doStep
      command.state should be(1)
      command.undoStep
      command.state should be(0)
    }
    "have a redo step" in {
      val command = new incrCommand
      command.state should be(0)
      command.doStep
      command.state should be(1)
      command.undoStep
      command.state should be(0)
      command.redoStep
      command.state should be(1)
    }
  }
}

class UndoManagerSpec extends WordSpec with Matchers {

  "An UndoManager" should {
    val undoManager = new UndoManager
    val command = new incrCommand
    "have a push" in {

      command.state should be(0)
      undoManager.doStep(command)
      command.state should be(1)
      undoManager.undoStep
      command.state should be(0)
      undoManager.redoStep
      command.state should be(1)
    }
  }
}

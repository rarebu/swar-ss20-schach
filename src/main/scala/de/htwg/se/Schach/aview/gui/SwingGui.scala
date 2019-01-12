package de.htwg.se.Schach.aview.gui

import de.htwg.se.Schach.controller.Controller
import de.htwg.se.Schach.util.Observer
import javax.swing. WindowConstants
import scala.swing.Swing.LineBorder
import scala.swing._
import scala.swing.event._


class SwingGui(controller: Controller) extends Frame with Observer {
  peer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  title = "HTWG Schach"

  def gridPanel: GridPanel = new GridPanel(8, 8) {
    border = LineBorder(java.awt.Color.BLACK, 2)
    background = java.awt.Color.BLACK
    for {
      outerRow <- 0 until 8
      outerColumn <- 0 until 8
    } {
      contents += Button("X") { println("Thank you") }
    }
  }

  val statusline = new TextField("status textfield")

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") {
        println("new")
      })
      contents += new MenuItem(Action("Quit") {
        sys.exit(0)
      })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") {
        println("undo")
      })
    }
  }

  visible = true

  override def update(): Boolean = true

}

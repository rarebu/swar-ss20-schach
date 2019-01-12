package de.htwg.se.Schach.aview.gui

import java.awt.GridLayout
import java.awt.event.WindowEvent

import com.sun.corba.se.spi.orbutil.threadpool.Work
import de.htwg.se.Schach.controller.Controller
import de.htwg.se.Schach.model.Colour
import de.htwg.se.Schach.util.Observer
import javax.swing.{JFrame, WindowConstants}

import scala.swing.Swing.LineBorder
import scala.swing._
import scala.swing.event._
import javax.swing.JTextArea
import javax.swing.JScrollPane
import java.awt.BorderLayout

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
      contents += new TextField("X")
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
        println("quit")
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

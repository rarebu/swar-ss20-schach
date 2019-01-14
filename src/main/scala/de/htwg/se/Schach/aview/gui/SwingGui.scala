package de.htwg.se.Schach.aview.gui

import de.htwg.se.Schach.controller.{CellChanged, ChooseFigure, Controller}
import de.htwg.se.Schach.util.Observer
import javax.swing.{JOptionPane, WindowConstants}

import scala.swing.Swing.LineBorder
import scala.swing._
import scala.swing.event._

class CellClicked(val row: Int, val column: Int) extends Event

class SwingGui(controller: Controller) extends Frame with Observer {
  peer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  title = "HTWG Schach"

  var cells = Array.ofDim[CellPanel](8, 8)

  def gridPanel: GridPanel = new GridPanel(8, 8) {
    border = LineBorder(java.awt.Color.BLACK, 2)
    for {
      outerRow <- 0 until 8
      outerColumn <- 0 until 8
    } {
      val row = outerRow
      val col = outerColumn
      val cellPanel = new CellPanel(row, col, controller)
      cells(row)(col) = cellPanel
      contents += cellPanel
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
        controller.newField
      })
      contents += new MenuItem(Action("Quit") {
        sys.exit(0)
      })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") {
        controller.undo
      })
      contents += new MenuItem(Action("Redo") {
        controller.redo
      })
    }
  }

  reactions += {
    case event: CellChanged => gridPanel
    case choose: ChooseFigure => {
      println("hello")
    }
  }

  visible = true

  override def update(): Boolean = true
}

package de.htwg.se.Schach.aview.gui

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.Schach.controller._
import scala.io.Source._

class CellClicked(val row: Int, val column: Int) extends Event

class SwingGui(controller: Controller) extends Frame {


  title = "HTWG Sudoku"
  var cells = Array.ofDim[CellPanel](controller.field.cells.size, controller.field.cells.size)

  /*def highlightpanel = new FlowPanel {
    contents += new Label("Highlight:")
      val button = Button(button.preferredSize_=(new Dimension(30, 30))
      contents += button
      listenTo(button)
  }*/

  def gridPanel = new GridPanel(controller.field.cells.size, controller.field.cells.size) {
    border = LineBorder(java.awt.Color.BLACK, 2)
    background = java.awt.Color.BLACK
    for {
      outerRow <- 0 until controller.field.cells.size
      outerColumn <- 0 until controller.field.cells.size
    } {
      contents += new GridPanel(controller.field.cells.size, controller.field.cells.size) {
        border = LineBorder(java.awt.Color.BLACK, 2)
        for {
          innerRow <- 0 until controller.field.cells.size
          innerColumn <- 0 until controller.field.cells.size
        } {
          val x = outerRow * controller.field.cells.size + innerRow
          val y = outerColumn * controller.field.cells.size + innerColumn
          val cellPanel = new CellPanel(x, y, controller)
          cells(x)(y) = cellPanel
          contents += cellPanel
          listenTo(cellPanel)
        }
      }
    }
  }
  val statusline = new TextField(controller.field.toString +  20)

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
    add(statusline, BorderPanel.Position.South)
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("Quit") { System.exit(0) })
    }
    contents += new Menu("Options") {
      mnemonic = Key.O
      contents += new MenuItem(Action("Show all candidates") {  })
    }
  }

  visible = true


  /* reactions += {
    case event: GridSizeChanged => resize(event.newSize)
    case event:      => redraw
    case event: CandidatesChanged => redraw
  }*/


}
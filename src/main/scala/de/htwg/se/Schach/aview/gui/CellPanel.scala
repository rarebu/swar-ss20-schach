package de.htwg.se.Schach.aview.gui

import de.htwg.se.Schach.controller.{CellChanged, Controller}

import scala.swing._
import scala.swing.event._

class CellPanel(row: Int, column: Int, controller: Controller) extends FlowPanel {

  def myCell = controller.cell(row, column)

  def cellText(row: Int, col: Int) = controller.cellContains(row, col)

  val label =
    new Label {
      text = cellText(row, column)
      font = new Font("Verdana", 1, 36)
    }

  val cell = new BoxPanel(Orientation.Vertical) {
    contents += label
    preferredSize = new Dimension(75, 75)
    background = if (controller.cellIsBlack(row, column)) java.awt.Color.GRAY else java.awt.Color.WHITE
    border = Swing.BeveledBorder(Swing.Raised)
    listenTo(mouse.clicks)
    listenTo(controller)
    reactions += {
      case e: CellChanged => {
        label.text = cellText(row, column)
        repaint
      }
      case MouseClicked(src, pt, mod, clicks, pops) => {
        println("dfdfdf")
        repaint
      }
    }
  }

  /*val cell = new Button(controller.cellContains(row, column)) {
    background = if (controller.cellIsBlack(row, column)) java.awt.Color.GRAY else java.awt.Color.GRAY
    border = Swing.BeveledBorder(Swing.Raised)
    contents += label
  }*/

  def redraw = {
    contents.clear()
    label.text = cellText(row, column)
    contents += cell
    repaint
  }

  def setBackground(p: Panel) = p.background = if (controller.cellIsBlack(row, column)) java.awt.Color.GRAY else java.awt.Color.WHITE

}
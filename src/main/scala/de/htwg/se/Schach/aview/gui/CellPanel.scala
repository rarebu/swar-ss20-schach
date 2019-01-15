package de.htwg.se.Schach.aview.gui

import de.htwg.se.Schach.controller.{CellChanged, Controller}

import scala.swing.Swing.LineBorder
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

  val cell = new BoxPanel(Orientation.Horizontal) {
    contents += label
    preferredSize = new Dimension(75, 75)
    background = if (controller.cellIsBlack(row, column)) java.awt.Color.GRAY else java.awt.Color.WHITE
    border = LineBorder(java.awt.Color.BLACK, 2)
    border = Swing.BeveledBorder(Swing.Raised)
    listenTo(mouse.clicks)
    listenTo(controller)
    reactions += {
      case e: CellChanged => {
        label.text = cellText(row, column)
        repaint
      }
      case MouseClicked(src, pt, mod, clicks, pops) => {
        if (Storage.store.isDefined) {
          val tmp = Storage.store.get
          controller.move(tmp._1, tmp._2, row, column)
          Storage.store = None
        } else {
          Storage.store = Option.apply((row, column))
        }
        repaint
      }
    }
  }
  contents += cell

  def redraw = {
    contents.clear()
    label.text = cellText(row, column)
    contents += cell
    repaint
  }
}
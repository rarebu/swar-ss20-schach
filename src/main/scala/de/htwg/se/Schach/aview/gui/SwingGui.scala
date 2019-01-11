package de.htwg.se.Schach.aview.gui

import java.awt.event.WindowEvent

import de.htwg.se.Schach.controller.Controller
import de.htwg.se.Schach.util.Observer
import javax.swing.WindowConstants

import scala.swing.Swing.LineBorder
import scala.swing._
import scala.swing.event._

class SwingGui(controller: Controller) extends Frame with Observer {


  title = "HTWG Sudoku"


  peer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

  def highlightpanel = new FlowPanel {
    contents += new Label("Highlight:")
    for {index <- 0 to 20} {
      val button = Button(if (index == 0) "" else index.toString) {
        //
      }
      button.preferredSize_=(new Dimension(30, 30))
      resizable = false
      resizable = true
      contents += button
      listenTo(button)
    }
    resizable = false
    resizable = true
  }

  def gridPanel = new GridPanel(1, 15) {
    border = LineBorder(java.awt.Color.BLACK, 300)
    background = java.awt.Color.BLACK
    for {
      outerRow <- 0 until 5
      outerColumn <- 0 until 5
    } {
      contents += new GridPanel(5,15) {
        border = LineBorder(java.awt.Color.GREEN, 2)
        for {
          innerRow <- 0 until 15
          innerColumn <- 0 until 15
        } {
          val x = outerRow * 5 + innerRow
          val y = outerColumn * 5 + innerColumn
        }
      }
    }
  }


  contents = new BorderPanel {
    add(highlightpanel, BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
  }

  visible = true


override def update: Boolean = true

}

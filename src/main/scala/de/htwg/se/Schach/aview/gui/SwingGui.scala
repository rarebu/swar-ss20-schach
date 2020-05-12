package de.htwg.se.Schach.aview.gui

import de.htwg.se.Schach.controller.controllerComponent.controllerBaseImpl.{CellChanged, ControllerInterface}
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.Field
import javax.swing.{Icon, JOptionPane, UIManager, WindowConstants}

import scala.swing.Swing.{EmptyIcon, LineBorder}
import scala.swing._
import scala.swing.event._

class CellClicked(val row: Int, val column: Int) extends Event

class SwingGui(controller: ControllerInterface) extends Frame {
  listenTo(controller)
  peer.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  title = "HTWG Schach"

  var cells = Array.ofDim[CellPanel](Field.SIZE, Field.SIZE)

  def gridPanel: GridPanel = new GridPanel(Field.SIZE, Field.SIZE) {
    border = LineBorder(java.awt.Color.BLACK, 2)
    (0 until Field.SIZE).foreach(outerRow => (0 until Field.SIZE).foreach(outerColumn => {
      val row = outerRow
      val col = outerColumn
      val cellPanel = new CellPanel(row, col, controller)
      cells(row)(col) = cellPanel
      contents += cellPanel
    }
    ))
  }

  val statusline = new TextField("spawn", 20)

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
    case event: CellChanged => redraw
  }

  visible = true

  def redraw: Unit = {
    (0 until Field.SIZE).foreach(row => (0 until Field.SIZE).foreach(col => cells(row)(col).redraw))
    val tmp = controller.pawnPromoting
    if (tmp.isDefined) {
      controller.choose(chooseFigure(tmp.get)) //potential error
    }
    statusline.text = controller.statusText
    repaint
  }

  def chooseFigure(blackorwhite: String): String = {

    object Choices extends Enumeration {
      type Choice = Value
      val Queen, Rook, Bishop, Knight = Value
    }

    def showOptions[A <: Enumeration](
                                       parent: Option[Component] = None,
                                       message: Any,
                                       title: String = UIManager.getString("OptionPane.titleText"),
                                       messageType: Dialog.Message.Value = Dialog.Message.Question,
                                       icon: Icon = EmptyIcon,
                                       entries: A,
                                       initial: A#Value): Option[A#Value] = {
      val r = JOptionPane.showOptionDialog(
        if (parent.isEmpty) null else parent.get.peer, message, title, 0,
        messageType.id, Swing.wrapIcon(icon),
        entries.values.toArray[AnyRef], initial)
      if (r < 0) None else Some(entries(r))
    }

    val res = showOptions(message = "Choose a figure", entries = Choices, initial = Choices.Queen)
    blackorwhite match {
      case "♕♖♗♘" => res match {
        case Some(Choices.Queen) => "♕"
        case Some(Choices.Rook) => "♖"
        case Some(Choices.Bishop) => "♗"
        case Some(Choices.Knight) => "♘"
      }
      case "♛♜♝♞" => res match {
        case Some(Choices.Queen) => "♛"
        case Some(Choices.Rook) => "♜"
        case Some(Choices.Bishop) => "♝"
        case Some(Choices.Knight) => "♞"
      }
    }
  }
}

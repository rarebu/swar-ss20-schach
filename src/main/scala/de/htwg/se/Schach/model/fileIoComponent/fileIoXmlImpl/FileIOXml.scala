package de.htwg.se.Schach.model.fileIoComponent.fileIoXmlImpl


import de.htwg.se.Schach.model.fileIoComponent.FileIO
import de.htwg.se.Schach.model.{FieldInterface, FigureInterface}
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.{Cell, Colour, Field, Figure, Matrix, RemovedFigures}

import scala.xml.{NodeSeq, PrettyPrinter}
import scala.xml.XML

class FileIOXml(field:FieldInterface) extends FileIO {
  override def load: FieldInterface = {
//    val file = scala.xml.XML.loadFile("field.xml")
//
//    val figurePositions: List[(CoordinatesInterface, FigureInterface)] = null
//
//    val toChange: String = ""
//
//    val roundCount:Int = 0
//
//    val removedFigures: RemovedFiguresInterface = null
//
//    new Field(figurePositions, toChange, roundCount, removedFigures)
    null
  }

  override def save: Unit = saveString

  def saveXML:Unit = {
    scala.xml.XML.save("grid.xml", fieldToXml)
  }

  def saveString:Unit = {
    import java.io._
    val pw = new PrintWriter(new File("field.xml"))
    val prettyPrinter = new PrettyPrinter(120,4)
    val xml = prettyPrinter.format(fieldToXml)
    pw.write(xml)
    pw.close()
  }

  def fieldToXml = {
    <field>
      {for {
      row <- 0 until field.getSize
      col <- 0 until field.getSize
    } yield cellToXml(row, col)}
    </field>


    }

  def figurePositionsToXml = {

  }

  def toChangeToXml = {

  }

  def removedFiguresToXml = {

  }

  def cellToXml(row:Int, col:Int) = {
    <cell row={row.toString} col={col.toString}>
      {field.cellContains(row, col)}
    </cell>
  }
  def xmlToField = ???
  def xmlToCell = ???
}

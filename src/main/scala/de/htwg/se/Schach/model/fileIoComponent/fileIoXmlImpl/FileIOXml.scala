package de.htwg.se.Schach.model.fileIoComponent.fileIoXmlImpl


import de.htwg.se.Schach.model.fileIoComponent.FileIO
import de.htwg.se.Schach.model.{FieldDataInterface, FieldInterface, FigureInterface, RemovedFigureInterface, ToChangeInterface}
import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.{Cell, Colour, Field, Figure, Matrix, PersistFigure, PersistRemovedFigure, PersistToChange, RemovedFigures}

import scala.collection.mutable.ListBuffer
import scala.xml.{NodeSeq, PrettyPrinter}
import scala.xml.XML

class FileIOXml(field:FieldInterface) extends FileIO {
  override def load: FieldInterface = {
    val file = scala.xml.XML.loadFile("field.xml")
    val roundAttr = (file \\ "field" \ "@round")
    val round = roundAttr.text.toInt
    val figurePositionsNodes = (file \\ "field" \ "figurePositions" \"figure" )
    val figurePositions = loadFigurePositions(figurePositionsNodes)
    val optionToChangeNode = (file \\ "field" \ "option")
    val toChange = loadToChange(optionToChangeNode)
    val removedFiguresNodes = (file \\ "removedFigure")
    val removedFIgures = loadRemovedFigures(removedFiguresNodes)

    new Field(figurePositions, toChange, removedFIgures, round)

  }

  def loadFigurePositions(figurePositions:NodeSeq):List[FigureInterface] = {
    figurePositions.map(figure => {
      loadFigure(figure)
    }).toList
  }

  def loadFigure(figure:NodeSeq):FigureInterface = {
    val isBlack = (figure \ "@isBlack").text.toBoolean
    val row = (figure \ "@row").text.toInt
    val col = (figure \ "@col").text.toInt
    val stepCount = (figure \ "@stepCount").text.toInt
    val kind = figure.text.trim
    PersistFigure(isBlack, kind, stepCount, (row,col))
  }

  def loadRemovedFigures(removedFigures:NodeSeq):List[RemovedFigureInterface] = {
    removedFigures.map(removedFigure => {
      val round = (removedFigure \ "@round").text.toInt
      val figure = loadFigure((removedFigure \ "figure"))
      PersistRemovedFigure(figure, round)
    }).toList
  }

  def loadToChange(toChangeOption:NodeSeq):Option[ToChangeInterface] = {
    if ((toChangeOption \ "@isDefined").text.toBoolean) {
      val toChange = (toChangeOption \ "toChange")
      val isBlack = (toChange \ "@isBlack").text.toBoolean
      Option.apply(PersistToChange(loadFigure((toChange \ "figure")), isBlack))
    } else Option.empty
  }

  override def save(field: FieldInterface): Unit = saveString(field.getField)


  def saveXML(field: FieldDataInterface): Unit = {
    scala.xml.XML.save("grid.xml", fieldToXml(field))
  }

  def saveString(field: FieldDataInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("field.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(fieldToXml(field))
    pw.write(xml)
    pw.close()
  }

  def fieldToXml(field: FieldDataInterface) = {
    <field round={field.getRoundCount.toString}>
      { figurePositionsToXml(field.getFigurePositions) }
      { toChangeToXml(field.getToChange) }
      { removedFiguresToXml(field.getRemovedFigures) }

    </field>


  }

  def figurePositionsToXml(figurePositions: List[FigureInterface]) = {
    <figurePositions>
      { for(figure <- figurePositions) yield figureToXml(figure) }
    </figurePositions>
  }

  def figureToXml(figure: FigureInterface) = {
    val position = figure.getPosition
    <figure isBlack={figure.isBlack.toString} row={position._1.toString} col={position._2.toString} stepCount={figure.getStepCount.toString}>
      {figure.getKind}
    </figure>
  }

  def toChangeToXml(toChange: Option[ToChangeInterface]) = {
    if (toChange.isDefined) {
      val toChangeGet = toChange.get
      <option isDefined="true">
        <toChange isBlack={toChangeGet.isBlack.toString}>
          {figureToXml(toChangeGet.getFigure)}
        </toChange>
      </option>

    } else {
      <option isDefinded="false">
      </option>
    }
  }

  def removedFiguresToXml(removedFigures: List[RemovedFigureInterface]) = {
    <removedFigures>
      {for (removedFigure <- removedFigures) yield removedFigureToXml(removedFigure)}
    </removedFigures>
  }

  def removedFigureToXml(removedFigure: RemovedFigureInterface) = {
    <removedFigure round={removedFigure.getRound.toString}>
      {figureToXml(removedFigure.getFigure)}
    </removedFigure>
  }
}

package de.htwg.se.Schach.model.fileIoComponent.fileIoJsonImpl

import de.htwg.se.Schach.model.fieldComponent.fieldBaseImpl.{Field, PersistField, PersistFigure, PersistRemovedFigure, PersistToChange}
import de.htwg.se.Schach.model.{FieldDataInterface, FieldInterface, FigureInterface, RemovedFigureInterface, ToChangeInterface}
import de.htwg.se.Schach.model.fileIoComponent.FileIO
import play.api.libs.json.{JsLookupResult, JsObject, JsValue, Json, Writes}

import scala.io.Source

class FileIOJson extends FileIO {
  override def load: FieldInterface = {
    val source: String = Source.fromFile("field.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val field = (json \ "field")
    val round = (field \ "round").get.toString().toInt
    val figurePositions = loadFigurePositions((field \ "figurePositions"))
    val optionToChange = loadOptionToChange((field \ "option"))
    val removedFigures = loadRemovedFigures((field \ "removedFigures"))
    new Field(PersistField(figurePositions, optionToChange, removedFigures, round))
  }

  def loadFigure(figure: JsValue): FigureInterface = {
    val isBlack = (figure \ "isBlack").get.toString().toBoolean
    val kind = (figure \ "kind").get.toString().filterNot("\"".toSet)
    val stepCount = (figure \ "stepCount").get.toString().toInt
    val row = (figure \ "row").get.toString().toInt
    val col = (figure \ "col").get.toString().toInt
    PersistFigure(isBlack, kind, stepCount, (row, col))
  }

  def loadFigurePositions(figurePositions: JsLookupResult): List[FigureInterface] =
    figurePositions.get.as[List[JsValue]].map(figure => loadFigure(figure))


  def loadOptionToChange(optionToCHange: JsLookupResult): Option[ToChangeInterface] = {
    val isDefined = (optionToCHange \ "isDefined").get.toString().toBoolean
    if (isDefined) {
      val isBlack = (optionToCHange \ "isBlack").get.toString().toBoolean
      val figurE = (optionToCHange \ "figure").get
      val figure = loadFigure(figurE)
      Option.apply(PersistToChange(figure, isBlack))
    } else Option.empty
  }

  def loadRemovedFigures(removedFigures: JsLookupResult): List[RemovedFigureInterface] = {
    removedFigures.get.as[List[JsValue]].map(removedFigure => PersistRemovedFigure(loadFigure((removedFigure \ "figure").get),
      (removedFigure \ "round").get.toString().toInt))
  }

  override def save(field: FieldInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("field.json"))
    pw.write(Json.prettyPrint(fieldToJson(field.getField)))
    pw.close
  }

  implicit val figureWrites = new Writes[FigureInterface] {
    def writes(figure: FigureInterface) = Json.obj(
      "isBlack" -> figure.isBlack,
      "kind" -> figure.getKind,
      "stepCount" -> figure.getStepCount,
      "row" -> figure.getPosition._1,
      "col" -> figure.getPosition._2
    )
  }

  implicit val removedFigureWrites = new Writes[RemovedFigureInterface] {
    def writes(removedFigure: RemovedFigureInterface) = Json.obj(
      "round" -> removedFigure.getRound,
      "figure" -> Json.toJson(removedFigure.getFigure)
    )
  }

  implicit val optionToChangeWrites = new Writes[Option[ToChangeInterface]] {
    def writes(optionToChange: Option[ToChangeInterface]) = {
      if (optionToChange.isDefined) {
        val toChange = optionToChange.get
        Json.obj(
          "isDefined" -> true,
          "isBlack" -> toChange.isBlack,
          "figure" -> Json.toJson(toChange.getFigure)
        )
      } else {
        Json.obj(
          "isDefined" -> false
        )
      }
    }
  }

  def fieldToJson(field: FieldDataInterface): JsObject = Json.obj(
    "field" -> Json.obj(
      "round" -> field.getRoundCount,
      "figurePositions" -> Json.toJson(field.getFigurePositions.map(figure => Json.toJson(figure)),
        "option" -> Json.toJson(field.getToChange),
        "removedFigures" -> Json.toJson(field.getRemovedFigures.map(removedFigure => Json.toJson(removedFigure)))
      )
    )
  )

}

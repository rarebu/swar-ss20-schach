package de.htwg.se.Schach.aview.HttpServer

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import de.htwg.se.Schach.Schach
import de.htwg.se.Schach.controller.controllerComponent.{CellChanged, LogicControllerInterface}
import de.htwg.se.Schach.util.Observable

import scala.concurrent.Future
import scala.swing.Reactor

case class SchachLogicHttpServer(logicControllerInterface: LogicControllerInterface) extends Observable with Reactor with PlayJsonSupport {
  implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: ActorMaterializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  val route: Route = concat(
//    newField
    delete {
      path("logic" / "newField") {
        logicControllerInterface.newField
        complete("")
      }
    },
//      cellContentBlack(x,y)
    get{
      path("logic" / "cellContentBlack") {
        parameters('x.as[Int], 'y.as[Int]) { (x, y) => {
          val tmp = logicControllerInterface.cellContentBlack(x, y)
          val tmp2 = if (tmp.isDefined) Some(tmp.get.toString) else None
          complete(tmp2)
          //          complete("")
        }
        }
      }
    },
//      pawnPromoting
    get{
      path("logic" / "pawnPromoting") {
        complete(logicControllerInterface.pawnPromoting)
      }
    },
//      getChangeableFigures
    get{
      path("logic" / "getChangeableFigures") {
        complete(logicControllerInterface.getChangeableFigures)
      }
    },
//      statusText
    get{
      path("logic" / "statusText") {
        complete(logicControllerInterface.statusText)
      }
    },
//      fieldToString
    get {
      path("logic" / "fieldToString") {
        complete(logicControllerInterface.fieldToString)
      }
    },
//      cellContains(row, col)
    get {
      path("logic" / "cellContains") {
        parameters('x.as[Int], 'y.as[Int]) { (x, y) =>
          complete(logicControllerInterface.cellContains(x, y))
        }
      }
    },
//      cellIsBlack(row, col)
    get {
      path("logic" / "cellIsBlack") {
        parameters('x.as[Int], 'y.as[Int]) { (x, y) =>
          complete(logicControllerInterface.cellIsBlack(x, y).toString)
        }
      }
    },
//      move(tmp._1, tmp._2, x, y)
    post{
      path("logic" / "move") {
        parameters('x.as[Int], 'y.as[Int], 'newX.as[Int], 'newY.as[Int]) { (x, y, newX, newY) =>
          complete(logicControllerInterface.move(x, y, newX, newY).toString)
        }
      }
    },

//      choose
    post{
      path("logic" / "choose") {
        parameter('choosed.as[String]) { choosed =>
          complete(logicControllerInterface.choose(choosed).toString)
        }
      }
    },
//      undo
    post{
      path("logic" / "undo") {
        logicControllerInterface.undo
        complete("")
      }
    },
//      redo
    post{
      path("logic" / "redo") {
        logicControllerInterface.redo
        complete("")
      }
    },
//      save
    post{
      path("logic" / "save") {
        logicControllerInterface.save
        complete("")
      }
    },
//      load
    post{
      path("logic" / "load") {
        logicControllerInterface.load
        complete("")
      }
    },
    post{
      path("logic" / "becomeObserver") {
        parameter('uri.as[String]) {uri =>
          this.add(Client(uri))
          complete("")
        }

      }
    },
    post{
      path("logic" / "shutDown") {
        Schach.shutdown = true
        complete("")
      }
    }
  )

  reactions += {
    case event: CellChanged => notifyObservers()
  }

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(route, "0.0.0.0", 9090)

  def shutdownWebServer() : Unit = {
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}

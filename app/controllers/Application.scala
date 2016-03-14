package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json._
import scala.concurrent.Future
import global._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import akka.pattern.ask
import akka.util.Timeout

case class Request(id: Int,
                   rows: Int,
                   cols: Int,
                   maze: Seq[Int],
                   startX: Int,
                   startY: Int,
                   endX: Int,
                   endY: Int)

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Welcome to Maze Solver"))
  }

  implicit val requestFormat = Json.format[Request]

  def maze = Action.async(parse.json) { request =>

    request.body.validate[Request].map {
      case req: Request =>
        if (req.cols * req.rows == req.maze.length) {
            MazeGlobal.actor ! req
          Future(Ok("Hit /wayout to get all paths of the maze. ensure you use the same id."))
        } else Future(BadRequest("Insufficient, Maze should have rows * cols number of elements."))
    }.recoverTotal {
      ex => Future {
        BadRequest("Improper Input")
      }
    }

  }

  case class Res(id: Int)
  implicit  val resFormat = Json.format[Res]

  def wayout = Action.async(parse.json) { request =>
      request.body.validate[Res].map {
        case res: Res =>
          import scala.concurrent.duration._
          implicit val timeout = Timeout(5 seconds)
            val f = (MazeGlobal.actor ? res.id).mapTo[String]
          f.map { str => Ok(Json.obj("result" -> str)) }.recover {case ex => Ok(Json.obj("error" -> ex.getMessage))}
      }.recoverTotal {
        ex => Future {
          BadRequest("Bad input")
        }
      }
  }

}
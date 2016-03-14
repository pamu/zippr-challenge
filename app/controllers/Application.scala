package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json._
import scala.concurrent.Future
import global._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Welcome to Maze Solver"))
  }


  case class Request(id: Long,
                     rows: Long,
                     cols: Long,
                     maze: Seq[Int],
                     startX: Int,
                     startY: Int,
                     endX: Int,
                     endY: Int)

  implicit val requestFormat = Json.format[Request]

  def maze = Action.async(parse.json) { request =>

    request.body.validate[Request].map {
      case req: Request =>
        MazeGlobal.actor ! Request
        Future {
          Ok(req.toString)
        }
    }.recoverTotal {
      ex => Future {
        BadRequest("Improper Input")
      }
    }

  }

  def wayout = Action.async(parse.json) { request =>
    Future {
      Ok("")
    }
  }

}
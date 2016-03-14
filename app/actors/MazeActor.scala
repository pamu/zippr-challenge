package actors

import actors.MazeActor.Point
import akka.actor.{Status, ActorLogging, Actor}
import controllers.Request
import utils.MazeSolver
import scala.concurrent.Future
import akka.pattern.pipe

/**
  * Created by pnagarjuna on 14/03/16.
  */

object MazeActor {
  case class Point(a: Int, b: Int)
  case class Res(id: Long, path: List[Point])
}

class MazeActor extends Actor with ActorLogging {

  var cache: Map[Int, String] = Map.empty[Int, String]

  var status: Map[Int, Boolean] = Map.empty[Int, Boolean]

  implicit val dispather = context.dispatcher

  override def receive = {
    case req: Request =>
      println(req.toString)
      log.info(req.toString)
      Future {
        val result = MazeSolver.getPaths(Point(req.startX, req.startY), Point(req.endX, req.endY), MazeSolver.toMaze(req).get, List(Point(req.startX, req.startY)), List(List.empty[Point]))
        //(result._1.distinct -> result._2)
        req.id -> (if (result._2) result._1.toString else "")
      } pipeTo self
    case (id: Int, str: String)  =>
      cache = cache + (id -> str)
    case Status.Failure(th) =>
      log.info(th.getMessage)
    case id: Int => sender() ! (if (cache contains id) cache(id) else "")
    case ex => log.info("something " + ex.getClass)
  }
}

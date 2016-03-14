package actors

import akka.actor.{ActorLogging, Actor}
import controllers.Request

/**
  * Created by pnagarjuna on 14/03/16.
  */

object MazeActor {
  case class Point(a: Int, b: Int)
  case class Res(id: Long, path: List[Point])
}

class MazeActor extends Actor with ActorLogging {

  override def receive = {
    case req: Request =>
      log.info(req.toString)

    case ex => log.info("something " + ex.getClass)
  }
}

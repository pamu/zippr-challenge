package actors

import akka.actor.Actor

/**
  * Created by pnagarjuna on 14/03/16.
  */

object MazeActor {
  case class Point(a: Int, b: Int)
  case class Res(id: Long, path: List[Point])
}

class MazeActor extends Actor {
  override def receive = {
    case _ =>
  }
}

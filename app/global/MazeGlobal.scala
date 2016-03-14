package global

import actors.MazeActor
import akka.actor.Props
import play.api.libs.concurrent.Akka
import play.api.{Application, GlobalSettings, Logger}
import play.api.Play.current

/**
  * Created by pnagarjuna on 14/03/16.
  */
object MazeGlobal extends GlobalSettings {

  lazy val actor = Akka.system.actorOf(Props[MazeActor], "MazeActor")

  override def onStart(app: Application): Unit = {
    super.onStart(app)
    Logger.info("App started")
  }

  override def onStop(app: Application): Unit = {
    super.onStop(app)
    Logger.info("App Stopped")
    Akka.system.shutdown()
  }

}

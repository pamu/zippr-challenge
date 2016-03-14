package controllers

import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Hello Play Framework"))
  }

  def maze = Action.async(parse.json) { request =>
    Future {
      Ok("")
    }
  }

  def wayout = Action.async(parse.json) { request =>
    Future {
      Ok("")
    }
  }

}
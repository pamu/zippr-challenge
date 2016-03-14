package utils

import actors.MazeActor.Point
import controllers.Request
import play.api.Logger


/**
  * Created by pnagarjuna on 14/03/16.
  */

object MazeSolver {

  def toMaze(req: Request): Option[Array[Array[Boolean]]] =
    if (req.maze.length == req.rows * req.cols) {
      Some(req.maze.grouped(req.cols).map(_.map(_ == 1).toArray).toArray)
    } else None

  def getPaths(start: Point, goal: Point, maze: Array[Array[Boolean]],
               currentPath: List[Point], paths: List[List[Point]]): (List[List[Point]], Boolean) = {

    Logger.info(currentPath.mkString(" ") + " " + paths.mkString(" "))
    if (isValidMove(start, maze)) {

      if (start == goal) {

        (currentPath :: paths, true)

      } else {

        val newCurrentPath = start :: currentPath

        if (getPaths(Direction.getPoint(start, Direction.NORTH), goal, maze, newCurrentPath, paths)._2) {
          (paths, true)
        } else {
          if (getPaths(Direction.getPoint(start, Direction.SOUTH), goal, maze, newCurrentPath, paths)._2) {
            (paths, true)
          } else {
            if (getPaths(Direction.getPoint(start, Direction.EAST), goal, maze, newCurrentPath, paths)._2) {
              (paths, true)
            } else {
              if (getPaths(Direction.getPoint(start, Direction.WEST), goal, maze, newCurrentPath, paths)._2) {
                (paths, true)
              } else {
                (paths, false)
//                if (getPaths(Direction.getPoint(start, Direction.NORTH_WEST), goal, maze, newCurrentPath, paths)._2) {
//                  (paths, true)
//                } else {
//                  if (getPaths(Direction.getPoint(start, Direction.NORTH_EAST), goal, maze, newCurrentPath, paths)._2) {
//                    (paths, true)
//                  } else {
//                    if (getPaths(Direction.getPoint(start, Direction.SOUTH_EAST), goal, maze, newCurrentPath, paths)._2) {
//                      (paths, true)
//                    } else {
//                      if (getPaths(Direction.getPoint(start, Direction.SOUTH_WEST), goal, maze, newCurrentPath, paths)._2) {
//                        (paths, true)
//                      } else {
//                        (paths, false)
//                      }
//                    }
//                  }
//                }
              }
            }
          }
        }

      }
    } else {
      (paths, false)
    }

  }

  private def isValidMove(point: Point, maze: Array[Array[Boolean]]): Boolean = {
    val x = point.a
    val y = point.b
    val rows = maze.length
    val cols = maze(0).length
    x >= 0 && y >= 0 && x < rows && y < cols && maze(x)(y)
  }

  object Direction extends Enumeration {
    type Direction = Value
    val NORTH, SOUTH, EAST, WEST, NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST = Value

    def getPoint(point: Point, direction: Direction): Point = direction match {
      case NORTH => Point(point.a - 1, point.b)
      case SOUTH => Point(point.a + 1, point.b)
      case EAST => Point(point.a, point.b + 1)
      case WEST => Point(point.a + 1, point.b)
      case NORTH_EAST => Point(point.a - 1, point.b + 1)
      case NORTH_WEST => Point(point.a - 1, point.b - 1)
      case SOUTH_WEST => Point(point.a + 1, point.b - 1)
      case SOUTH_EAST => Point(point.a + 1, point.b + 1)
    }
  }

}

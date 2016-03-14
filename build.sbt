name := "zipr"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq("com.typesafe.play" %% "play-json" % "2.4.0")

lazy val root = (project in file(".")).enablePlugins(PlayScala)

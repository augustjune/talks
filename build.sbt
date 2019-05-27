name := "talks"

version := "0.1"

scalaVersion := "2.12.8"

scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0-M1"
libraryDependencies += "org.typelevel" %% "cats-effect" % "1.3.0"

libraryDependencies += "com.softwaremill.sttp" %% "core" % "1.5.17"
libraryDependencies += "com.softwaremill.sttp" %% "async-http-client-backend-cats" % "1.5.17"

libraryDependencies += "com.typesafe" % "config" % "1.3.4"

libraryDependencies += "com.bot4s" %% "telegram-core" % "4.2.0-RC1"
libraryDependencies += "com.bot4s" %% "telegram-akka" % "4.2.0-RC1"

val circeVersion = "0.10.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

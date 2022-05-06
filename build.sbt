lazy val `scala-skeleton` = (project in file("."))
  .settings(
    name := "scala-skeleton",
    scalaVersion := "2.13.6",
    version := "0.1",
    // The contents of /project is magically imported here, see project/Dependencies.scala
    libraryDependencies ++= Dependencies.appDependencies
  )
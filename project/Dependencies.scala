import sbt._

object Dependencies {
  object Versions {
    val scalaTestVersion = "3.2.9"
  }

  import Versions._

  val appDeps = Seq()

  val testDeps = Seq(
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  )

  val appDependencies: Seq[ModuleID] = Seq(
    testDeps,
    appDeps,
  ).flatten
}
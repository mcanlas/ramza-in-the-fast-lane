initialCommands in console := """
  import com.htmlism.ramza._
"""

scalaVersion := "2.12.1"

libraryDependencies += "org.specs2" %% "specs2-core" % "3.8.9" % "test"

libraryDependencies += "guru.nidi" % "graphviz-java" % "0.1.0"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases" // for specs2

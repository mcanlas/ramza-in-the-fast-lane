initialCommands in console := """
  import com.htmlism.ramza._
  import com.htmlism.ramza.Jobs._
"""

scalaVersion := "2.12.0"

libraryDependencies += "org.specs2" %% "specs2-core" % "3.8.6" % "test"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases" // for specs2

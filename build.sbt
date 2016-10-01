initialCommands in console := """
  import com.htmlism.ramza._
  import com.htmlism.ramza.Jobs._
"""

libraryDependencies += "org.specs2" %% "specs2-core" % "3.8.5" % "test"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases" // for specs2

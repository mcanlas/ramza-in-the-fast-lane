initialCommands in console := """
  import com.htmlism.ramza._
  import com.htmlism.ramza.Jobs._
"""

libraryDependencies += "org.specs2" %% "specs2" % "2.4.6" % "test"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases" // for specs2

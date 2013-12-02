scalaVersion := "2.10.3"

initialCommands in console := """
  import com.htmlism.ramza._
  import com.htmlism.ramza.Jobs._
"""

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "2.3.4" % "test"
)

scalacOptions ++= Seq( "-deprecation", "-unchecked", "-feature" )

import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "ltg-student-roster-tool"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    javaCore,
    "org.mongodb" % "mongo-java-driver" % "2.11.2"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += "Maven central repo" at "http://repo1.maven.org/maven2/"
  )

}

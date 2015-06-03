import sbt._
import Keys._

object MyBuild extends Build {

  scalaVersion in ThisBuild := "2.11.6"

  lazy val defaultSettings = Defaults.defaultSettings ++ Seq[Setting[_]](
    version := "0.1-SNAPSHOT",
    scalaVersion := "2.11.6",
    com.typesafe.sbteclipse.plugin.EclipsePlugin.EclipseKeys.withSource := true
  )

  lazy val root = project.in(file(".")).aggregate(erased, miniboxed)

  lazy val scalameterSettings = Seq[Setting[_]](
    resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/releases",
    libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.6",
    testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework"),
    parallelExecution in Test := false
  )
  
  lazy val erased = Project(
  	"erased",
  	file("erased"),
    settings = defaultSettings ++ scalameterSettings
  )

  lazy val miniboxed = Project(
    "miniboxed",
    file("miniboxed"),
    settings = defaultSettings ++ miniboxingSettings ++ scalameterSettings
  )
  
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
  
  /** Settings for the miniboxing plugin */
  lazy val miniboxingSettings = Seq[Setting[_]](
    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += Resolver.sonatypeRepo("releases"),
    libraryDependencies += "org.scala-miniboxing.plugins" %% "miniboxing-runtime" % "0.4-M4",
    addCompilerPlugin("org.scala-miniboxing.plugins" %% "miniboxing-plugin" % "0.4-M4"),
    scalacOptions ++= (
      //"-P:minibox:log" ::    // enable the miniboxing plugin output
      //                       // (which explains what the plugin is doing)
      //"-P:minibox:hijack" :: // enable hijacking the @specialized annotations
      //                       // transforming them into @miniboxed annotations
      Nil
    )
  )
}

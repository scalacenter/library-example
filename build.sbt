import xerial.sbt.Sonatype._

lazy val `library-example` = project
  .in(file("."))
  .settings(
    name := "library-example",
    organization := "ch.epfl.scala",
    description := "A library that does nothing useful",
    crossScalaVersions := Seq("3.0.0", "2.13.6", "2.12.14"),
    scalaVersion := crossScalaVersions.value.head,
    libraryDependencies += "com.github.scalaprops" %% "scalaprops" % "0.8.3" % Test,
    testFrameworks += new TestFramework("scalaprops.ScalapropsFramework")
  ).settings(publishSettings)
   .settings(siteSettings)

lazy val publishSettings = Def.settings(
  sonatypeProjectHosting := Some(GitHubHosting("scalacenter", "library-example", "julien.richard-foy@epfl.ch")),
  // indicate the open source licenses that apply to our project
  licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  // publish to the Sonatype repository
  publishTo := sonatypePublishToBundle.value,
  Compile / doc / target := file("site-output"),
  // binary compatibility check
  mimaPreviousArtifacts := Set.empty // Disabled on `master` branch
)

lazy val root = project
  .in(file("."))
  .aggregate(`library-example`)

lazy val siteSettings = Def.settings(
  Compile / doc / scalacOptions ++= Seq(
    "-siteroot", "./site",
  )
)

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
   .enablePlugins(MdocPlugin)

lazy val publishSettings = Def.settings(
  sonatypeProjectHosting := Some(GitHubHosting("scalacenter", "library-example", "julien.richard-foy@epfl.ch")),
  // indicate the open source licenses that apply to our project
  licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  // publish to the Sonatype repository
  publishTo := sonatypePublishToBundle.value,
  // binary compatibility check
  mimaPreviousArtifacts := Set.empty // Disabled on `master` branch
)

lazy val root = project
  .in(file("."))
  .aggregate(`library-example`)

lazy val siteSettings = {
  /*
  Combine scaladoc and mdoc.
  To do so, 
   1. copy site to target/processed_site
   2. use mdoc to process site/docs/ **.md
   3. mdoc outputs new md files in target/processed_site/docs
   4. scaladoc runs and outputs files in site-ouput
  */
  val tmpSiteDir = "processed_site"
  val siteDir = "site"
  val outputSiteDir = "site-output"

  val copySiteTask = Def.task {
    IO.copyDirectory(
      baseDirectory.value / "site",
      target.value / tmpSiteDir
    )
  }

  Def.settings(
    Compile / doc / target := baseDirectory.value / outputSiteDir,
    mdocIn := baseDirectory.value / siteDir / "docs",
    mdocOut := target.value / tmpSiteDir / "docs",
    mdocVariables := Map("VERSION" -> version.value),
    Compile / doc := (Compile / doc).dependsOn(Def.sequential(copySiteTask, mdoc.toTask(""))).value,
    Compile / doc / scalacOptions ++= Seq(
      "-siteroot", (target.value / tmpSiteDir).absolutePath,
    )
  )
}

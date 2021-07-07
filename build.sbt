import xerial.sbt.Sonatype._

ThisBuild / scalaVersion := "3.0.1-RC2"

lazy val library = project
  .in(file("library"))
  .settings(
    name := "library-example",
    organization := "ch.epfl.scala", // also used as a `groupId` by Sonatype
    description := "A library that does nothing useful",
    crossScalaVersions := Seq("3.0.0", "2.13.6", "2.12.14"),
    scalaVersion := crossScalaVersions.value.head,
    libraryDependencies += "com.github.scalaprops" %% "scalaprops" % "0.8.3" % Test,
    testFrameworks += new TestFramework("scalaprops.ScalapropsFramework")
  ).settings(publishSettings)

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

lazy val site = project
  .in(file("site"))
  .settings(siteSettings)
  .settings(publish / skip := true)
  .enablePlugins(MdocPlugin)
  .dependsOn(library)

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
  val outputSiteDir = "site-output"

  val copySiteTask = Def.task {
    IO.copyDirectory(
      baseDirectory.value / "site",
      target.value / tmpSiteDir
    )
  }

  Def.settings(
    library / Compile / doc / target := (LocalRootProject / target).value / outputSiteDir,
    mdocIn :=  baseDirectory.value / "docs",
    mdocOut := (LocalRootProject / target).value / tmpSiteDir / "docs",
    mdocVariables := Map("VERSION" -> version.value),
    Compile / doc := (library / Compile / doc).dependsOn(Def.sequential(copySiteTask, mdoc.toTask(""))).value,
    library / Compile / doc / scalacOptions ++= Seq(
      "-siteroot", ((LocalRootProject / target).value / tmpSiteDir).absolutePath,
    )
  )
}


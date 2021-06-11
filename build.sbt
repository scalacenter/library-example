crossScalaVersions := Seq("2.13.6", "2.12.14")
scalaVersion := crossScalaVersions.value.head

name := "library-example"

// also used as a `groupId` by Sonatype
organization := "ch.epfl.scala"

libraryDependencies += "com.github.scalaprops" %% "scalaprops" % "0.8.3" % Test
testFrameworks += new TestFramework("scalaprops.ScalapropsFramework")

description := "A library that does nothing useful"

import xerial.sbt.Sonatype._
sonatypeProjectHosting := Some(GitHubHosting("scalacenter", "library-example", "julien.richard-foy@epfl.ch"))
// indicate the open source licenses that apply to our project
licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))
// publish to the Sonatype repository
publishTo := sonatypePublishToBundle.value

// documentation website
enablePlugins(ParadoxPlugin, ParadoxSitePlugin, MdocPlugin, SiteScaladocPlugin)
mdocIn := sourceDirectory.value / "documentation"
mdocExtraArguments += "--no-link-hygiene"
Paradox / sourceDirectory := mdocOut.value
makeSite := makeSite.dependsOn(mdoc.toTask("")).value
SiteScaladoc / siteSubdirName := "api"
paradoxProperties += ("scaladoc.base_url" -> "api")

// binary compatibility check
mimaPreviousArtifacts := Set.empty // Disabled on `master` branch

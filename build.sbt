crossScalaVersions := Seq("2.12.8", "2.11.12")
scalaVersion := crossScalaVersions.value.head

name := "library-example"

// also used as a `groupId` by Sonatype
organization := "ch.epfl.scala"

libraryDependencies += "com.github.scalaprops" %% "scalaprops" % "0.5.5" % Test
testFrameworks += new TestFramework("scalaprops.ScalapropsFramework")

// indicate the open source licenses that apply to our project
licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

description := "A library that does nothing useful"

import xerial.sbt.Sonatype._
sonatypeProjectHosting := Some(GitHubHosting("scalacenter", "library-example", "julien.richard-foy@epfl.ch"))

// publish to the sonatype repository
publishTo := sonatypePublishTo.value

// retrieve secrets to sign files and authenticate to Sonatype
pgpPublicRing := file("ci/pubring.asc")
pgpSecretRing := file("ci/secring.asc")
pgpPassphrase := sys.env.get("PGP_PASSPHRASE").map(_.toArray)

// documentation website
enablePlugins(ParadoxPlugin, ParadoxSitePlugin, TutPlugin, SiteScaladocPlugin, GhpagesPlugin)
tutSourceDirectory := sourceDirectory.value / "documentation"
Paradox / sourceDirectory := tutTargetDirectory.value
makeSite := makeSite.dependsOn(tut).value
SiteScaladoc / siteSubdirName := "api"
paradoxProperties += ("scaladoc.base_url" -> "api")
git.remoteRepo := sonatypeProjectHosting.value.get.scmUrl

// binary compatibility check
mimaPreviousArtifacts := {
  val majorVersionBranch = "(\\d+)\\.x".r
  git.gitCurrentBranch.value match {
    case "master"              => Set.empty
    case majorVersionBranch(v) => Set(organization.value %% name.value % v)
  }
}

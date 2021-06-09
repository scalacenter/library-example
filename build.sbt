crossScalaVersions := Seq("2.13.6", "2.12.8")
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
publishTo := sonatypePublishTo.value
// retrieve secrets to sign files
pgpPublicRing := file("ci/pubring.asc")
pgpSecretRing := file("ci/secring.asc")
pgpPassphrase := sys.env.get("PGP_PASSPHRASE").map(_.toArray)

// documentation website
enablePlugins(ParadoxPlugin, ParadoxSitePlugin, MdocPlugin, SiteScaladocPlugin, GhpagesPlugin)
mdocIn := sourceDirectory.value / "documentation"
mdocExtraArguments += "--no-link-hygiene"
Paradox / sourceDirectory := mdocOut.value
makeSite := makeSite.dependsOn(mdoc.toTask("")).value
SiteScaladoc / siteSubdirName := "api"
paradoxProperties += ("scaladoc.base_url" -> "api")
git.remoteRepo := sonatypeProjectHosting.value.get.scmUrl

// binary compatibility check
mimaPreviousArtifacts := Set.empty // Disabled on `master` branch

crossScalaVersions := Seq("2.12.8", "2.11.12")
scalaVersion := crossScalaVersions.value.head

name := "library-example"

// also used as a `groupId` by Sonatype
organization := "ch.epfl.scala"

libraryDependencies += "com.github.scalaprops" %% "scalaprops" % "0.5.5" % Test
testFrameworks += new TestFramework("scalaprops.ScalapropsFramework")

// indicate the open source licenses that apply to our project
licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

scmInfo := Some(ScmInfo(url("https://github.com/scalacenter/library-example"), "scm:git@github.com:scalacenter/library-example.git"))

developers := List(Developer("julienrf", "Julien Richard-Foy", "julien.richard-foy@epfl.ch", url("https://people.epfl.ch/julien.richard-foy")))

description := "A library that does nothing useful"
homepage := Some(url("http://scalacenter.github.io/library-example"))

// publish to the sonatype repository
publishTo := sonatypePublishTo.value

// retrieve secrets to sign files and authenticate to Sonatype
pgpPublicRing := file("ci/pubring.asc")
pgpSecretRing := file("ci/secring.asc")
pgpPassphrase := sys.env.get("PGP_PASSPHRASE").map(_.toArray)
credentials ++= (
  for {
    username <- sys.env.get("SONATYPE_USER")
    password <- sys.env.get("SONATYPE_PASSWORD")
  } yield Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", username, password)
).toList

// documentation website
enablePlugins(ParadoxPlugin, ParadoxSitePlugin, TutPlugin, SiteScaladocPlugin, GhpagesPlugin)
tutSourceDirectory := sourceDirectory.value / "documentation"
Paradox / sourceDirectory := tutTargetDirectory.value
makeSite := makeSite.dependsOn(tut).value
SiteScaladoc / siteSubdirName := "api"
paradoxProperties += ("scaladoc.base_url" -> "api")
git.remoteRepo := "git@github.com:scalacenter/library-example.git"
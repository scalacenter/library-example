name := "library-example"

// also used as a `groupId` by Sonatype
organization := "ch.epfl.scala"

// indicate the open source licenses that apply to our project
licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

scmInfo := Some(ScmInfo(url("https://github.com/scalacenter/library-example"), "scm:git@github.com:scalacenter/library-example.git"))

developers := List(Developer("julienrf", "Julien Richard-Foy", "julien.richard-foy@epfl.ch", url("https://people.epfl.ch/julien.richard-foy")))

description := "A library that does nothing useful"
homepage := Some(url("http://scalacenter.github.io/library-example"))

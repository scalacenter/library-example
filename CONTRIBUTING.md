Contributing
============

## Requirements

You will need the following tools:

- [Git](https://git-scm.com/)
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [SBT](http://www.scala-sbt.org/)

## Workflow

Create your own fork of the repository and work in a local branch based on `master`. Binary compatible
fixes should be based on the corresponding branch version (e.g., `1.x`).

## Code Style

We use [scalafmt](https://scalameta.org/scalafmt/) to format the source code, and recommend you to setup
your editor to “format on save”, as documented [here](https://scalameta.org/scalafmt/docs/installation.html).

## Run Tests

From the sbt shell:

~~~
> +test
~~~

## Publish a Release

Push a Git tag:

~~~ bash
$ git tag v2.0.0
$ git push origin v2.0.0
~~~

After releasing a new major version, create a new Git branch (e.g., `2.x`) that will contain the binary
compatible evolutions of that version. In this branch, set the `mimaPreviousArtifacts` setting (in file
`build.sbt`) to the following value:

~~~ diff
-mimaPreviousArtifacts := Set.empty
+mimaPreviousArtifacts := previousStableVersion.value.map(organization.value %% name.value % _).toSet
~~~

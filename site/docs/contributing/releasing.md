---
title: Realeasing
---

# Publish a Release

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

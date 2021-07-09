---
title: Setup
---

# Setup

## Requirements

You will need the following tools:

- [Git](https://git-scm.com/)
- [OpenJDK 11](https://adoptopenjdk.net/)
- [SBT](http://www.scala-sbt.org/)

## Workflow

Create your own fork of the repository and work in a local branch based on `main`. Binary compatible
fixes should be based on the corresponding branch version (e.g., `1.x`).

## Code Style

We use [scalafmt](https://scalameta.org/scalafmt/) to format the source code, and recommend you to setup
your editor to “format on save”, as documented [here](https://scalameta.org/scalafmt/docs/installation.html).

## Run Tests

From the sbt shell:

~~~
> +test
~~~

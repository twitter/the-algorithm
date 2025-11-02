load("@rules_scala//scala:scala.bzl", "scala_library")
scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "decider/src/main/scala",
        "src/scala/com/twitter/recos/util:recos-util",
    ],
)

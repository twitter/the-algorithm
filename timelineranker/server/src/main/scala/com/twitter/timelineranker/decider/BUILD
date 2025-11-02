load("@rules_scala//scala:scala.bzl", "scala_library")
scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "servo/decider",
        "timelineranker/server/config",
    ],
)

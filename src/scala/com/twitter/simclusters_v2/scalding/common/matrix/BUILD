load("@rules_scala//scala:scala.bzl", "scala_library")
scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/scala/com/twitter/scalding_internal/job/analytics_batch",
    ],
)

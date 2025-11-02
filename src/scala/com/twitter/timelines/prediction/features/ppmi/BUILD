load("@rules_scala//scala:scala.bzl", "scala_library")
scala_library(
    sources = ["*.scala"],
    platform = "java8",
    tags = ["bazel-compatible"],
    dependencies = [
        "src/java/com/twitter/ml/api:api-base",
    ],
)

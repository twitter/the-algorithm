scala_library(
    sources = ["**/*.scala"],
    platform = "java8",
    provides = scala_artifact(
        org = "com.twitter",
        name = "servo-decider",
        repo = artifactory,
    ),
    strict_deps = True,
    tags = ["bazel-compatible"],
    dependencies = [
        "decider",
        "finagle/finagle-core/src/main",
        "tweetypie/servo/util",
        "twitter-server-internal",
        "twitter-server/server/src/main/scala",
    ],
)
